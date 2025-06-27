Sleek Programming Language
==========================

Sleek is a simple, statically-typed programming language featuring clean syntax with familiar control structures and I/O operations.

Compilation Methods
-------------------

1. Compiling via IDE (Eclipse/IntelliJ)
   - Import the SleekCompiler project
   - Set up Run Configuration:
     * Main class: compiler.SleekCompiler
     * Program arguments: path/to/program.sleek
   - Run the compiler to generate LLVM IR (.ll file)

2. Compiling via Terminal
   - Navigate to project directory:
     cd /path/to/Sleek/src
   - Compile using:
     java compiler.SleekCompiler program.sleek
   - Generates program.ll in root project directory

3. Building Executable from LLVM IR
   - First generate native object file:
     llc -filetype=obj program.ll -o program.o
   - Then link with system libraries:
     clang program.o -o program
   - Run executable:
     ./program

Complete Workflow Example
------------------------
1. Create hello.sleek:
   func main() {
       print("Hello World!");
       return 0;
   }

2. Compile to LLVM IR:
   java compiler.SleekCompiler hello.sleek

3. Generate Executable:
   llc -filetype=obj hello.ll -o hello.o
   clang hello.o -o hello

4. Run Program:
   ./hello

Output:
Hello World!

Error Handling
-------------
Compiler reports:
- Syntax errors during parsing
- Type mismatches in semantic analysis
- Code generation failures

Next Steps
---------
- Add Makefile for build automation
- Implement package distribution
- Expand standard library
