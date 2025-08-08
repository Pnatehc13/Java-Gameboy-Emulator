public class Register {
    public int a, f;
    public int b, c;
    public int d, e;
    public int h, l;
    // --- Flag bit positions in F register ---
    private static final int FLAG_Z = 0x80; // Zero
    private static final int FLAG_N = 0x40; // Subtract
    private static final int FLAG_H = 0x20; // Half-carry
    private static final int FLAG_C = 0x10; // Carry

    public int getAF() {
        return (a << 8) | f;
    }

    public void setAF(int value) {
        a = (value >> 8) & 0xFF;
        f = value & 0xF0;
    }

    public int getBC() {
        return (b << 8) | c;
    }

    public void setBC(int value) {
        b = (value >> 8) & 0xFF;
        c = value & 0xFF;
    }

    public int getDE() {
        return (d << 8) | e;
    }

    public void setDE(int value) {
        d = (value >> 8) & 0xFF;
        e = value & 0xFF;
    }

    public int getHL() {
        return (h << 8) | l;
    }

    public void setHL(int value) {
        h = (value >> 8) & 0xFF;
        l = value & 0xFF;
    }

    public boolean getZeroFlag() {
        return (f & 0x80) != 0;
    }

    public boolean getCarryFlag() {
        return (f & 0x10) != 0;
    }

    public boolean getZ() {
        return (f & FLAG_Z) != 0;
    }

    public boolean getN() {
        return (f & FLAG_N) != 0;
    }

    public boolean getH() {
        return (f & FLAG_H) != 0;
    }

    public boolean getC() {
        return (f & FLAG_C) != 0;
    }

    // --- Setters ---
    public void setZ(boolean value) {
        if (value) f |= FLAG_Z;
        else f &= ~FLAG_Z;
    }

    public void setN(boolean value) {
        if (value) f |= FLAG_N;
        else f &= ~FLAG_N;
    }

    public void setH(boolean value) {
        if (value) f |= FLAG_H;
        else f &= ~FLAG_H;
    }

    public void setC(boolean value) {
        if (value) f |= FLAG_C;
        else f &= ~FLAG_C;
    }
}
