package compiler;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import antlr.SleekLexer;
import antlr.SleekParser;
import compiler.ast.Program;
import compiler.codegen.LLVMCodeGenerator;
import compiler.semantics.SemanticAnalyzer;

public class SleekCompiler {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Usage: java -jar sleekc.jar <input.sleek>");
            System.exit(1);
        }
        String inputFile = args[0];

        // derive output filename by replacing the extension with .ll
        Path inPath = Paths.get(inputFile);
        String baseName = inPath.getFileName().toString();
        int dot = baseName.lastIndexOf('.');
        if (dot > 0) baseName = baseName.substring(0, dot);
        String outputFile = inPath.getParent() == null
                            ? baseName + ".ll"
                            : inPath.getParent().resolve(baseName + ".ll").toString();

        try {
            // 1. Read source file into a CharStream
            CharStream input = CharStreams.fromFileName(inputFile);

            // 2. Lexing
            SleekLexer lexer = new SleekLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);

            // 3. Parsing
            SleekParser parser = new SleekParser(tokens);
            ParseTree tree = parser.program();

            // 4. AST construction
            ASTConverter astConverter = new ASTConverter();
            Program ast = (Program) astConverter.visit(tree);

            // 5. Semantic analysis (type inference & checks)
            SemanticAnalyzer semanticAnalyzer = new SemanticAnalyzer();
            semanticAnalyzer.analyze(ast);

            // 6. LLVM IR code generation
            LLVMCodeGenerator codeGen = new LLVMCodeGenerator(semanticAnalyzer);
            codeGen.generate(ast, outputFile);

            System.out.println("Compilation succeeded. IR written to " + outputFile);
        } catch (IOException e) {
            System.err.println("IO Error: " + e.getMessage());
            System.exit(2);
        } catch (RuntimeException e) {
            // Catch SemanticException and others
            System.err.println("Compilation error: " + e.getMessage());
            System.exit(3);
        }
    }
}
