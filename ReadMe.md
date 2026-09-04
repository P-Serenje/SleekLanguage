# Sleek Language

A minimal, type-inferred language compiling to LLVM IR.

## Features

- Clean, expression-oriented syntax
- Automatic type inference
- Compile-time type checking
- Flexible I/O operations

## Getting Started

### Requirements

- Java JDK 8+
- LLVM toolchain (`clang`)

### Compilation

1. Compile the compiler sources (if not already built):

   ```bash
   javac -cp "out:lib/antlr.jar" -d out $(find src -name "*.java" ! -name "module-info.java")
   ```

2. Compile a `.sleek` program to LLVM IR:

   ```bash
   java -cp "out:lib/antlr.jar" compiler.SleekCompiler test.sleek
   ```

   This produces `test.ll` in the same directory as the input file.

3. Build the executable:

   ```bash
   clang test.ll runtime.c -O2 -o test
   ```

4. Run:

   ```bash
   ./test
   ```

### Example (`test.sleek`)

```
func add(a, b)
{
    return a + b;
}
x = input();
y = input();
z = add(x, y);
print(z);
```

## Key Characteristics

### Type Inference

```
x = input()     // type determined by usage
y = "text"      // string
z = true        // bool
```

### Error Checking

- Syntax errors
- Type mismatches (e.g., `"text" + 42`)
- Undefined variables (e.g., using `m` without declaration)

## Next Steps

- Build system integration
- Standard library expansion
- Enhanced debugging support
