package compiler.ast;

import java.util.List;

public class Program extends ASTNode {
    public List<ASTNode> declarations;
    
    public Program(List<ASTNode> declarations) {
        this.declarations = declarations;
    }
    
    public void accept(Visitor visitor) {
        visitor.visit(this);
        for (ASTNode decl : declarations) {
            if (decl instanceof FunctionDecl) {
                ((FunctionDecl) decl).accept(visitor);
            }
        }
    }
}