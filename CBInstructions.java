public class CBInstructions {
    public static void execute(CPU cpu , int opcode)
    {
        switch (opcode)
        {
            case 0x00: cpu.register.b = rlc(cpu.register.b, cpu); break;
            case 0x01: cpu.register.c = rlc(cpu.register.c, cpu); break;
            case 0x02: cpu.register.d = rlc(cpu.register.d, cpu); break;
            case 0x03: cpu.register.e = rlc(cpu.register.e, cpu); break;
            case 0x04: cpu.register.h = rlc(cpu.register.h, cpu); break;
            case 0x05: cpu.register.l = rlc(cpu.register.l, cpu); break;
            case 0x06: {
                int addr = cpu.register.getHL();
                int result = rlc(cpu.memory.readByte(addr), cpu);
                cpu.memory.writeByte(addr, (byte) result);
                break;
            }
            case 0x07: cpu.register.a = rlc(cpu.register.a, cpu); break;
            //
            case 0x08: cpu.register.b = rrc(cpu.register.b, cpu); break;
            case 0x09: cpu.register.c = rrc(cpu.register.c, cpu); break;
            case 0x0A: cpu.register.d = rrc(cpu.register.d, cpu); break;
            case 0x0B: cpu.register.e = rrc(cpu.register.e, cpu); break;
            case 0x0C: cpu.register.h = rrc(cpu.register.h, cpu); break;
            case 0x0D: cpu.register.l = rrc(cpu.register.l, cpu); break;
            case 0x0E: {
                int addr = cpu.register.getHL();
                int result = rrc(cpu.memory.readByte(addr), cpu);
                cpu.memory.writeByte(addr, (byte) result);
                break;
            }
            case 0x0F: cpu.register.a = rrc(cpu.register.a, cpu); break;
            //
            case 0x18: cpu.register.b = rr(cpu.register.b, cpu); break;
            case 0x19: cpu.register.c = rr(cpu.register.c, cpu); break;
            case 0x1A: cpu.register.d = rr(cpu.register.d, cpu); break;
            case 0x1B: cpu.register.e = rr(cpu.register.e, cpu); break;
            case 0x1C: cpu.register.h = rr(cpu.register.h, cpu); break;
            case 0x1D: cpu.register.l = rr(cpu.register.l, cpu); break;
            case 0x1E: {
                int addr = cpu.register.getHL();
                int result = rr(cpu.memory.readByte(addr), cpu);
                cpu.memory.writeByte(addr, (byte) result);
                break;
            }
            case 0x1F: cpu.register.a = rr(cpu.register.a, cpu); break;
            //
            case 0x10: cpu.register.b = rl(cpu.register.b, cpu); break;
            case 0x11: cpu.register.c = rl(cpu.register.c, cpu); break;
            case 0x12: cpu.register.d = rl(cpu.register.d, cpu); break;
            case 0x13: cpu.register.e = rl(cpu.register.e, cpu); break;
            case 0x14: cpu.register.h = rl(cpu.register.h, cpu); break;
            case 0x15: cpu.register.l = rl(cpu.register.l, cpu); break;
            case 0x16: {
                int addr = cpu.register.getHL();
                int result = rl(cpu.memory.readByte(addr), cpu);
                cpu.memory.writeByte(addr, (byte) result);
                break;
            }
            case 0x17: cpu.register.a = rl(cpu.register.a, cpu); break;
            //
            case 0x20: cpu.register.b = sla(cpu.register.b, cpu); break;
            case 0x21: cpu.register.c = sla(cpu.register.c, cpu); break;
            case 0x22: cpu.register.d = sla(cpu.register.d, cpu); break;
            case 0x23: cpu.register.e = sla(cpu.register.e, cpu); break;
            case 0x24: cpu.register.h = sla(cpu.register.h, cpu); break;
            case 0x25: cpu.register.l = sla(cpu.register.l, cpu); break;
            case 0x26: {
                int addr = cpu.register.getHL();
                int result = sla(cpu.memory.readByte(addr), cpu);
                cpu.memory.writeByte(addr, (byte) result);
                break;
            }
            case 0x27: cpu.register.a = sla(cpu.register.a, cpu); break;
            //
            case 0x28: cpu.register.b = sra(cpu.register.b, cpu); break;
            case 0x29: cpu.register.c = sra(cpu.register.c, cpu); break;
            case 0x2A: cpu.register.d = sra(cpu.register.d, cpu); break;
            case 0x2B: cpu.register.e = sra(cpu.register.e, cpu); break;
            case 0x2C: cpu.register.h = sra(cpu.register.h, cpu); break;
            case 0x2D: cpu.register.l = sra(cpu.register.l, cpu); break;
            case 0x2E: {
                int addr = cpu.register.getHL();
                int result = sra(cpu.memory.readByte(addr), cpu);
                cpu.memory.writeByte(addr, (byte) result);
                break;
            }
            case 0x2F: cpu.register.a = sra(cpu.register.a, cpu); break;
            //
            case 0x38: cpu.register.b = srl(cpu.register.b, cpu); break;
            case 0x39: cpu.register.c = srl(cpu.register.c, cpu); break;
            case 0x3A: cpu.register.d = srl(cpu.register.d, cpu); break;
            case 0x3B: cpu.register.e = srl(cpu.register.e, cpu); break;
            case 0x3C: cpu.register.h = srl(cpu.register.h, cpu); break;
            case 0x3D: cpu.register.l = srl(cpu.register.l, cpu); break;
            case 0x3E: {
                int addr = cpu.register.getHL();
                int result = srl(cpu.memory.readByte(addr), cpu);
                cpu.memory.writeByte(addr, (byte) result);
                break;
            }
            case 0x3F: cpu.register.a = srl(cpu.register.a, cpu); break;
            //
            case 0x30: cpu.register.b = swap(cpu.register.b, cpu); break;
            case 0x31: cpu.register.c = swap(cpu.register.c, cpu); break;
            case 0x32: cpu.register.d = swap(cpu.register.d, cpu); break;
            case 0x33: cpu.register.e = swap(cpu.register.e, cpu); break;
            case 0x34: cpu.register.h = swap(cpu.register.h, cpu); break;
            case 0x35: cpu.register.l = swap(cpu.register.l, cpu); break;
            case 0x36: {
                int addr = cpu.register.getHL();
                int result = swap(cpu.memory.readByte(addr), cpu);
                cpu.memory.writeByte(addr, (byte) result);
                break;
            }
            case 0x37: cpu.register.a = swap(cpu.register.a, cpu); break;
            //bit
            case 0x40: cpu.register.b = bit(cpu.register.b, 0, cpu); break;
            case 0x41: cpu.register.c = bit(cpu.register.c, 0, cpu); break;
            case 0x42: cpu.register.d = bit(cpu.register.d, 0, cpu); break;
            case 0x43: cpu.register.e = bit(cpu.register.e, 0, cpu); break;
            case 0x44: cpu.register.h = bit(cpu.register.h, 0, cpu); break;
            case 0x45: cpu.register.l = bit(cpu.register.l, 0, cpu); break;
            case 0x46: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                bit(value, 0, cpu);
                break;
            }
            case 0x47: cpu.register.a = bit(cpu.register.a, 0, cpu); break;
            //bit 1
            case 0x48: bit(cpu.register.b, 1, cpu); break;
            case 0x49: bit(cpu.register.c, 1, cpu); break;
            case 0x4A: bit(cpu.register.d, 1, cpu); break;
            case 0x4B: bit(cpu.register.e, 1, cpu); break;
            case 0x4C: bit(cpu.register.h, 1, cpu); break;
            case 0x4D: bit(cpu.register.l, 1, cpu); break;
            case 0x4E: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                bit(value, 1, cpu);
                break;
            }
            case 0x4F: bit(cpu.register.a, 1, cpu); break;
            //bit2
            case 0x50: bit(cpu.register.b, 2, cpu); break;
            case 0x51: bit(cpu.register.c, 2, cpu); break;
            case 0x52: bit(cpu.register.d, 2, cpu); break;
            case 0x53: bit(cpu.register.e, 2, cpu); break;
            case 0x54: bit(cpu.register.h, 2, cpu); break;
            case 0x55: bit(cpu.register.l, 2, cpu); break;
            case 0x56: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                bit(value, 2, cpu);
                break;
            }
            case 0x57: bit(cpu.register.a, 2, cpu); break;
            //
            case 0x58: bit(cpu.register.b, 3, cpu); break;
            case 0x59: bit(cpu.register.c, 3, cpu); break;
            case 0x5A: bit(cpu.register.d, 3, cpu); break;
            case 0x5B: bit(cpu.register.e, 3, cpu); break;
            case 0x5C: bit(cpu.register.h, 3, cpu); break;
            case 0x5D: bit(cpu.register.l, 3, cpu); break;
            case 0x5E: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                bit(value, 3, cpu);
                break;
            }
            case 0x5F: bit(cpu.register.a, 3, cpu); break;
            //4
            case 0x60: bit(cpu.register.b, 4, cpu); break;
            case 0x61: bit(cpu.register.c, 4, cpu); break;
            case 0x62: bit(cpu.register.d, 4, cpu); break;
            case 0x63: bit(cpu.register.e, 4, cpu); break;
            case 0x64: bit(cpu.register.h, 4, cpu); break;
            case 0x65: bit(cpu.register.l, 4, cpu); break;
            case 0x66: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                bit(value, 4, cpu);
                break;
            }
            case 0x67: bit(cpu.register.a, 4, cpu); break;
            //5
            case 0x68: bit(cpu.register.b, 5, cpu); break;
            case 0x69: bit(cpu.register.c, 5, cpu); break;
            case 0x6A: bit(cpu.register.d, 5, cpu); break;
            case 0x6B: bit(cpu.register.e, 5, cpu); break;
            case 0x6C: bit(cpu.register.h, 5, cpu); break;
            case 0x6D: bit(cpu.register.l, 5, cpu); break;
            case 0x6E: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                bit(value, 5, cpu);
                break;
            }
            case 0x6F: bit(cpu.register.a, 5, cpu); break;
            //6
            case 0x70: bit(cpu.register.b, 6, cpu); break;
            case 0x71: bit(cpu.register.c, 6, cpu); break;
            case 0x72: bit(cpu.register.d, 6, cpu); break;
            case 0x73: bit(cpu.register.e, 6, cpu); break;
            case 0x74: bit(cpu.register.h, 6, cpu); break;
            case 0x75: bit(cpu.register.l, 6, cpu); break;
            case 0x76: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                bit(value, 6, cpu);
                break;
            }
            case 0x77: bit(cpu.register.a, 6, cpu); break;
            //7
            case 0x78: bit(cpu.register.b, 7, cpu); break;
            case 0x79: bit(cpu.register.c, 7, cpu); break;
            case 0x7A: bit(cpu.register.d, 7, cpu); break;
            case 0x7B: bit(cpu.register.e, 7, cpu); break;
            case 0x7C: bit(cpu.register.h, 7, cpu); break;
            case 0x7D: bit(cpu.register.l, 7, cpu); break;
            case 0x7E: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                bit(value, 7, cpu);
                break;
            }
            case 0x7F: bit(cpu.register.a, 7, cpu); break;
            //
             //
             //all reset to 0
            case 0x80: cpu.register.b &= ~(1 << 0); break;
            case 0x81: cpu.register.c &= ~(1 << 0); break;
            case 0x82: cpu.register.d &= ~(1 << 0); break;
            case 0x83: cpu.register.e &= ~(1 << 0); break;
            case 0x84: cpu.register.h &= ~(1 << 0); break;
            case 0x85: cpu.register.l &= ~(1 << 0); break;
            case 0x86: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                value &= ~(1 << 0);
                cpu.memory.writeByte(addr, (byte)value);
                break;
            }
            case 0x87: cpu.register.a &= ~(1 << 0); break;
            //
            case 0x88: cpu.register.b &= ~(1 << 1); break;
            case 0x89: cpu.register.c &= ~(1 << 1); break;
            case 0x8A: cpu.register.d &= ~(1 << 1); break;
            case 0x8B: cpu.register.e &= ~(1 << 1); break;
            case 0x8C: cpu.register.h &= ~(1 << 1); break;
            case 0x8D: cpu.register.l &= ~(1 << 1); break;
            case 0x8E: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                value &= ~(1 << 1);
                cpu.memory.writeByte(addr, (byte)value);
                break;
            }
            case 0x8F: cpu.register.a &= ~(1 << 1); break;
            //
            case 0x90: cpu.register.b &= ~(1 << 2); break;
            case 0x91: cpu.register.c &= ~(1 << 2); break;
            case 0x92: cpu.register.d &= ~(1 << 2); break;
            case 0x93: cpu.register.e &= ~(1 << 2); break;
            case 0x94: cpu.register.h &= ~(1 << 2); break;
            case 0x95: cpu.register.l &= ~(1 << 2); break;
            case 0x96: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                value &= ~(1 << 2);
                cpu.memory.writeByte(addr, (byte)value);
                break;
            }
            case 0x97: cpu.register.a &= ~(1 << 2); break;
            //
            case 0x98: cpu.register.b &= ~(1 << 3); break;
            case 0x99: cpu.register.c &= ~(1 << 3); break;
            case 0x9A: cpu.register.d &= ~(1 << 3); break;
            case 0x9B: cpu.register.e &= ~(1 << 3); break;
            case 0x9C: cpu.register.h &= ~(1 << 3); break;
            case 0x9D: cpu.register.l &= ~(1 << 3); break;
            case 0x9E: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                value &= ~(1 << 3);
                cpu.memory.writeByte(addr, (byte)value);
                break;
            }
            case 0x9F: cpu.register.a &= ~(1 << 3); break;

            //
            case 0xA0: cpu.register.b &= ~(1 << 4); break;
            case 0xA1: cpu.register.c &= ~(1 << 4); break;
            case 0xA2: cpu.register.d &= ~(1 << 4); break;
            case 0xA3: cpu.register.e &= ~(1 << 4); break;
            case 0xA4: cpu.register.h &= ~(1 << 4); break;
            case 0xA5: cpu.register.l &= ~(1 << 4); break;
            case 0xA6: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                value &= ~(1 << 4);
                cpu.memory.writeByte(addr, (byte)value);
                break;
            }
            case 0xA7: cpu.register.a &= ~(1 << 4); break;

            //
            case 0xA8: cpu.register.b &= ~(1 << 5); break;
            case 0xA9: cpu.register.c &= ~(1 << 5); break;
            case 0xAA: cpu.register.d &= ~(1 << 5); break;
            case 0xAB: cpu.register.e &= ~(1 << 5); break;
            case 0xAC: cpu.register.h &= ~(1 << 5); break;
            case 0xAD: cpu.register.l &= ~(1 << 5); break;
            case 0xAE: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                value &= ~(1 << 5);
                cpu.memory.writeByte(addr, (byte)value);
                break;
            }
            case 0xAF: cpu.register.a &= ~(1 << 5); break;
            //
            case 0xB0: cpu.register.b &= ~(1 << 6); break;
            case 0xB1: cpu.register.c &= ~(1 << 6); break;
            case 0xB2: cpu.register.d &= ~(1 << 6); break;
            case 0xB3: cpu.register.e &= ~(1 << 6); break;
            case 0xB4: cpu.register.h &= ~(1 << 6); break;
            case 0xB5: cpu.register.l &= ~(1 << 6); break;
            case 0xB6: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                value &= ~(1 << 6);
                cpu.memory.writeByte(addr, (byte)value);
                break;
            }
            case 0xB7: cpu.register.a &= ~(1 << 6); break;
            //
            case 0xB8: cpu.register.b &= ~(1 << 7); break;
            case 0xB9: cpu.register.c &= ~(1 << 7); break;
            case 0xBA: cpu.register.d &= ~(1 << 7); break;
            case 0xBB: cpu.register.e &= ~(1 << 7); break;
            case 0xBC: cpu.register.h &= ~(1 << 7); break;
            case 0xBD: cpu.register.l &= ~(1 << 7); break;
            case 0xBE: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                value &= ~(1 << 7);
                cpu.memory.writeByte(addr, (byte)value);
                break;
            }
            case 0xBF: cpu.register.a &= ~(1 << 7); break;
            // all reset done

            //now set bits
            case 0xC0: cpu.register.b |= (1 << 0); break;
            case 0xC1: cpu.register.c |= (1 << 0); break;
            case 0xC2: cpu.register.d |= (1 << 0); break;
            case 0xC3: cpu.register.e |= (1 << 0); break;
            case 0xC4: cpu.register.h |= (1 << 0); break;
            case 0xC5: cpu.register.l |= (1 << 0); break;
            case 0xC6: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                value |= (1 << 0);
                cpu.memory.writeByte(addr, (byte)value);
                break;
            }
            case 0xC7: cpu.register.a |= (1 << 0); break;
            //
            case 0xC8: cpu.register.b |= (1 << 1); break;
            case 0xC9: cpu.register.c |= (1 << 1); break;
            case 0xCA: cpu.register.d |= (1 << 1); break;
            case 0xCB: cpu.register.e |= (1 << 1); break;
            case 0xCC: cpu.register.h |= (1 << 1); break;
            case 0xCD: cpu.register.l |= (1 << 1); break;
            case 0xCE: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                value |= (1 << 1);
                cpu.memory.writeByte(addr, (byte)value);
                break;
            }
            case 0xCF: cpu.register.a |= (1 << 1); break;
            //
            case 0xD0: cpu.register.b |= (1 << 2); break;
            case 0xD1: cpu.register.c |= (1 << 2); break;
            case 0xD2: cpu.register.d |= (1 << 2); break;
            case 0xD3: cpu.register.e |= (1 << 2); break;
            case 0xD4: cpu.register.h |= (1 << 2); break;
            case 0xD5: cpu.register.l |= (1 << 2); break;
            case 0xD6: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                value |= (1 << 2);
                cpu.memory.writeByte(addr, (byte)value);
                break;
            }
            case 0xD7: cpu.register.a |= (1 << 2); break;
            //
            case 0xD8: cpu.register.b |= (1 << 3); break;
            case 0xD9: cpu.register.c |= (1 << 3); break;
            case 0xDA: cpu.register.d |= (1 << 3); break;
            case 0xDB: cpu.register.e |= (1 << 3); break;
            case 0xDC: cpu.register.h |= (1 << 3); break;
            case 0xDD: cpu.register.l |= (1 << 3); break;
            case 0xDE: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                value |= (1 << 3);
                cpu.memory.writeByte(addr, (byte)value);
                break;
            }
            case 0xDF: cpu.register.a |= (1 << 3); break;
            //
            case 0xE0: cpu.register.b |= (1 << 4); break;
            case 0xE1: cpu.register.c |= (1 << 4); break;
            case 0xE2: cpu.register.d |= (1 << 4); break;
            case 0xE3: cpu.register.e |= (1 << 4); break;
            case 0xE4: cpu.register.h |= (1 << 4); break;
            case 0xE5: cpu.register.l |= (1 << 4); break;
            case 0xE6: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                value |= (1 << 4);
                cpu.memory.writeByte(addr, (byte)value);
                break;
            }
            case 0xE7: cpu.register.a |= (1 << 4); break;
            //
            case 0xE8: cpu.register.b |= (1 << 5); break;
            case 0xE9: cpu.register.c |= (1 << 5); break;
            case 0xEA: cpu.register.d |= (1 << 5); break;
            case 0xEB: cpu.register.e |= (1 << 5); break;
            case 0xEC: cpu.register.h |= (1 << 5); break;
            case 0xED: cpu.register.l |= (1 << 5); break;
            case 0xEE: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                value |= (1 << 5);
                cpu.memory.writeByte(addr, (byte)value);
                break;
            }
            case 0xEF: cpu.register.a |= (1 << 5); break;
            //
            case 0xF0: cpu.register.b |= (1 << 6); break;
            case 0xF1: cpu.register.c |= (1 << 6); break;
            case 0xF2: cpu.register.d |= (1 << 6); break;
            case 0xF3: cpu.register.e |= (1 << 6); break;
            case 0xF4: cpu.register.h |= (1 << 6); break;
            case 0xF5: cpu.register.l |= (1 << 6); break;
            case 0xF6: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                value |= (1 << 6);
                cpu.memory.writeByte(addr, (byte)value);
                break;
            }
            case 0xF7: cpu.register.a |= (1 << 6); break;

            //
            case 0xF8: cpu.register.b |= (1 << 7); break;
            case 0xF9: cpu.register.c |= (1 << 7); break;
            case 0xFA: cpu.register.d |= (1 << 7); break;
            case 0xFB: cpu.register.e |= (1 << 7); break;
            case 0xFC: cpu.register.h |= (1 << 7); break;
            case 0xFD: cpu.register.l |= (1 << 7); break;
            case 0xFE: {
                int addr = cpu.register.getHL();
                int value = cpu.memory.readByte(addr) & 0xFF;
                value |= (1 << 7);
                cpu.memory.writeByte(addr, (byte)value);
                break;
            }
            case 0xFF: cpu.register.a |= (1 << 7); break;
            // all set done
            // with this all cb done




        }

    }

    //
     //bit
    private static int bit(int value, int bitIndex, CPU cpu) {
        boolean isZero = ((value >> bitIndex) & 1) == 0;
        cpu.register.f &= 0b00010000; // Preserve only Carry (C), clear others
        cpu.register.f |= 0b00100000; // Set Half-carry (H)
        if (isZero) cpu.register.f |= 0b10000000; // Set Zero (Z) if bit is 0
        return value;
    }


/// //
    private static int swap(int value, CPU cpu) {
        int result = ((value & 0xF) << 4) | ((value & 0xF0) >> 4);
        clearFlags(cpu);
        if (result == 0) setZeroFlag(cpu, result);
        return result;
    }

    private static int srl(int value, CPU cpu) {
        int carry = value & 0x01;
        int result = (value >> 1) & 0x7F;

        clearFlags(cpu);
        if (result == 0) setZeroFlag(cpu, result);
        if (carry == 1) setCarryFlag(cpu);

        return result;
    }

    private static int sra(int value, CPU cpu) {
        int carry = value & 0x01;
        int msb = value & 0x80;
        int result = (value >> 1) | msb;

        clearFlags(cpu);
        if (result == 0) setZeroFlag(cpu, result);
        if (carry == 1) setCarryFlag(cpu);

        return result;
    }

    private static int sla(int value, CPU cpu) {
        int carry = (value >> 7) & 0x1;
        int result = (value << 1) & 0xFF;

        clearFlags(cpu);
        if (result == 0) setZeroFlag(cpu, result);
        if (carry == 1) setCarryFlag(cpu);

        return result;
    }
    private static void setZeroFlag(CPU cpu, int value) {
        if (value == 0) cpu.register.f |= 0x80; // Z
    }

    private static void setCarryFlag(CPU cpu) {
        cpu.register.f |= 0x10; // C
    }

    private static void clearFlags(CPU cpu) {
        cpu.register.f = 0;
    }

    private static int rlc(int value, CPU cpu) {
        int carry = (value >> 7) & 0x1;
        int result = ((value << 1) | carry) & 0xFF;
        clearFlags(cpu);
        if (result == 0) setZeroFlag(cpu, result);
        if (carry == 1) setCarryFlag(cpu);
        return result;
    }
    private static int rrc(int value, CPU cpu) {
        int carry = value & 0x1;
        int result = ((value >>> 1) | (carry << 7)) & 0xFF;
        clearFlags(cpu);
        if (result == 0) setZeroFlag(cpu, result);
        if (carry == 1) setCarryFlag(cpu);
        return result;
    }
    private static int rr(int value, CPU cpu) {
        int oldCarry = (cpu.register.f & 0x10) != 0 ? 1 : 0; // Get carry flag
        int newCarry = value & 0x1; // Bit 0 becomes new carry
        int result = (value >> 1) | (oldCarry << 7); // Carry into bit 7
        clearFlags(cpu);
        if (result == 0) setZeroFlag(cpu, result);
        if (newCarry == 1) setCarryFlag(cpu);
        return result;
    }
    private static int rl(int value, CPU cpu) {
        int carryIn = (cpu.register.f & 0x10) != 0 ? 1 : 0; // get old carry
        int carryOut = (value >> 7) & 0x1;
        int result = ((value << 1) | carryIn) & 0xFF;

        clearFlags(cpu);
        if (result == 0) setZeroFlag(cpu, result);
        if (carryOut == 1) setCarryFlag(cpu);

        return result;
    }

}
