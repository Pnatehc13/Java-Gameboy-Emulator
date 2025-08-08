import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntSupplier;

public class Instructions {
    public static int executeOpcode(CPU cpu, int opcode)
    {
        switch (opcode) {
            case 0xD3: case 0xDB: case 0xDD:
            case 0xE3: case 0xE4: case 0xEB: case 0xEC: case 0xED:
            case 0xF4: case 0xFC: case 0xFD:
                return 0; // Undefined/illegal instructions, usually treated as NOP

            case 0x00: nop(cpu); return 4;

// Load r, d8
/// /

// LD (a16), A and LD A, (a16)


// LD (HL), r


// LD r, (HL)

// Special register loads
            case 0x0A: ld_a_bc(cpu); return 8;
            case 0x1A: ld_a_de(cpu); return 8;
            case 0x02: ld_bc_a(cpu); return 8;
            case 0x12: ld_de_a(cpu); return 8;
            case 0xF8: ld_hl_sp_plus_e8(cpu); return 12;
            case 0xF9: ld_sp_hl(cpu); return 8;


//arthemetic
//add a r
            case 0x80: add_a_r(cpu, cpu.register.b); return 4;      // ADD A, B
            case 0x81: add_a_r(cpu, cpu.register.c); return 4;      // ADD A, C
            case 0x82: add_a_r(cpu, cpu.register.d); return 4;      // ADD A, D
            case 0x83: add_a_r(cpu, cpu.register.e); return 4;      // ADD A, E
            case 0x84: add_a_r(cpu, cpu.register.h); return 4;      // ADD A, H
            case 0x85: add_a_r(cpu, cpu.register.l); return 4;      // ADD A, L
            case 0x86: add_a_r(cpu, cpu.memory.readByte(cpu.register.getHL())); return 8; // ADD A, (HL)
            case 0x87: add_a_r(cpu, cpu.register.a); return 4;      // ADD A, A

// adc a r
            case 0x88: adc_a_r(cpu, () -> cpu.register.b); return 4;
            case 0x89: adc_a_r(cpu, () -> cpu.register.c); return 4;
            case 0x8A: adc_a_r(cpu, () -> cpu.register.d); return 4;
            case 0x8B: adc_a_r(cpu, () -> cpu.register.e); return 4;
            case 0x8C: adc_a_r(cpu, () -> cpu.register.h); return 4;
            case 0x8D: adc_a_r(cpu, () -> cpu.register.l); return 4;
            case 0x8F: adc_a_r(cpu, () -> cpu.register.a); return 4;
            case 0x8E: adc_a_r(cpu, () -> cpu.memory.readByte(cpu.register.getHL())); return 8;

// sub a r
            case 0x90: sub_a_r(cpu, cpu.register.b); return 4;
            case 0x91: sub_a_r(cpu, cpu.register.c); return 4;
            case 0x92: sub_a_r(cpu, cpu.register.d); return 4;
            case 0x93: sub_a_r(cpu, cpu.register.e); return 4;
            case 0x94: sub_a_r(cpu, cpu.register.h); return 4;
            case 0x95: sub_a_r(cpu, cpu.register.l); return 4;
            case 0x96: sub_a_r(cpu, cpu.memory.readByte(cpu.register.getHL())); return 8;
            case 0x97: sub_a_r(cpu, cpu.register.a); return 4;

// sbc a r
            case 0x98: sbc_a_r(cpu, cpu.register.b); return 4;
            case 0x99: sbc_a_r(cpu, cpu.register.c); return 4;
            case 0x9A: sbc_a_r(cpu, cpu.register.d); return 4;
            case 0x9B: sbc_a_r(cpu, cpu.register.e); return 4;
            case 0x9C: sbc_a_r(cpu, cpu.register.h); return 4;
            case 0x9D: sbc_a_r(cpu, cpu.register.l); return 4;
            case 0x9E: sbc_a_r(cpu, cpu.memory.readByte(cpu.register.getHL())); return 8;
            case 0x9F: sbc_a_r(cpu, cpu.register.a); return 4;

//and
            case 0xA0: and_a_r(cpu, cpu.register.b); return 4;
            case 0xA1: and_a_r(cpu, cpu.register.c); return 4;
            case 0xA2: and_a_r(cpu, cpu.register.d); return 4;
            case 0xA3: and_a_r(cpu, cpu.register.e); return 4;
            case 0xA4: and_a_r(cpu, cpu.register.h); return 4;
            case 0xA5: and_a_r(cpu, cpu.register.l); return 4;
            case 0xA6: and_a_r(cpu, cpu.memory.readByte(cpu.register.getHL())); return 8;
            case 0xA7: and_a_r(cpu, cpu.register.a); return 4;

//or
            case 0xB0: or_a_r(cpu, cpu.register.b); return 4;
            case 0xB1: or_a_r(cpu, cpu.register.c); return 4;
            case 0xB2: or_a_r(cpu, cpu.register.d); return 4;
            case 0xB3: or_a_r(cpu, cpu.register.e); return 4;
            case 0xB4: or_a_r(cpu, cpu.register.h); return 4;
            case 0xB5: or_a_r(cpu, cpu.register.l); return 4;
            case 0xB6: or_a_r(cpu, cpu.memory.readByte(cpu.register.getHL())); return 8;
            case 0xB7: or_a_r(cpu, cpu.register.a); return 4;

//xor
            case 0xA8: xor_a_r(cpu, cpu.register.b); return 4;
            case 0xA9: xor_a_r(cpu, cpu.register.c); return 4;
            case 0xAA: xor_a_r(cpu, cpu.register.d); return 4;
            case 0xAB: xor_a_r(cpu, cpu.register.e); return 4;
            case 0xAC: xor_a_r(cpu, cpu.register.h); return 4;
            case 0xAD: xor_a_r(cpu, cpu.register.l); return 4;
            case 0xAE: xor_a_r(cpu, cpu.memory.readByte(cpu.register.getHL())); return 8;
            case 0xAF: xor_a_r(cpu, cpu.register.a); return 4;

//cp
            case 0xB8: cp_a_r(cpu, cpu.register.b); return 4;
            case 0xB9: cp_a_r(cpu, cpu.register.c); return 4;
            case 0xBA: cp_a_r(cpu, cpu.register.d); return 4;
            case 0xBB: cp_a_r(cpu, cpu.register.e); return 4;
            case 0xBC: cp_a_r(cpu, cpu.register.h); return 4;
            case 0xBD: cp_a_r(cpu, cpu.register.l); return 4;
            case 0xBE: cp_a_r(cpu, cpu.memory.readByte(cpu.register.getHL())); return 8;
            case 0xBF: cp_a_r(cpu, cpu.register.a); return 4;
//


//
            case 0xF3: di(cpu); cpu.ime = false; cpu.imeRequested = false; return 4;
            case 0xFB: ei(cpu); cpu.imeRequested = true; return 4;
            case 0x76: halt(cpu); return 4;
            case 0x10: stop(cpu); return 4;
// INC r
            case 0x04: cpu.register.b = inc8(cpu, cpu.register.b); return 4;
            case 0x0C: cpu.register.c = inc8(cpu, cpu.register.c); return 4;
            case 0x14: cpu.register.d = inc8(cpu, cpu.register.d); return 4;
            case 0x1C: cpu.register.e = inc8(cpu, cpu.register.e); return 4;
            case 0x24: cpu.register.h = inc8(cpu, cpu.register.h); return 4;
            case 0x2C: cpu.register.l = inc8(cpu, cpu.register.l); return 4;
            case 0x3C: cpu.register.a = inc8(cpu, cpu.register.a); return 4;

// DEC r
            case 0x05: cpu.register.b = dec8(cpu, cpu.register.b); return 4;
            case 0x0D: cpu.register.c = dec8(cpu, cpu.register.c); return 4;
            case 0x15: cpu.register.d = dec8(cpu, cpu.register.d); return 4;
            case 0x1D: cpu.register.e = dec8(cpu, cpu.register.e); return 4;
            case 0x25: cpu.register.h = dec8(cpu, cpu.register.h); return 4;
            case 0x2D: cpu.register.l = dec8(cpu, cpu.register.l); return 4;
            case 0x3D: cpu.register.a = dec8(cpu, cpu.register.a); return 4;

// ADD HL, rr
            case 0x09: addHL(cpu, cpu.register.getBC()); return 8;
            case 0x19: addHL(cpu, cpu.register.getDE()); return 8;
            case 0x29: addHL(cpu, cpu.register.getHL()); return 8;
            case 0x39: addHL(cpu, cpu.sp); return 8;
            case 0xE8: addSPe8(cpu); return 16;

// 16-bit INC
            case 0x03: cpu.register.setBC((cpu.register.getBC() + 1) & 0xFFFF); return 8;
            case 0x13: cpu.register.setDE((cpu.register.getDE() + 1) & 0xFFFF); return 8;
            case 0x23: cpu.register.setHL((cpu.register.getHL() + 1) & 0xFFFF); return 8;
            case 0x33: cpu.sp = (cpu.sp + 1) & 0xFFFF; return 8;

// 16-bit DEC
            case 0x0B: cpu.register.setBC((cpu.register.getBC() - 1) & 0xFFFF); return 8;
            case 0x1B: cpu.register.setDE((cpu.register.getDE() - 1) & 0xFFFF); return 8;
            case 0x2B: cpu.register.setHL((cpu.register.getHL() - 1) & 0xFFFF); return 8;
            case 0x3B: cpu.sp = (cpu.sp - 1) & 0xFFFF; return 8;

            //
            case 0x01: {
                int low = cpu.memory.readByte(cpu.pc++);
                int high = cpu.memory.readByte(cpu.pc++);
                cpu.register.setBC((high << 8) | low);
                return 12;
            }
            case 0x11: {
                int low = cpu.memory.readByte(cpu.pc++);
                int high = cpu.memory.readByte(cpu.pc++);
                cpu.register.setDE((high << 8) | low);
                return 12;
            }
            case 0x21:
                cpu.register.l = cpu.memory.readByte(cpu.pc++);
                cpu.register.h = cpu.memory.readByte(cpu.pc++);
                cpu.cycles += 12;
                return 12;
            case 0x31: {
                int low = cpu.memory.readByte(cpu.pc++);
                int high = cpu.memory.readByte(cpu.pc++);
                cpu.sp = (high << 8) | low;
                return 12;
            }

// PUSH
            case 0xF5: { // PUSH AF
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) cpu.register.a);
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) (cpu.register.f & 0xF0)); // only upper 4 bits valid
                return 16;
            }
            case 0xC5: { // PUSH BC
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) cpu.register.b);
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) cpu.register.c);
                return 16;
            }
            case 0xD5: { // PUSH DE
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) cpu.register.d);
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) cpu.register.e);
                return 16;
            }
            case 0xE5: { // PUSH HL
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) cpu.register.h);
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) cpu.register.l);
                return 16;
            }

// POP
            case 0xF1: { // POP AF
                cpu.register.f = cpu.memory.readByte(cpu.sp++) & 0xF0;
                cpu.register.a = cpu.memory.readByte(cpu.sp++);
                return 12;
            }
            case 0xC1: { // POP BC
                cpu.register.c = cpu.memory.readByte(cpu.sp++);
                cpu.register.b = cpu.memory.readByte(cpu.sp++);
                return 12;
            }
            case 0xD1: { // POP DE
                cpu.register.e = cpu.memory.readByte(cpu.sp++);
                cpu.register.d = cpu.memory.readByte(cpu.sp++);
                return 12;
            }
            case 0xE1: { // POP HL
                cpu.register.l = cpu.memory.readByte(cpu.sp++);
                cpu.register.h = cpu.memory.readByte(cpu.sp++);
                return 12;
            }
            //

// LD r, r (already had HL versions)
            case 0x40: cpu.register.b = cpu.register.b; return 4; // LD B, B
            case 0x41: cpu.register.b = cpu.register.c; return 4;
            case 0x42: cpu.register.b = cpu.register.d; return 4;
            case 0x43: cpu.register.b = cpu.register.e; return 4;
            case 0x44: cpu.register.b = cpu.register.h; return 4;
            case 0x45: cpu.register.b = cpu.register.l; return 4;
            case 0x47: cpu.register.b = cpu.register.a; return 4;

            case 0x48: cpu.register.c = cpu.register.b; return 4;
            case 0x49: cpu.register.c = cpu.register.c; return 4;
            case 0x4A: cpu.register.c = cpu.register.d; return 4;
            case 0x4B: cpu.register.c = cpu.register.e; return 4;
            case 0x4C: cpu.register.c = cpu.register.h; return 4;
            case 0x4D: cpu.register.c = cpu.register.l; return 4;
            case 0x4F: cpu.register.c = cpu.register.a; return 4;

            case 0x50: cpu.register.d = cpu.register.b; return 4;
            case 0x51: cpu.register.d = cpu.register.c; return 4;
            case 0x52: cpu.register.d = cpu.register.d; return 4;
            case 0x53: cpu.register.d = cpu.register.e; return 4;
            case 0x54: cpu.register.d = cpu.register.h; return 4;
            case 0x55: cpu.register.d = cpu.register.l; return 4;
            case 0x57: cpu.register.d = cpu.register.a; return 4;

            case 0x58: cpu.register.e = cpu.register.b; return 4;
            case 0x59: cpu.register.e = cpu.register.c; return 4;
            case 0x5A: cpu.register.e = cpu.register.d; return 4;
            case 0x5B: cpu.register.e = cpu.register.e; return 4;
            case 0x5C: cpu.register.e = cpu.register.h; return 4;
            case 0x5D: cpu.register.e = cpu.register.l; return 4;
            case 0x5F: cpu.register.e = cpu.register.a; return 4;

            case 0x60: cpu.register.h = cpu.register.b; return 4;
            case 0x61: cpu.register.h = cpu.register.c; return 4;
            case 0x62: cpu.register.h = cpu.register.d; return 4;
            case 0x63: cpu.register.h = cpu.register.e; return 4;
            case 0x64: cpu.register.h = cpu.register.h; return 4;
            case 0x65: cpu.register.h = cpu.register.l; return 4;
            case 0x67: cpu.register.h = cpu.register.a; return 4;

            case 0x68: cpu.register.l = cpu.register.b; return 4;
            case 0x69: cpu.register.l = cpu.register.c; return 4;
            case 0x6A: cpu.register.l = cpu.register.d; return 4;
            case 0x6B: cpu.register.l = cpu.register.e; return 4;
            case 0x6C: cpu.register.l = cpu.register.h; return 4;
            case 0x6D: cpu.register.l = cpu.register.l; return 4;
            case 0x6F: cpu.register.l = cpu.register.a; return 4;

            case 0x78: cpu.register.a = cpu.register.b; return 4;
            case 0x79: cpu.register.a = cpu.register.c; return 4;
            case 0x7A: cpu.register.a = cpu.register.d; return 4;
            case 0x7B: cpu.register.a = cpu.register.e; return 4;
            case 0x7C: cpu.register.a = cpu.register.h; return 4;
            case 0x7D: cpu.register.a = cpu.register.l; return 4;
            case 0x7F: cpu.register.a = cpu.register.a; return 4;

            //
// Rotate and Flag operations
            case 0x07: rlca(cpu); return 4;
            case 0x0F: rrca(cpu); return 4;
            case 0x17: rla(cpu); return 4;
            case 0x1F: rra(cpu); return 4;
            case 0x27: daa(cpu); return 4;
            case 0x2F: cpl(cpu); return 4;
            case 0x37: scf(cpu); return 4;
            case 0x3F: ccf(cpu); return 4;

            case 0x34: {
                int addr = cpu.register.getHL();
                byte value = (byte) cpu.memory.readByte(addr);
                byte result = (byte) inc8(cpu, value);
                cpu.memory.writeByte(addr, result);
                return 12;
            }
            case 0x35: {
                int addr = cpu.register.getHL();
                byte value = (byte) cpu.memory.readByte(addr);
                byte result = (byte) dec8(cpu, value);
                cpu.memory.writeByte(addr, result);
                return 12;
            }

            case 0xE0: {
                int a8 = cpu.fetch(); // Fetch next byte after opcode
                int addr = 0xFF00 | a8;
                cpu.memory.writeByte(addr,(byte) (cpu.register.a));
                return 12;
            }
            case 0xF0: {
                int a8 = cpu.fetch();
                int addr = 0xFF00 | a8;
                cpu.register.a = cpu.memory.readByte(addr);
                return 12;
            }

            case 0xC6: {
                int value = cpu.memory.readByte(cpu.pc++);
                add_a_r(cpu, value);
                return 8;
            }
            case 0xCE: {
                int value = cpu.memory.readByte(cpu.pc++);
                adc_a_r(cpu, () -> value);
                return 8;
            }
            case 0xD6: {
                int value = cpu.memory.readByte(cpu.pc++);
                sub_a_r(cpu, value);
                return 8;
            }
            case 0xDE: {
                int value = cpu.memory.readByte(cpu.pc++);
                sbc_a_r(cpu, value);
                return 8;
            }
            case 0xE6: {
                int value = cpu.memory.readByte(cpu.pc++);
                and_a_r(cpu, value);
                return 8;
            }
            case 0xEE: {
                int value = cpu.memory.readByte(cpu.pc++);
                xor_a_r(cpu, value);
                return 8;
            }
            case 0xF6: {
                int value = cpu.memory.readByte(cpu.pc++);
                or_a_r(cpu, value);
                return 8;
            }
            case 0xFE: {
                int value = cpu.memory.readByte(cpu.pc++);
                cp_a_r(cpu, value);
                return 8;
            }

            // --- LD (FF00 + C), A ---
            case 0xE2: {
                int addr = 0xFF00 | (cpu.register.c & 0xFF);
                cpu.memory.writeByte(addr, (byte) cpu.register.a);
                return 8;
            }

// --- LD A, (FF00 + C) ---
            case 0xF2: {
                int addr = 0xFF00 | (cpu.register.c & 0xFF);
                cpu.register.a = cpu.memory.readByte(addr);
                return 8;
            }

// --- LD (a16), A ---
            case 0xEA: {
                int lo = cpu.fetch();
                int hi = cpu.fetch();
                int addr = (hi << 8) | lo;
                cpu.memory.writeByte(addr, (byte) cpu.register.a);
                return 16;
            }

// --- LD A, (a16) ---
            case 0xFA: {
                int lo = cpu.fetch();
                int hi = cpu.fetch();
                int addr = (hi << 8) | lo;
                cpu.register.a = cpu.memory.readByte(addr);
                return 16;
            }

// --- LDI A, (HL) ---
            case 0x2A: {
                cpu.register.a = cpu.memory.readByte(cpu.register.getHL());
                cpu.register.setHL((cpu.register.getHL() + 1) & 0xFFFF);
                return 8;
            }

// --- LDI (HL), A ---
            case 0x22: {
                cpu.memory.writeByte(cpu.register.getHL(), (byte) cpu.register.a);
                cpu.register.setHL((cpu.register.getHL() + 1) & 0xFFFF);
                return 8;
            }

// --- LDD A, (HL) ---
            case 0x3A: {
                cpu.register.a = cpu.memory.readByte(cpu.register.getHL());
                cpu.register.setHL((cpu.register.getHL() - 1) & 0xFFFF);
                return 8;
            }

// --- LDD (HL), A ---
            case 0x32: {
                cpu.memory.writeByte(cpu.register.getHL(), (byte) cpu.register.a);
                cpu.register.setHL((cpu.register.getHL() - 1) & 0xFFFF);
                return 8;
            }

// --- LD r, (HL) ---
            case 0x46: cpu.register.b = cpu.memory.readByte(cpu.register.getHL()); return 8;
            case 0x4E: cpu.register.c = cpu.memory.readByte(cpu.register.getHL()); return 8;
            case 0x56: cpu.register.d = cpu.memory.readByte(cpu.register.getHL()); return 8;
            case 0x5E: cpu.register.e = cpu.memory.readByte(cpu.register.getHL()); return 8;
            case 0x66: cpu.register.h = cpu.memory.readByte(cpu.register.getHL()); return 8;
            case 0x6E: cpu.register.l = cpu.memory.readByte(cpu.register.getHL()); return 8;
            case 0x7E: cpu.register.a = cpu.memory.readByte(cpu.register.getHL()); return 8;

// --- LD (HL), r ---
            case 0x70: cpu.memory.writeByte(cpu.register.getHL(), (byte) cpu.register.b); return 8;
            case 0x71: cpu.memory.writeByte(cpu.register.getHL(), (byte) cpu.register.c); return 8;
            case 0x72: cpu.memory.writeByte(cpu.register.getHL(), (byte) cpu.register.d); return 8;
            case 0x73: cpu.memory.writeByte(cpu.register.getHL(), (byte) cpu.register.e); return 8;
            case 0x74: cpu.memory.writeByte(cpu.register.getHL(), (byte) cpu.register.h); return 8;
            case 0x75: cpu.memory.writeByte(cpu.register.getHL(), (byte) cpu.register.l); return 8;
            case 0x77: cpu.memory.writeByte(cpu.register.getHL(), (byte) cpu.register.a); return 8;

// --- LD r, d8 ---
            case 0x06: cpu.register.b = cpu.fetch(); return 8;
            case 0x0E: cpu.register.c = cpu.fetch(); return 8;
            case 0x16: cpu.register.d = cpu.fetch(); return 8;
            case 0x1E: cpu.register.e = cpu.fetch(); return 8;
            case 0x26: cpu.register.h = cpu.fetch(); return 8;
            case 0x2E: cpu.register.l = cpu.fetch(); return 8;
            case 0x3E: cpu.register.a = cpu.fetch(); return 8;

// --- LD (HL), d8 ---
            case 0x36: {
                int value = cpu.fetch();
                cpu.memory.writeByte(cpu.register.getHL(), (byte) value);
                return 12;
            }

// --- LD (a16), SP ---
            case 0x08: {
                int lo = cpu.fetch();
                int hi = cpu.fetch();
                int addr = (hi << 8) | lo;
                cpu.memory.writeByte(addr, (byte) (cpu.sp & 0xFF));
                cpu.memory.writeByte((addr + 1) & 0xFFFF, (byte) ((cpu.sp >> 8) & 0xFF));
                return 20;
            }

            // --- JP a16 ---
            case 0xC3: {
                int lo = cpu.fetch();
                int hi = cpu.fetch();
                cpu.pc = (hi << 8) | lo;
                return 16;
            }

// --- JP cc, a16 ---
            case 0xC2: { // JP NZ, a16
                int lo = cpu.fetch();
                int hi = cpu.fetch();
                int addr = (hi << 8) | lo;
                if (!cpu.register.getZ()) {
                    cpu.pc = addr;
                    return 16;
                }
                return 12;
            }
            case 0xCA: { // JP Z, a16
                int lo = cpu.fetch();
                int hi = cpu.fetch();
                int addr = (hi << 8) | lo;
                if (cpu.register.getZ()) {
                    cpu.pc = addr;
                    return 16;
                }
                return 12;
            }
            case 0xD2: { // JP NC, a16
                int lo = cpu.fetch();
                int hi = cpu.fetch();
                int addr = (hi << 8) | lo;
                if (!cpu.register.getC()) {
                    cpu.pc = addr;
                    return 16;
                }
                return 12;
            }
            case 0xDA: { // JP C, a16
                int lo = cpu.fetch();
                int hi = cpu.fetch();
                int addr = (hi << 8) | lo;
                if (cpu.register.getC()) {
                    cpu.pc = addr;
                    return 16;
                }
                return 12;
            }

// --- JR r8 ---
            case 0x18: {
                int offset = (byte) cpu.fetch();
                cpu.pc = (cpu.pc + offset) & 0xFFFF;
                return 12;
            }

// --- JR cc, r8 ---
            case 0x20: { // JR NZ, r8
                int offset = (byte) cpu.fetch();
                if (!cpu.register.getZ()) {
                    cpu.pc = (cpu.pc + offset) & 0xFFFF;
                    return 12;
                }
                return 8;
            }
            case 0x28: { // JR Z, r8
                int offset = (byte) cpu.fetch();
                if (cpu.register.getZ()) {
                    cpu.pc = (cpu.pc + offset) & 0xFFFF;
                    return 12;
                }
                return 8;
            }
            case 0x30: { // JR NC, r8
                int offset = (byte) cpu.fetch();
                if (!cpu.register.getC()) {
                    cpu.pc = (cpu.pc + offset) & 0xFFFF;
                    return 12;
                }
                return 8;
            }
            case 0x38: { // JR C, r8
                int offset = (byte) cpu.fetch();
                if (cpu.register.getC()) {
                    cpu.pc = (cpu.pc + offset) & 0xFFFF;
                    return 12;
                }
                return 8;
            }

// --- JP (HL) ---
            case 0xE9:
                cpu.pc = cpu.register.getHL();
                return 4;

// --- CALL a16 ---
            case 0xCD: {
                int lo = cpu.fetch();
                int hi = cpu.fetch();
                int addr = (hi << 8) | lo;
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) ((cpu.pc >> 8) & 0xFF));
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) (cpu.pc & 0xFF));
                cpu.pc = addr;
                return 24;
            }

// --- CALL cc, a16 ---
            case 0xC4: { // CALL NZ, a16
                int lo = cpu.fetch();
                int hi = cpu.fetch();
                int addr = (hi << 8) | lo;
                if (!cpu.register.getZ()) {
                    cpu.sp = (cpu.sp - 1) & 0xFFFF;
                    cpu.memory.writeByte(cpu.sp, (byte) ((cpu.pc >> 8) & 0xFF));
                    cpu.sp = (cpu.sp - 1) & 0xFFFF;
                    cpu.memory.writeByte(cpu.sp, (byte) (cpu.pc & 0xFF));
                    cpu.pc = addr;
                    return 24;
                }
                return 12;
            }
            case 0xCC: { // CALL Z, a16
                int lo = cpu.fetch();
                int hi = cpu.fetch();
                int addr = (hi << 8) | lo;
                if (cpu.register.getZ()) {
                    cpu.sp = (cpu.sp - 1) & 0xFFFF;
                    cpu.memory.writeByte(cpu.sp, (byte) ((cpu.pc >> 8) & 0xFF));
                    cpu.sp = (cpu.sp - 1) & 0xFFFF;
                    cpu.memory.writeByte(cpu.sp, (byte) (cpu.pc & 0xFF));
                    cpu.pc = addr;
                    return 24;
                }
                return 12;
            }
            case 0xD4: { // CALL NC, a16
                int lo = cpu.fetch();
                int hi = cpu.fetch();
                int addr = (hi << 8) | lo;
                if (!cpu.register.getC()) {
                    cpu.sp = (cpu.sp - 1) & 0xFFFF;
                    cpu.memory.writeByte(cpu.sp, (byte) ((cpu.pc >> 8) & 0xFF));
                    cpu.sp = (cpu.sp - 1) & 0xFFFF;
                    cpu.memory.writeByte(cpu.sp, (byte) (cpu.pc & 0xFF));
                    cpu.pc = addr;
                    return 24;
                }
                return 12;
            }
            case 0xDC: { // CALL C, a16
                int lo = cpu.fetch();
                int hi = cpu.fetch();
                int addr = (hi << 8) | lo;
                if (cpu.register.getC()) {
                    cpu.sp = (cpu.sp - 1) & 0xFFFF;
                    cpu.memory.writeByte(cpu.sp, (byte) ((cpu.pc >> 8) & 0xFF));
                    cpu.sp = (cpu.sp - 1) & 0xFFFF;
                    cpu.memory.writeByte(cpu.sp, (byte) (cpu.pc & 0xFF));
                    cpu.pc = addr;
                    return 24;
                }
                return 12;
            }

// --- RET ---
            case 0xC9: {
                int lo = cpu.memory.readByte(cpu.sp++) & 0xFF;
                int hi = cpu.memory.readByte(cpu.sp++) & 0xFF;
                cpu.pc = (hi << 8) | lo;
                return 16;
            }

// --- RET cc ---
            case 0xC0: { // RET NZ
                if (!cpu.register.getZ()) {
                    int lo = cpu.memory.readByte(cpu.sp++) & 0xFF;
                    int hi = cpu.memory.readByte(cpu.sp++) & 0xFF;
                    cpu.pc = (hi << 8) | lo;
                    return 20;
                }
                return 8;
            }
            case 0xC8: { // RET Z
                if (cpu.register.getZ()) {
                    int lo = cpu.memory.readByte(cpu.sp++) & 0xFF;
                    int hi = cpu.memory.readByte(cpu.sp++) & 0xFF;
                    cpu.pc = (hi << 8) | lo;
                    return 20;
                }
                return 8;
            }
            case 0xD0: { // RET NC
                if (!cpu.register.getC()) {
                    int lo = cpu.memory.readByte(cpu.sp++) & 0xFF;
                    int hi = cpu.memory.readByte(cpu.sp++) & 0xFF;
                    cpu.pc = (hi << 8) | lo;
                    return 20;
                }
                return 8;
            }
            case 0xD8: { // RET C
                if (cpu.register.getC()) {
                    int lo = cpu.memory.readByte(cpu.sp++) & 0xFF;
                    int hi = cpu.memory.readByte(cpu.sp++) & 0xFF;
                    cpu.pc = (hi << 8) | lo;
                    return 20;
                }
                return 8;
            }

// --- RETI ---
            case 0xD9: {
                int lo = cpu.memory.readByte(cpu.sp++) & 0xFF;
                int hi = cpu.memory.readByte(cpu.sp++) & 0xFF;
                cpu.pc = (hi << 8) | lo;
                cpu.ime = true; // enable interrupts
                return 16;
            }

// --- RST n ---
            case 0xC7: { // RST 00H
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) ((cpu.pc >> 8) & 0xFF));
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) (cpu.pc & 0xFF));
                cpu.pc = 0x00;
                return 16;
            }
            case 0xCF: { // RST 08H
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) ((cpu.pc >> 8) & 0xFF));
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) (cpu.pc & 0xFF));
                cpu.pc = 0x08;
                return 16;
            }
            case 0xD7: { // RST 10H
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) ((cpu.pc >> 8) & 0xFF));
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) (cpu.pc & 0xFF));
                cpu.pc = 0x10;
                return 16;
            }
            case 0xDF: { // RST 18H
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) ((cpu.pc >> 8) & 0xFF));
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) (cpu.pc & 0xFF));
                cpu.pc = 0x18;
                return 16;
            }
            case 0xE7: { // RST 20H
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) ((cpu.pc >> 8) & 0xFF));
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) (cpu.pc & 0xFF));
                cpu.pc = 0x20;
                return 16;
            }
            case 0xEF: { // RST 28H
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) ((cpu.pc >> 8) & 0xFF));
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) (cpu.pc & 0xFF));
                cpu.pc = 0x28;
                return 16;
            }
            case 0xF7: { // RST 30H
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) ((cpu.pc >> 8) & 0xFF));
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) (cpu.pc & 0xFF));
                cpu.pc = 0x30;
                return 16;
            }
            case 0xFF: { // RST 38H
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) ((cpu.pc >> 8) & 0xFF));
                cpu.sp = (cpu.sp - 1) & 0xFFFF;
                cpu.memory.writeByte(cpu.sp, (byte) (cpu.pc & 0xFF));
                cpu.pc = 0x38;
                return 16;
            }


            case 0xCB:
                int cbOpcode = cpu.memory.readByte(cpu.pc++);
                CBInstructions.execute(cpu, cbOpcode);
                return 8;

            default:
                System.out.printf("Unimplemented opcode: 0x%02X\n", opcode);
                break;
        }
        return 0;
    }
    //

    private static void ccf(CPU cpu) {
        boolean carry = (cpu.register.f & 0x10) != 0;
        setSubtractFlag(cpu, false);
        setHalfCarryFlag(cpu, false);
        setCarryFlag(cpu, !carry);
    }
    private static void scf(CPU cpu) {
        setSubtractFlag(cpu, false);
        setHalfCarryFlag(cpu, false);
        setCarryFlag(cpu, true);
    }
    private static void cpl(CPU cpu) {
        cpu.register.a ^= 0xFF;
        setSubtractFlag(cpu, true);
        setHalfCarryFlag(cpu, true);
    }
    private static void daa(CPU cpu) {
        int a = cpu.register.a;
        boolean n = (cpu.register.f & 0x40) != 0;
        boolean h = (cpu.register.f & 0x20) != 0;
        boolean c = (cpu.register.f & 0x10) != 0;

        int correction = 0;
        if (!n) {
            if (h || (a & 0x0F) > 9) correction |= 0x06;
            if (c || a > 0x99) {
                correction |= 0x60;
                setCarryFlag(cpu, true);
            }
            a = (a + correction) & 0xFF;
        } else {
            if (h) correction |= 0x06;
            if (c) correction |= 0x60;
            a = (a - correction) & 0xFF;
        }

        cpu.register.a = a;
        setZeroFlag(cpu, a == 0);
        setHalfCarryFlag(cpu, false); // always cleared
    }
    private static void rra(CPU cpu) {
        int a = cpu.register.a;
        int oldCarry = (cpu.register.f & 0x10) != 0 ? 1 : 0;
        int newCarry = a & 0x01;

        cpu.register.a = ((a >>> 1) | (oldCarry << 7)) & 0xFF;

        clearFlags(cpu);
        if (newCarry != 0) setCarryFlag(cpu, true);
    }
    private static void rla(CPU cpu) {
        int a = cpu.register.a;
        int oldCarry = (cpu.register.f & 0x10) != 0 ? 1 : 0;
        int newCarry = (a & 0x80) >> 7;

        cpu.register.a = ((a << 1) | oldCarry) & 0xFF;

        clearFlags(cpu);
        if (newCarry != 0) setCarryFlag(cpu, true);
    }
    private static void rrca(CPU cpu) {
        int a = cpu.register.a;
        int carry = a & 0x01;
        cpu.register.a = ((a >>> 1) | (carry << 7)) & 0xFF;

        clearFlags(cpu);
        if (carry != 0) setCarryFlag(cpu, true);
    }
    private static void rlca(CPU cpu) {
        int a = cpu.register.a;
        int carry = (a & 0x80) >> 7;
        cpu.register.a = ((a << 1) | carry) & 0xFF;

        clearFlags(cpu);
        if (carry != 0) setCarryFlag(cpu, true);
    }
    private static void addSPe8(CPU cpu) {
        int e8 = (byte) cpu.memory.readByte(cpu.pc++);  // Sign-extend the 8-bit immediate
        int sp = cpu.sp;
        int result = sp + e8;

        // Flags: Z = 0, N = 0
        cpu.register.f = 0;

        // Half-carry: carry from bit 3
        if (((sp & 0xF) + (e8 & 0xF)) > 0xF)
            cpu.register.f |= 0x20;  // H
        // Carry: carry from bit 7
        if (((sp & 0xFF) + (e8 & 0xFF)) > 0xFF)
            cpu.register.f |= 0x10;  // C

        cpu.sp = result & 0xFFFF;
    }
    private static void addHL(CPU cpu, int value) {
        int hl = cpu.register.getHL();
        int result = hl + value;

        cpu.register.setHL(result & 0xFFFF);

        // Preserve Z flag, clear N, set/reset H and C manually
        int flags = cpu.register.f & 0x80; // Keep Z flag
        flags &= 0x80; // Clear N

        if (((hl & 0xFFF) + (value & 0xFFF)) > 0xFFF)
            flags |= 0x20; // Set H
        if ((hl + value) > 0xFFFF)
            flags |= 0x10; // Set C

        cpu.register.f = flags;
    }
    private static void setCarryFlag(CPU cpu, boolean on) {
        cpu.register.f = on ? (cpu.register.f | 0x10) : (cpu.register.f & ~0x10);
    }
    private static void setZeroFlag(CPU cpu, boolean on) {
        cpu.register.f = on ? (cpu.register.f | 0x80) : (cpu.register.f & ~0x80);
    }

    private static void setSubtractFlag(CPU cpu, boolean on) {
        cpu.register.f = on ? (cpu.register.f | 0x40) : (cpu.register.f & ~0x40);
    }

    private static void setHalfCarryFlag(CPU cpu, boolean on) {
        cpu.register.f = on ? (cpu.register.f | 0x20) : (cpu.register.f & ~0x20);
    }

    private static void clearFlags(CPU cpu) {
        cpu.register.f = 0;
    }

    private static int inc8(CPU cpu, int value) {
        int result = (value + 1) & 0xFF;
        setZeroFlag(cpu, result == 0);
        setSubtractFlag(cpu, false);
        setHalfCarryFlag(cpu, (value & 0x0F) + 1 > 0x0F);
        return result;
    }

    private static int dec8(CPU cpu, int value) {
        int result = (value - 1) & 0xFF;
        setZeroFlag(cpu, result == 0);
        setSubtractFlag(cpu, true);
        setHalfCarryFlag(cpu, (value & 0x0F) == 0x00);
        return result;
    }
    public static void di(CPU cpu) {
        cpu.ime = false; // IME = Interrupt Master Enable
    }

    public static void ei(CPU cpu) {
        cpu.ime = true;
    }

    public static void halt(CPU cpu) {
        cpu.halted = true; // you may define this flag if handling HALT later
    }

    public static void stop(CPU cpu) {
        cpu.stopped = true; // for now, just mark it (Game Boy uses it rarely)
    }

    public static void ret_cc(CPU cpu, boolean condition) {
        if (condition) {
            int low = cpu.memory.readByte(cpu.sp++) & 0xFF;
            int high = cpu.memory.readByte(cpu.sp++) & 0xFF;
            cpu.pc = (high << 8) | low;
        }
    }

    public static void call_cc_a16(CPU cpu, boolean condition) {
        int low = cpu.memory.readByte(cpu.pc++) & 0xFF;
        int high = cpu.memory.readByte(cpu.pc++) & 0xFF;
        int addr = (high << 8) | low;

        if (condition) {
            cpu.sp--;
            cpu.memory.writeByte(cpu.sp, (byte)((cpu.pc >> 8) & 0xFF)); // high
            cpu.sp--;
            cpu.memory.writeByte(cpu.sp, (byte)(cpu.pc & 0xFF));        // low
            cpu.pc = addr;
        }
    }

    public static void jr_cc_r8(CPU cpu, boolean condition) {
        int offset = cpu.memory.readByte(cpu.pc++) & 0xFF;
        if (condition) {
            if (offset >= 0x80) offset -= 0x100; // signed
            cpu.pc += offset;
        }
    }
    public static void jp_cc_a16(CPU cpu, boolean condition) {
        int low = cpu.memory.readByte(cpu.pc++) & 0xFF;
        int high = cpu.memory.readByte(cpu.pc++) & 0xFF;
        int addr = (high << 8) | low;

        if (condition) {
            cpu.pc = addr;
        }
    }

    public static void rst(CPU cpu, int address) {
        int ret = cpu.pc;
        cpu.memory.writeByte(--cpu.sp, (byte)((ret >> 8) & 0xFF)); // High
        cpu.memory.writeByte(--cpu.sp, (byte)(ret & 0xFF));        // Low
        cpu.pc = address;
    }
    private static void jp_a16(CPU cpu) {
        int lo = cpu.memory.readByte(cpu.pc) & 0xFF;
        cpu.pc++;
        int hi = cpu.memory.readByte(cpu.pc) & 0xFF;
        cpu.pc++;
        cpu.pc = (hi << 8) | lo;
        cpu.cycles += 16;
    }
    public static void reti(CPU cpu) {
        int low = cpu.memory.readByte(cpu.sp++) & 0xFF;
        int high = cpu.memory.readByte(cpu.sp++) & 0xFF;
        cpu.pc = (high << 8) | low;
        cpu.ime = true;
    }
    public static void ret(CPU cpu) {
        int low = cpu.memory.readByte(cpu.sp++) & 0xFF;
        int high = cpu.memory.readByte(cpu.sp++) & 0xFF;
        cpu.pc = (high << 8) | low;
    }

    public static void call_a16(CPU cpu)
    {
        int l = cpu.memory.readByte(cpu.pc++)&0xFF;
        int h = cpu.memory.readByte(cpu.pc++)&0xFF;
        int addr = (h<<8)|l;

        cpu.memory.writeByte(--cpu.sp,(byte) ((addr>>8) & 0xFF));
        cpu.memory.writeByte(--cpu.sp,(byte) (addr & 0xFF));
        cpu.pc = addr;
    }

    public static void jr_8(CPU cpu)
    {
        byte offset = (byte) cpu.memory.readByte(cpu.pc++);
        cpu.pc = (cpu.pc+offset)&0xFFFF;
    }


    //cp
    public static void cp_a_r(CPU cpu, int value) {
        int result = cpu.register.a - value;
        cpu.register.f = 0;
        if ((result & 0xFF) == 0) cpu.register.f |= 0x80; // Z
        cpu.register.f |= 0x40; // N
        if ((cpu.register.a & 0xF) < (value & 0xF)) cpu.register.f |= 0x20; // H
        if (cpu.register.a < value) cpu.register.f |= 0x10; // C
    }
    //or
    public static void or_a_r(CPU cpu, int value) {
        cpu.register.a |= value;
        cpu.register.f = 0;
        if (cpu.register.a == 0) cpu.register.f |= 0x80; // Z
    }
    //and
    public static void and_a_r(CPU cpu, int value) {
        cpu.register.a &= value;
        cpu.register.f = 0;
        if (cpu.register.a == 0) cpu.register.f |= 0x80; // Z
        cpu.register.f |= 0x20; // H
    }

    // xor
    private static void xor_a_r(CPU cpu, int value) {
        cpu.register.a = cpu.register.a ^ value;
        cpu.register.a &= 0xFF;

        cpu.register.f = 0; // Clear all flags
        if (cpu.register.a == 0) cpu.register.f |= 0x80; // Set Z flag if result is 0

        cpu.cycles += 4;
    }
    //add A r
    public static void add_a_r(CPU cpu, int value) {
        int result = cpu.register.a + value;
        cpu.register.f = 0;
        if ((result & 0xFF) == 0) cpu.register.f |= 0x80;         // Z
        if (((cpu.register.a & 0xF) + (value & 0xF)) > 0xF) cpu.register.f |= 0x20; // H
        if (result > 0xFF) cpu.register.f |= 0x10;                // C
        cpu.register.a = result & 0xFF;
    }

    //Adc A r
    public static void adc_a_r(CPU cpu, IntSupplier r) {
        int a = cpu.register.a;
        int value = r.getAsInt();
        int carry = (cpu.register.f & 0x10) != 0 ? 1 : 0;

        int result = a + value + carry;

        cpu.register.a = result & 0xFF;

        cpu.register.f = 0; // Clear all flags
        if ((result & 0xFF) == 0) cpu.register.f |= 0x80; // Z
        if (((a & 0xF) + (value & 0xF) + carry) > 0xF) cpu.register.f |= 0x20; // H
        if (result > 0xFF) cpu.register.f |= 0x10; // C
    }

    // sub a r
    public static void sub_a_r(CPU cpu, int value) {
        int result = cpu.register.a - value;
        cpu.register.f = 0; // Clear all flags

        if ((result & 0xFF) == 0) cpu.register.f |= 0x80; // Z (Zero)
        cpu.register.f |= 0x40; // N (Subtract flag always set)
        if ((cpu.register.a & 0xF) < (value & 0xF)) cpu.register.f |= 0x20; // H (Half-carry)
        if (cpu.register.a < value) cpu.register.f |= 0x10; // C (Carry/borrow)

        cpu.register.a = result & 0xFF; // Store result in A (truncate to 8 bits)
    }

    // sbc ar
    public static void sbc_a_r(CPU cpu, int value) {
        int carry = (cpu.register.f & 0x10) != 0 ? 1 : 0;
        int result = cpu.register.a - value - carry;

        cpu.register.f = 0;
        if ((result & 0xFF) == 0) cpu.register.f |= 0x80; // Z
        cpu.register.f |= 0x40; // N
        if ((cpu.register.a & 0xF) < ((value & 0xF) + carry)) cpu.register.f |= 0x20; // H
        if (cpu.register.a < (value + carry)) cpu.register.f |= 0x10; // C

        cpu.register.a = result & 0xFF;
    }


    //Load r d8
    public static void ld_r_d8(CPU cpu, BiConsumer<CPU, Integer> registerSetter) {
        int value = cpu.memory.readByte(cpu.pc);
        cpu.pc++;
        registerSetter.accept(cpu, value);
    }

    // a
    public static void ld_a16_a(CPU cpu) {
        int low = cpu.memory.readByte(cpu.pc++) & 0xFF;
        int high = cpu.memory.readByte(cpu.pc++) & 0xFF;
        int addr = (high << 8) | low;
        cpu.memory.writeByte(addr, (byte)cpu.register.a);
    }
    public static void ld_a_a16(CPU cpu) {
        int low = cpu.memory.readByte(cpu.pc++) & 0xFF;
        int high = cpu.memory.readByte(cpu.pc++) & 0xFF;
        int addr = (high << 8) | low;

        cpu.register.a = cpu.memory.readByte(addr) & 0xFF;
    }
    //ld hl r
    public static void ld_hl_r(CPU cpu, Function<CPU, Integer> regGetter) {
        int addr = cpu.register.getHL();
        cpu.memory.writeByte(addr, (byte)(regGetter.apply(cpu) & 0xFF));
    }

    // ld r hl
    public static void ld_r_hl(CPU cpu, BiConsumer<CPU, Integer> regSetter) {
        int addr = cpu.register.getHL();
        int value = cpu.memory.readByte(addr);
        regSetter.accept(cpu, value);
    }

    // LD A, (BC)
    public static void ld_a_bc(CPU cpu) {
        int addr = cpu.register.getBC();
        cpu.register.a = cpu.memory.readByte(addr);
    }

    // LD A, (DE)
    public static void ld_a_de(CPU cpu) {
        int addr = cpu.register.getDE();
        cpu.register.a = cpu.memory.readByte(addr);
    }

    // LD (BC), A
    public static void ld_bc_a(CPU cpu) {
        int addr = cpu.register.getBC();
        cpu.memory.writeByte(addr, (byte)cpu.register.a);
    }

    // LD (DE), A
    public static void ld_de_a(CPU cpu) {
        int addr = cpu.register.getDE();
        cpu.memory.writeByte(addr, (byte)cpu.register.a);
    }

    // LD A, (C) → [$FF00 + C]
    public static void ld_a_c_io(CPU cpu) {
        int addr = 0xFF00 + cpu.register.c;
        cpu.register.a = cpu.memory.readByte(addr);
    }

    // LD (C), A → [$FF00 + C] = A
    public static void ld_c_a_io(CPU cpu) {
        int addr = 0xFF00 + cpu.register.c;
        cpu.memory.writeByte(addr, (byte)cpu.register.a);
    }

    // LD A, (HL+)
    public static void ld_a_hlp(CPU cpu) {
        cpu.register.a = cpu.memory.readByte(cpu.register.getHL());
        cpu.register.setHL(cpu.register.getHL() + 1);
    }

    // LD (HL+), A
    public static void ld_hlp_a(CPU cpu) {
        cpu.memory.writeByte(cpu.register.getHL(), (byte)cpu.register.a);
        cpu.register.setHL(cpu.register.getHL() + 1);
    }

    // LD A, (HL-)
    public static void ld_a_hlm(CPU cpu) {
        cpu.register.a = cpu.memory.readByte(cpu.register.getHL());
        cpu.register.setHL(cpu.register.getHL() - 1);
    }

    // LD (HL-), A
    public static void ld_hlm_a(CPU cpu) {
        cpu.memory.writeByte(cpu.register.getHL(), (byte)cpu.register.a);
        cpu.register.setHL(cpu.register.getHL() - 1);
    }

    // LD HL, SP+e8
    public static void ld_hl_sp_plus_e8(CPU cpu) {
        byte e8 = (byte)cpu.memory.readByte(cpu.pc++);
        int result = cpu.sp + e8;
        cpu.register.setHL(result & 0xFFFF);

        cpu.register.f = 0;
        if (((cpu.sp ^ e8 ^ (result & 0xFFFF)) & 0x10) != 0) cpu.register.f |= 0x20; // H
        if (((cpu.sp ^ e8 ^ (result & 0xFFFF)) & 0x100) != 0) cpu.register.f |= 0x10; // C
    }

    // LD SP, HL
    public static void ld_sp_hl(CPU cpu) {
        cpu.sp = cpu.register.getHL();
    }

    // LD (a16), SP
    public static void ld_a16_sp(CPU cpu) {
        int low = cpu.memory.readByte(cpu.pc++) & 0xFF;
        int high = cpu.memory.readByte(cpu.pc++) & 0xFF;
        int addr = (high << 8) | low;
        cpu.memory.writeByte(addr, (byte)(cpu.sp & 0xFF));
        cpu.memory.writeByte(addr + 1, (byte)((cpu.sp >> 8) & 0xFF));
    }






    private static void nop(CPU cpu){cpu.cycles+=4;}
}
