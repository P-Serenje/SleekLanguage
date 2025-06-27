=====================================================================
                      SLEEK PROGRAMMING LANGUAGE
=====================================================================

Sleek is a simple, type-inferred programming language with clean syntax
and familiar control structures, compiling to efficient LLVM IR.

Key Features:
- Type inference (no explicit type declarations needed)
- Automatic type resolution based on usage
- Static type checking at compile-time
- Familiar C-like syntax with minimal boilerplate

--------------------------------------------------------------
1. COMPILER REQUIREMENTS
--------------------------------------------------------------
- Java JDK 8 or later
- LLVM toolchain (llc, clang)
- ANTLR 4 runtime (included in project)

--------------------------------------------------------------
2. COMPILING SLEEK PROGRAMS
--------------------------------------------------------------

A. Using an IDE (Eclipse/IntelliJ):
   1. Import the SleekCompiler project
   2. Create Run Configuration:
      - Main Class: compiler.SleekCompiler
      - Arguments: path/to/your_program.sleek
   3. Run to generate LLVM IR (.ll file)

B. Using Command Line:
   java compiler.SleekCompiler program.sleek
   -> Generates program.ll in project root directory

C. Building Executables:
   1. Generate object file:
      llc -filetype=obj program.ll -o program.o
   2. Link executable:
      clang program.o -o program
   3. Run:
      ./program

--------------------------------------------------------------
3. TYPE SYSTEM (USAGE-BASED INFERENCE)
--------------------------------------------------------------
Examples of automatic type resolution:
- x = 4 + 5       // inferred as int
- y = "a" + "b"   // inferred as string
- z = true        // inferred as bool

The compiler will reject:
- 4 + "text"      // type mismatch error
- true * 5        // invalid operation

--------------------------------------------------------------
4. EXAMPLE WORKFLOW
--------------------------------------------------------------

A. Create hello.sleek:
----------------------------------
func main() {
    message = "Hello " + "World!"  // string inferred
    print(message);
    return 0;
}
----------------------------------

B. Compile steps:
1. java compiler.SleekCompiler hello.sleek  (creates hello.ll)
2. llc -filetype=obj hello.ll -o hello.o
3. clang hello.o -o hello
4. ./hello

Output: Hello World!

--------------------------------------------------------------
5. ERROR HANDLING
--------------------------------------------------------------
Compiler reports:
- Syntax errors (line numbers, unexpected tokens)
- Type errors (invalid operations, mismatched types)
- Semantic errors (undefined variables/functions)

--------------------------------------------------------------
6. NEXT STEPS
--------------------------------------------------------------
- Add Makefile for build automation
- Implement package distribution
- Expand standard library functions
- Add more type inference examples to documentation

=====================================================================
Note: Sleek's type system automatically resolves types based on usage
      while maintaining compile-time type safety.
=====================================================================
