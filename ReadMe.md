<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Sleek Language</title>
  <style>
    body {
      font-family: monospace;
      white-space: pre-wrap;
      background: #f9f9f9;
      padding: 2em;
      line-height: 1.5;
    }
    code {
      background: #eee;
      padding: 0.2em 0.4em;
    }
    .block {
      background: #eee;
      padding: 1em;
      margin: 1em 0;
      border-left: 4px solid #999;
    }
  </style>
</head>
<body>

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
   <code>$ java compiler.SleekProgram program.sleek</code>

2. Build executable:
   <code>$ llc -filetype=obj program.ll -o program.o</code><br>
   <code>$ clang program.o -o program</code>

3. Run:
   <code>$ ./program</code>

▼ Example (demo.sleek):
<div class="block">
func add(a, b) {<br>
    return a + b<br>
}<br><br>
x = input()<br>
y = input()<br>
z = add(x, y)<br>
print(z)
</div>

==========================================
          KEY CHARACTERISTICS
==========================================

▼ Type Inference<br>
<code>x = input()</code>     // type determined by usage<br>
<code>y = "text"</code>      // string<br>
<code>z = true</code>        // bool

▼ Error Checking
• Syntax errors<br>
• Type mismatches (e.g., "text" + 42)<br>
• Undefined variables (e.g., using 'm' without declaration)

==========================================
            NEXT STEPS
==========================================
- Build system integration<br>
- Standard library expansion<br>
- Enhanced debugging support

</body>
</html>
