===========
SLEEK LANGUAGE
===========

A minimal, type-inferred language compiling to LLVM IR

■ Features
• Clean, expression-oriented syntax
• Automatic type inference
• Compile-time type checking
• Flexible I/O operations

============
GETTING STARTED
============

▼ Requirements
- Java JDK 8+
- LLVM toolchain (llc, clang)

▼ Compilation
1. Compile to LLVM:
   $ java compiler.SleekProgram program.sleek

2. Build executable:
   $ llc -filetype=obj program.ll -o program.o
   $ clang program.o -o program

3. Run:
   $ ./program

▼ Example (demo.sleek):
----------------------------------------
func add(a, b) {
    return a + b
}

x = input()
y = input()
z = add(x, y)
print(z)
----------------------------------------

==========================================
          KEY CHARACTERISTICS
==========================================

▼ Type Inference
x = input()     // type determined by usage
y = "text"      // string
z = true        // bool

▼ Error Checking
• Syntax errors
• Type mismatches (e.g., "text" + 42)
• Undefined variables (e.g., using 'm' without declaration)

==========================================
            NEXT STEPS
==========================================
- Build system integration
- Standard library expansion
- Enhanced debugging support
