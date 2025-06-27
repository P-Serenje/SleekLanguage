package compiler.semantics;

import java.util.*;
import compiler.ast.*;

// ===== TYPES =====
public abstract class Type {
    public abstract String getName();
    public boolean isNumeric() {
        return this instanceof IntType || this instanceof FloatType; // String and Void are not numeric
    }
}

class IntType    extends Type { public String getName() { return "int";   } }
class FloatType  extends Type { public String getName() { return "float"; } }
class StringType extends Type { public String getName() { return "string";} }
class VoidType   extends Type { public String getName() { return "void";  } }

// ===== SYMBOLS =====
abstract class Symbol {
    protected final String name;
    protected Type type;
    public Symbol(String name, Type type) { this.name = name; this.type = type; }
    public String getName() { return name; }
    public Type   getType() { return type; }
    public void   setType(Type type) { this.type = type; }
}

class VariableSymbol extends Symbol {
    public VariableSymbol(String name, Type type) { super(name, type); }
}

class FunctionSymbol extends Symbol {
    private final List<VariableSymbol> parameters;
    private Type returnType;

    public FunctionSymbol(String name, Type returnType, List<VariableSymbol> params) {
        super(name, null);
        this.parameters = params;
        this.returnType = returnType;
    }
    public List<VariableSymbol> getParameters() { return parameters; }
    public Type getReturnType() { return returnType; }
    public void setReturnType(Type rt) { this.returnType = rt; }
}

// ===== SYMBOL TABLE =====
class SymbolTable {
    private final Deque<Map<String,Symbol>> scopes = new ArrayDeque<>();

    public void enterScope() { scopes.push(new HashMap<>()); }
    public void exitScope()  { scopes.pop(); }
    public void define(Symbol sym) { scopes.peek().put(sym.getName(), sym); }
    public Symbol resolve(String name) {
        Iterator<Map<String,Symbol>> it = scopes.descendingIterator();
        while (it.hasNext()) {
            Map<String,Symbol> scope = it.next();
            if (scope.containsKey(name)) return scope.get(name);
        }
        return null;
    }
}

// ===== SEMANTIC EXCEPTION =====
class SemanticException extends RuntimeException {
    public SemanticException(String msg) { super(msg); }
}
