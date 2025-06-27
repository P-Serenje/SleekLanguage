package compiler.ast;

import java.util.List;

//Block: { ... }
public class Block extends Statement {
 public List<Statement> statements;
 public Block(List<Statement> statements) {
     this.statements = statements;
 }
 
 @Override
 public void accept(Visitor visitor) {
     visitor.visit(this);
     for (Statement stmt : statements) {
         stmt.accept(visitor);
     }
 }
}