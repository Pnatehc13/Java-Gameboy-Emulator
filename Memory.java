import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Memory {
    byte[] rom = new byte[0x8000]; // 32KB for now (Bank 0 + Bank 1)
    byte[] wram = new byte[0x2000];
    byte[] oam = new byte[0xA0]; // 160 bytes
    byte[] io = new byte[0x80];
    byte[] hram = new byte[0x7F]; // 127 bytes
    byte[] vram = new byte[0x2000]; // 8KB VRAM
    byte[] eram = new byte[0x2000]; // 8KB External RAM
    byte interruptEnable = 0;
    // Timer registers
    int div = 0;
    int tima = 0;
    int tma = 0;
    int tac = 0;
    public int timerCounter = 0;
    public int divCounter = 0;
    private int joypad = 0xFF; // Default all 1s

    public void updateTimers(int cycles) {
        // Increment DIV every 256 cycles
        divCounter += cycles;
        if (divCounter >= 256) {
            div = (div + 1) & 0xFF;
            divCounter -= 256;
        }

        // Check if timer is enabled (bit 2 of TAC)
        if ((tac & 0b100) != 0) {
            timerCounter += cycles;

            int threshold = switch (tac & 0b11) {
                case 0b00 -> 1024; // 4096 Hz
                case 0b01 -> 16;   // 262144 Hz
                case 0b10 -> 64;   // 65536 Hz
                case 0b11 -> 256;  // 16384 Hz
                default -> 1024;
            };

            while (timerCounter >= threshold) {
                timerCounter -= threshold;
                tima++;
                if (tima > 0xFF) {
                    tima = tma;
                    requestInterrupt(2);  // Timer interrupt
                }
            }
        }
    }

    public int readByte(int addr)
    {
        if (addr >= 0x0000 && addr <= 0x3FFF) return rom[addr] & 0xFF;               // ROM Bank 0
        else if (addr >= 0x4000 && addr <= 0x7FFF) return rom[addr] & 0xFF;         // ROM Bank 1 (hardcoded)
        else if (addr >= 0x8000 && addr <= 0x9FFF) return vram[addr - 0x8000] & 0xFF; // VRAM
        else if (addr >= 0xA000 && addr <= 0xBFFF) return eram[addr - 0xA000] & 0xFF;//ERAM
        else if(addr >= 0xC000 && addr<=0xDFFF) return wram[addr-0xC000]&0xFF;//WRAM
        else if (addr >= 0xE000 && addr <= 0xFDFF) return readByte(addr - 0x2000); // Mirror WRAM
        else if (addr >= 0xFE00 && addr <= 0xFE9F) return oam[addr - 0xFE00]; // OAM
        else if (addr >= 0xFEA0 && addr <= 0xFEFF) return 0xFF; // Or 0x00
        else if (addr == 0xFF00) return joypad & 0xFF;
        else if (addr == 0xFF04) return div & 0xFF;
        else if (addr == 0xFF05) return tima & 0xFF;
        else if (addr == 0xFF06) return tma & 0xFF;
        else if (addr == 0xFF07) return tac & 0xFF;
        else if (addr >= 0xFF00 && addr <= 0xFF7F) return io[addr - 0xFF00] & 0xFF;
        else if (addr >= 0xFF80 && addr <= 0xFFFE) return hram[addr - 0xFF80] & 0xFF;
        else if (addr == 0xFFFF) return interruptEnable & 0xFF;
        else {
            System.err.printf("Unhandled memory read at: 0x%04X%n", addr);
            return 0xFF;
        }
    }
    public void writeByte(int addr , byte val)
    {
        if (addr == 0xFF46) {
            int src = (val & 0xFF) << 8;
            for (int i = 0; i < 0xA0; i++) {
                oam[i] = (byte)(readByte(src + i) & 0xFF);
            }
            return;
        }
        if (addr == 0xFF02 && val == (byte) 0x81) { // Start serial transfer
            char c = (char) (readByte(0xFF01) & 0xFF); // Read SB
            System.out.print(c); // Output character
            io[0x02] = 0x00; // Directly clear SC without recursive call
            return;
        }

        if (addr >= 0x0000 && addr <= 0x7FFF) return; // ROM is read-only
        else if (addr >= 0x8000 && addr <= 0x9FFF) vram[addr - 0x8000] = val; // VRAM
        else if (addr >= 0xA000 && addr <= 0xBFFF) eram[addr - 0xA000] = val;//ERAM
        else if (addr >= 0xC000 && addr <= 0xDFFF) wram[addr - 0xC000] = val;// WRAM
        else if (addr >= 0xE000 && addr <= 0xFDFF) writeByte(addr - 0x2000, val); // Mirror WRAM
        else if (addr >= 0xFE00 && addr <= 0xFE9F) oam[addr - 0xFE00] = val;//OAM
        else if (addr >= 0xFEA0 && addr <= 0xFEFF) return; // Or 0x00
        else if (addr == 0xFF00) joypad = (joypad & 0xCF) | (val & 0x30); // only bits 4 and 5 are writable
        else if (addr == 0xFF04) div = 0;
        else if (addr == 0xFF05) tima = val & 0xFF;
        else if (addr == 0xFF06) tma = val & 0xFF;
        else if (addr == 0xFF07) tac = val & 0xFF;
        else if (addr >= 0xFF00 && addr <= 0xFF7F) io[addr - 0xFF00] = val;
        else if (addr >= 0xFF80 && addr <= 0xFFFE) hram[addr - 0xFF80] = val;
        else if (addr == 0xFFFF) interruptEnable = val;
        else return;
    }

    public void requestInterrupt(int index) {
        int currentIF = readByte(0xFF0F);
        currentIF |= (1 << index);
        writeByte(0xFF0F, (byte) currentIF);
    }

    public void writeWord(int address, int value) {
        writeByte(address, (byte) (value & 0xFF));         // low byte
        writeByte(address + 1, (byte) ((value >> 8) & 0xFF)); // high byte
    }

    private final boolean[] keyStates = new boolean[8]; // 0: Right, ..., 7: Start

    public void setKeyState(int key, boolean pressed) {
        keyStates[key] = pressed;
        updateJoypad();
    }

    private void updateJoypad() {
        int input = 0xFF;

        if ((joypad & 0x10) == 0) {
            if (keyStates[0]) input &= ~(1 << 0); // Right
            if (keyStates[1]) input &= ~(1 << 1); // Left
            if (keyStates[2]) input &= ~(1 << 2); // Up
            if (keyStates[3]) input &= ~(1 << 3); // Down
        } else if ((joypad & 0x20) == 0) {
            if (keyStates[4]) input &= ~(1 << 0); // A
            if (keyStates[5]) input &= ~(1 << 1); // B
            if (keyStates[6]) input &= ~(1 << 2); // Select
            if (keyStates[7]) input &= ~(1 << 3); // Start
        }

        joypad = (joypad & 0xF0) | (input & 0x0F);
    }


    public int readWord(int address) {
        int low = readByte(address) & 0xFF;
        int high = readByte(address + 1) & 0xFF;
        return (high << 8) | low;
    }
    public int readLCDC() {
        return readByte(0xFF40) & 0xFF;
    }

    public int readBGP() {
        return readByte(0xFF47) & 0xFF;
    }
    public void loadROM(String path) {
        try {
            java.nio.file.Path filePath = java.nio.file.Paths.get(path);
            byte[] fileData = java.nio.file.Files.readAllBytes(filePath);

            if (fileData.length > rom.length) {
                System.out.printf("Warning: ROM size (%d bytes) larger than base ROM array (%d bytes). Truncating.\n",
                        fileData.length, rom.length);
            }

            // Copy into ROM array (fixed + banked area 0x0000–0x7FFF)
            System.arraycopy(fileData, 0, rom, 0, Math.min(fileData.length, rom.length));

            System.out.printf("ROM loaded: %d bytes\n", fileData.length);

            // Optional: Dump first 256 bytes for inspection
            System.out.println("=== ROM DUMP (first 256 bytes) ===");
            for (int i = 0; i < 256 && i < fileData.length; i++) {
                System.out.printf("%02X ", fileData[i] & 0xFF);
                if ((i + 1) % 16 == 0) System.out.println();
            }

        } catch (Exception e) {
            System.err.println("Failed to load ROM: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
