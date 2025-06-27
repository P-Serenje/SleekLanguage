===============SLEEK LANGUAGE===============
===============

A minimal, type-inferred language compiling to LLVM IR

■ Features
• Clean C-like syntax
• Automatic type inference
• Compile-time type checking
• Simple I/O operations

===============           
GETTING STARTED
===============

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

▼ Example (hello.sleek):
----------------------------------------
func main() {

    msg = "Hello " + "World!"
    print(msg)
    return 0
    
}
----------------------------------------

=========KEY CHARACTERISTICS=========

▼ Type Inference
x = 42          // int
y = "text"      // string
z = x > 10      // bool

▼ Error Checking
• Syntax errors
• Type mismatches
• Undefined variables

=========NEXT STEPS=========

- Build system integration
- Standard library expansion
- Enhanced debugging
