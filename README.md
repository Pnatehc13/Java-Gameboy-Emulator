# Java-Gameboy-Emulator
Game Boy emulator core implemented from scratch in Java. Partial CPU, memory, and opcode support completed in a solo project spanning 6 days. A deep dive into hardware emulation, CPU architecture, and debugging complex systems. Work in progress.
# Java Game Boy Emulator

A partial Game Boy emulator core implemented from scratch in Java. This solo project was completed in 6 days and serves as a deep dive into CPU architecture, opcode decoding, memory mapping, and the challenges of hardware emulation.

## Overview

This project implements the core components of the original Game Boy’s CPU (LR35902), including register management, opcode execution, and partial memory handling. It is a work in progress and does not fully run all Game Boy ROMs yet.

## What I Learned

- How a CPU processes instructions at the hardware level  
- Opcode decoding and timing cycles  
- Memory mapping and register usage  
- The challenges of debugging and building a complex system solo  
- Pushing Java beyond typical use cases into low-level emulation  

## Status

- CPU core mostly implemented  
- Memory and opcode execution partially done  
- Graphics (PPU) and timers still under development  
- Emulator currently does not fully run commercial ROMs  

## How to Build and Run

This project is written in Java and requires JDK 11 or later to compile and run.

### Build

If you’re using the command line:
bash
javac -d bin src/**/*.java


This compiles all Java source files under src/ and puts class files in the bin/ directory.

Run
After building, run the main emulator class (replace MainClass with your actual main class name):
java -cp bin your.package.MainClass path/to/gameboy_rom.gb

Notes
The emulator is a work in progress and may not run all ROMs correctly.

No BIOS support yet (if applicable), so use compatible ROMs or test files.

You may need to tweak memory or opcode settings depending on the ROM.

Disclaimer: Partial Emulation & Approximations
This emulator is still a work in progress and does not fully or accurately emulate all Game Boy functionality.

To get some ROMs to run without crashing, certain behaviors are approximated :

Some timing cycles are simplified or fixed

Graphics rendering and input handling may be incomplete or stubbed out

Interrupt handling and timers may be partially bypassed

Hardcoded shortcuts might exist to allow basic boot or simple games to start

This project is primarily an educational exploration of how an emulator core can be structured in Java, rather than a polished product.

Future Plans
Complete opcode support and timing accuracy

Implement graphics rendering and input handling

Optimize code and improve performance

License
This project is licensed under the MIT License. See LICENSE for details.



