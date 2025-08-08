public class CPU {
    int pc;
    public int cycles = 0;
    Memory memory = new Memory();
    Register register = new Register();
    public int sp = 0xFFFE;
    public boolean ime = false;
    public boolean halted = false;
    public boolean stopped = false;
    public boolean imeRequested = false;

    public int fetch()
    {
        int opcode = memory.readByte(pc);
        pc++;
        return opcode;
    }
    public void execute(int opcode)
    {
        // Debug log before executing
        System.out.printf(
                "PC=%04X OP=%02X  A=%02X F=%02X B=%02X C=%02X D=%02X E=%02X H=%02X L=%02X SP=%04X CYCLES=%d\n",
                pc - 1, // pc was incremented in fetch()
                opcode,
                register.a & 0xFF,
                register.f & 0xFF,
                register.b & 0xFF,
                register.c & 0xFF,
                register.d & 0xFF,
                register.e & 0xFF,
                register.h & 0xFF,
                register.l & 0xFF,
                sp & 0xFFFF,
                cycles
        );

        if (opcode == 0xCB) {
            int cbOpcode = memory.readByte(pc) & 0xFF;
            pc++;
            System.out.printf("   CB=%02X\n", cbOpcode);
            CBInstructions.execute(this, cbOpcode);
            return;
        }

        Instructions.executeOpcode(this, opcode);
    }


    public void checkAndHandleInterrupts()
    {
        System.out.println("[CPU] IME: " + ime + " IF: " + Integer.toHexString(memory.readByte(0xFF0F)) + " IE: " + Integer.toHexString(memory.readByte(0xFFFF)));

        if(!ime)return;
        int ie = memory.readByte(0xFFFF);
        int _if = memory.readByte(0xFF0F);
        int triggered = ie&_if;
        if(triggered == 0)return;
        ime = false;

        for (int i =0;i<5;i++)
        {
            if ((triggered & (1 << i)) != 0) {
                memory.writeByte(0xFF0F, (byte) (_if & ~(1 << i))); // clear the interrupt flag bit

                // push current PC to stack
                sp -= 2;
                memory.writeWord(sp, pc);

                // jump to interrupt vector
                switch (i) {
                    case 0: pc = 0x40; break;  // VBlank
                    case 1: pc = 0x48; break;  // LCD STAT
                    case 2: pc = 0x50; break;  // Timer
                    case 3: pc = 0x58; break;  // Serial
                    case 4: pc = 0x60; break;  // Joypad
                }
                return;
            }
        }
    }

    public void setKeyState(int key, boolean pressed) {
        memory.setKeyState(key, pressed);
    }
    public int step()
    {
        checkAndHandleInterrupts();

        int opcode = memory.readByte(pc) & 0xFF;
        // DEBUG: remove or comment out noisy prints while running games
        // System.out.printf("PC: %04X  Opcode: %02X\n", pc - 1, opcode);

        pc++;
        int cyclesUsed = Instructions.executeOpcode(this, opcode);
        cycles += cyclesUsed;

        memory.updateTimers(cyclesUsed);
        return cyclesUsed;
    }
}
