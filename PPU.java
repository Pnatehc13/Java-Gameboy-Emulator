import java.util.Arrays;
import java.util.List;

public class PPU {
    private Memory memory;

    private int mode = 2; // Start in OAM
    private int scanline = 0;
    private int modeClock = 0;

    private int[] framebuffer = new int[160 * 144];
    private boolean frameReady = false;
    int[] palette = { 0xFFFFFF, 0xAAAAAA, 0x555555, 0x000000 };
    public PPU(Memory memory) {
        this.memory = memory;
    }

    public static byte[] intToBytes(int value) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (value >> 24);
        bytes[1] = (byte) (value >> 16);
        bytes[2] = (byte) (value >> 8);
        bytes[3] = (byte) value;
        return bytes;
    }

    private int mapColorFromPalette(int colorIndex) {
        int palette = memory.readByte(0xFF47) & 0xFF;
        int shade = (palette >> (colorIndex * 2)) & 0b11;

        switch (shade) {
            case 0: return 0xFFFFFFFF; // White
            case 1: return 0xFFAAAAAA; // Light Gray
            case 2: return 0xFF555555; // Dark Gray
            case 3: return 0xFF000000; // Black
        }
        return 0xFFFF00FF; // Magenta (should never happen)
    }


    public int[] renderFrame() {
        int scx = memory.readByte(0xFF43) & 0xFF;
        int scy = memory.readByte(0xFF42) & 0xFF;
        int[] framebuffer = new int[160 * 144];

        int lcdc = memory.readByte(0xFF40) & 0xFF;
        boolean tileDataUnsigned = (lcdc & 0b00010000) != 0;
        boolean tileMapHigh = (lcdc & 0b00001000) != 0;

        int tileDataStart = tileDataUnsigned ? 0x8000 : 0x8800;
        int tileMapAddr = tileMapHigh ? 0x9C00 : 0x9800;

        for (int screenY = 0; screenY < 144; screenY++) {
            for (int screenX = 0; screenX < 160; screenX++) {
                int globalX = (screenX + scx) & 0xFF;
                int globalY = (screenY + scy) & 0xFF;

                int tileX = globalX / 8;
                int tileY = globalY / 8;

                int mapIndex = tileY * 32 + tileX;
                int tileIdRaw = memory.readByte(tileMapAddr + mapIndex);
                int tileId = tileDataUnsigned ? (tileIdRaw & 0xFF) : (byte) tileIdRaw;

                int tileAddr;
                if (tileDataUnsigned) {
                    tileAddr = 0x8000 + (tileId & 0xFF) * 16;
                } else {
                    tileAddr = 0x9000 + tileId * 16;
                }

                int[][] tile = Tiledecoder.decodeTile(tileAddr, memory);
                int pixelX = globalX % 8;
                int pixelY = globalY % 8;
                int color = mapColorFromPalette(tile[pixelY][pixelX]);

                framebuffer[screenY * 160 + screenX] = color;
            }
        }
        boolean windowEnabled = (lcdc & 0b00100000) != 0;

        if (windowEnabled) {
            int wx = (memory.readByte(0xFF4B) & 0xFF) - 7;
            int wy = memory.readByte(0xFF4A) & 0xFF;
            boolean windowTileMapHigh = (lcdc & 0b01000000) != 0;
            int windowTileMapAddr = windowTileMapHigh ? 0x9C00 : 0x9800;

            for (int winTileY = 0; winTileY < 18; winTileY++) {
                for (int winTileX = 0; winTileX < 20; winTileX++) {
                    int mapIndex = winTileY * 32 + winTileX;
                    int tileIdRaw = memory.readByte(windowTileMapAddr + mapIndex);
                    int tileId = tileDataUnsigned ? (tileIdRaw & 0xFF) : (byte) tileIdRaw;
                    int tileAddr = tileDataUnsigned ? 0x8000 + tileId * 16 : 0x9000 + tileId * 16;

                    int[][] tile = Tiledecoder.decodeTile(tileAddr, memory);

                    for (int y = 0; y < 8; y++) {
                        for (int x = 0; x < 8; x++) {
                            int screenX = wx + winTileX * 8 + x;
                            int screenY = wy + winTileY * 8 + y;

                            if (screenX >= 0 && screenX < 160 && screenY >= 0 && screenY < 144) {
                                int color = mapColorFromPalette(tile[y][x]);
                                framebuffer[screenY * 160 + screenX] = color;
                            }
                        }
                    }
                }
            }
        }
        drawSprites(framebuffer);
        return framebuffer;
    }
    public void drawSprites(int[] framebuffer) {
        boolean use8x16 = (memory.readByte(0xFF40) & (1 << 2)) != 0;

        for (int i = 0; i < 40; i++) {
            int base = 0xFE00 + i * 4;
            int yPos = memory.readByte(base) & 0xFF;
            int xPos = memory.readByte(base + 1) & 0xFF;
            int tileIndex = memory.readByte(base + 2) & 0xFF;
            int attr = memory.readByte(base + 3) & 0xFF;

            int spriteHeight = use8x16 ? 16 : 8;

            // Adjust coordinates (sprite is offset by (-8, -16) in hardware)
            int screenX = xPos - 8;
            int screenY = yPos - 16;

            if (screenX < -7 || screenX >= 160 || screenY < -15 || screenY >= 144) continue;

            boolean flipX = (attr & (1 << 5)) != 0;
            boolean flipY = (attr & (1 << 6)) != 0;
            boolean usePalette1 = (attr & (1 << 4)) != 0;
            boolean priority = (attr & (1 << 7)) != 0;

            int palette = memory.readByte(usePalette1 ? 0xFF49 : 0xFF48) & 0xFF;

            for (int row = 0; row < spriteHeight; row++) {
                int tileRow = flipY ? spriteHeight - 1 - row : row;
                int actualTileIndex = use8x16 ? (tileIndex & 0xFE) + (tileRow >= 8 ? 1 : 0) : tileIndex;
                int yInTile = tileRow % 8;

                int addr = 0x8000 + actualTileIndex * 16 + yInTile * 2;
                int lo = memory.readByte(addr) & 0xFF;
                int hi = memory.readByte(addr + 1) & 0xFF;

                for (int col = 0; col < 8; col++) {
                    int bit = flipX ? col : 7 - col;
                    int colorId = ((hi >> bit) & 1) << 1 | ((lo >> bit) & 1);

                    if (colorId == 0) continue; // Transparent

                    int paletteColor = (palette >> (colorId * 2)) & 0b11;
                    int color = getColor(paletteColor);

                    int px = screenX + col;
                    int py = screenY + row;

                    if (px < 0 || px >= 160 || py < 0 || py >= 144) continue;

                    int index = py * 160 + px;

                    if (!priority || framebuffer[index] == getColor(0)) {
                        framebuffer[index] = color;
                    }
                }
            }
        }
    }
    private int getColor(int id) {
        switch (id) {
            case 0: return 0xFFFFFFFF; // White
            case 1: return 0xFFAAAAAA; // Light gray
            case 2: return 0xFF555555; // Dark gray
            case 3: return 0xFF000000; // Black
            default: return 0xFFFFFFFF;
        }
    }


    private int mapColor(int color) {
        int bgp = memory.readByte(0xFF47) & 0xFF;
        int paletteBits = (bgp >> (color * 2)) & 0b11;

        return switch (paletteBits) {
            case 0 -> 0xFFFFFF; // White
            case 1 -> 0xAAAAAA; // Light Gray
            case 2 -> 0x555555; // Dark Gray
            case 3 -> 0x000000; // Black
            default -> 0xFF00FF; // Error
        };
    }

    public boolean isFrameReady() {
        return frameReady;
    }
    private void drawTileFromVRAM(Memory memory, int[] framebuffer, int startX, int startY, int tileIndex) {
        int baseAddr = 0x8000 + tileIndex * 16;

        for (int row = 0; row < 8; row++) {
            int low = memory.readByte(baseAddr + row * 2) & 0xFF;
            int high = memory.readByte(baseAddr + row * 2 + 1) & 0xFF;

            for (int col = 0; col < 8; col++) {
                int bit = 7 - col;
                int colorId = ((high >> bit) & 1) << 1 | ((low >> bit) & 1);
                int color;
                switch (colorId) {
                    case 0 : color = 0xFFFFFF;break; // wh;ite
                    case 1 : color = 0xAAAAAA;break; // light gray
                    case 2 : color =0x555555;break; // dark gray
                    case 3 : color = 0x000000;break; // black
                    default : color = 0xFF00FF;break; // magenta = error
                };

                int x = startX + col;
                int y = startY + row;

                if (x >= 0 && x < 160 && y >= 0 && y < 144) {
                    framebuffer[y * 160 + x] = color;
                }
            }
        }
    }


    public void step(int cycles) {
        int lcdc = memory.readByte(0xFF40) & 0xFF;
        if ((lcdc & 0x80) == 0) {
            // LCD off behaviour
            mode = 1;
            modeClock = 0;
            scanline = 0;
            memory.writeByte(0xFF44, (byte)0);
            frameReady = false;
            return;
        }
        modeClock += cycles;

        if (mode == 2 && modeClock >= 80) {
            mode = 3;
            modeClock -= 80;
        } else if (mode == 3 && modeClock >= 172) {
            mode = 0;
            modeClock -= 172;
            // drawScanline(); // implement this if needed
        } else if (mode == 0 && modeClock >= 204) {
            modeClock -= 204;
            scanline++;
            memory.writeByte(0xFF44, (byte) scanline);

            if (scanline == 144) {
                mode = 1; // VBlank
                memory.requestInterrupt(0); // VBlank interrupt
                frameReady = true;
            } else if (scanline > 153) {
                scanline = 0;
                mode = 2;
                memory.writeByte(0xFF44, (byte) scanline);
            }
        }
    }

    private void drawScanline() {

        // Later: draw 160 pixels for current scanline using tile map + tile data
    }

    public boolean shouldDrawFrame() {
        if (frameReady) {
            frameReady = false;
            return true;
        }
        return false;
    }

}
