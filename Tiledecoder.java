import java.util.*;

public class Tiledecoder {

    // Returns a list of 384 decoded tiles, each 8x8
    public static List<int[][]> decodeAllTiles(Memory memory) {
        List<int[][]> tiles = new ArrayList<>();

        for (int tileIndex = 0; tileIndex < 384; tileIndex++) {
            int baseAddr = 0x8000 + tileIndex * 16;
            byte[] tileBytes = new byte[16];

            for (int i = 0; i < 16; i++) {
                tileBytes[i] = (byte) memory.readByte(baseAddr + i);
            }

            int[][] tilePixels = decodeTile(tileBytes);
            tiles.add(tilePixels);
        }

        return tiles;
    }


    public static int[][] decodeTile(int tileAddr, Memory memory) {
        byte[] tileData = new byte[16];
        for (int i = 0; i < 16; i++) {
            tileData[i] = (byte) memory.readByte(tileAddr + i);
        }
        return decodeTile(tileData);
    }
    // Decodes 1 tile (16 bytes → 8x8 2-bit pixels)
    public static int[][] decodeTile(byte[] tileData) {
        int[][] pixels = new int[8][8];

        for (int row = 0; row < 8; row++) {
            int b1 = tileData[row * 2] & 0xFF;
            int b2 = tileData[row * 2 + 1] & 0xFF;

            for (int col = 0; col < 8; col++) {
                int bit1 = (b1 >> (7 - col)) & 1;
                int bit2 = (b2 >> (7 - col)) & 1;
                int color = (bit2 << 1) | bit1;
                pixels[row][col] = color;
            }
        }

        return pixels;
    }

    public static void printTile(int[][] tile) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                System.out.print(tile[row][col] + " ");
            }
            System.out.println();
        }
    }
}
