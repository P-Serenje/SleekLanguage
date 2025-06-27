package compiler.ast;

import java.util.List;

//Function Declaration
public class FunctionDecl extends ASTNode {
 public String name;
 public List<String> params;
 public Block body;
 public FunctionDecl(String name, List<String> params, Block body) {
     this.name = name;
     this.params = params;
     this.body = body;
 }
 
 @Override
 public void accept(Visitor visitor) {
     visitor.visit(this);
     body.accept(visitor); // Visit function body
 }
 
}
