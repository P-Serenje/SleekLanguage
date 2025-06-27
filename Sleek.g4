grammar Sleek;

@header {
package compiler;
}

// Parser Rules
program
    : (functionDecl | statement)* EOF
    ;

functionDecl
    : FUNC ID '(' paramList? ')' block
    ;

paramList
    : ID (COMMA ID)*
    ;

statement
    : assignment        // Variables are declared via assignment
    | printStmt
    | returnStmt
    | ifStmt
    | whileStmt
    | exprStmt
    | block
    ;

assignment
    : ID ASSIGN expr SEMI   // Implicit variable declaration
    ;

printStmt
    : PRINT '(' expr ')' SEMI
    ;

returnStmt
    : RETURN expr SEMI
    ;

ifStmt
    : IF '(' expr ')' block (ELSE block)?
    ;

whileStmt
    : WHILE '(' expr ')' block
    ;

block
    : LBRACE statement* RBRACE
    ;

exprStmt
    : expr SEMI
    ;

expr
    : equalityExpr
    ;

equalityExpr
    : relationalExpr ( (EQ | NEQ) relationalExpr )*
    ;

relationalExpr
    : additiveExpr ( (LT | GT | LE | GE) additiveExpr )*
    ;

additiveExpr
    : multiplicativeExpr ( (PLUS | MINUS) multiplicativeExpr )*
    ;

multiplicativeExpr
    : primary ( (MUL | DIV) primary )*
    ;

primary
    : LPAREN expr RPAREN
    | ID
    | functionCall
    | inputCall
    | literal
    ;

functionCall
    : ID '(' argList? ')'
    ;

inputCall
    : INPUT '(' ')'       # InputIntCall
    | INPUTFLOAT '(' ')'  # InputFloatCall
    | INPUTSTRING '(' ')' # InputStringCall
    ;

argList
    : expr (COMMA expr)*
    ;

literal
    : INT
    | FLOAT
    | STRING
    ;

// Lexer Rules
FUNC    : 'func';
IF      : 'if';
ELSE    : 'else';
WHILE   : 'while';
PRINT   : 'print';
INPUT   : 'input';
INPUTFLOAT : 'inputfloat';  // Added for float input
INPUTSTRING: 'inputstring'; // Added for string input
RETURN  : 'return';

// Operators
ASSIGN  : '=';
EQ      : '==';
NEQ     : '!=';
LE      : '<=';
GE      : '>=';
LT      : '<';
GT      : '>';
PLUS    : '+';
MINUS   : '-';
MUL     : '*';
DIV     : '/';

// Punctuation
LPAREN  : '(';
RPAREN  : ')';
LBRACE  : '{';
RBRACE  : '}';
SEMI    : ';';
COMMA   : ',';

// Literals
INT     : [0-9]+;
FLOAT   : [0-9]+ '.' [0-9]+;
STRING  : '"' ( ~["\\] | '\\' . )* '"';
ID      : [a-zA-Z_][a-zA-Z0-9_]*; // Ensure ID comes after keywords

// Ignore
WS          : [ \t\r\n]+ -> skip;
LINE_COMMENT: '//' ~[\r\n]* -> skip;
COMMENT     : '/*' .*? '*/' -> skip;