package compiler.ast;

// Base AST Node
public abstract class ASTNode {
    public abstract void accept(Visitor visitor);
}