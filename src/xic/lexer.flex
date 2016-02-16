package xic;
import java_cup.runtime.*;
      
%%   
%class Lexer
%line
%column
%cup
   
/*
  Declarations
   
  Code between %{ and %}, both of which must be at the beginning of a
  line, will be copied letter to letter into the lexer class source.
  Here you declare member variables and functions that are used inside
  scanner actions.  
*/
%{   
    StringBuffer string = new StringBuffer();
    /* To create a new java_cup.runtime.Symbol with information about
       the current token, the token will have no value in this
       case. */
    private Symbol symbol(int type) {
        return new Symbol(type, yyline, yycolumn);
    }
    
    /* Also creates a new java_cup.runtime.Symbol with information
       about the current token, but this object has a value. */
    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yyline, yycolumn, value);
    }
%}
   

/*
  Macro Declarations
  
  These declarations are regular expressions that will be used latter
  in the Lexical Rules Section.  
*/
   
/* A line terminator is a \r (carriage return), \n (line feed), or
   \r\n. */
// LineTerminator = \r|\n|\r\n
   
/* White space is a line terminator, space, tab, or line feed. */
// WhiteSpace     = {LineTerminator} | [ \t\f]
   
/* A literal integer is is a number beginning with a number between
   one and nine followed by zero or more numbers between zero and nine
   or just a zero.  */
// dec_int_lit = 0 | [1-9][0-9]*
   
/* A identifier integer is a word beginning a letter between A and
   Z, a and z, or an underscore followed by zero or more letters
   between A and Z, a and z, zero and nine, or an underscore. */
// dec_int_id = [A-Za-z_][A-Za-z_0-9]*
LineTerminator = \r|\n|\r\n
InputCharacter = [^\r\n]
WhiteSpace = {LineTerminator} | [ \t\f]

Comment = "//" {InputCharacter}*
Integer = [1-9] [0-9]* | 0
HexNumber = [0-9A-F]+

Letter = [a-zA-Z]
Identifier = {Letter} [a-zA-Z0-9_]*

%state STRING
%state CHAR

%%
   
/*
   This section contains regular expressions and actions, i.e. Java
   code, that will be executed when the scanner matches the associated
   regular expression. */
   
   /* YYINITIAL is the state at which the lexer begins scanning.  So
   these regular expressions will only be matched if the scanner is in
   the start state YYINITIAL. */
   
<YYINITIAL> {

    /* Keywords */
    "use"              { return symbol(sym.USE); }
    "if"               { return symbol(sym.IF); }
    "else"             { return symbol(sym.ELSE); }
    "while"            { return symbol(sym.WHILE); }
    "return"           { return symbol(sym.RETURN); }
    "length"           { return symbol(sym.LENGTH); }
    
    /* primitive types */
    "true"             { return symbol(sym.TRUE); }
    "false"            { return symbol(sym.FALSE); }
    "int"              { return symbol(sym.INT); }
    "bool"             { return symbol(sym.BOOL); }    


    ";"                { return symbol(sym.SEMI); }
    ":"                { return symbol(sym.COLON); }
    ","                { return symbol(sym.COMMA); }
    "_"                { return symbol(sym.UNDERSCORE); }

    "+"                { return symbol(sym.PLUS); }
    "-"                { return symbol(sym.MINUS); }
    "*"                { return symbol(sym.MULTIPLY); }
    "/"                { return symbol(sym.DIVIDE); }
    "%"                { return symbol(sym.MOD); }
    
    "&"                { return symbol(sym.AND); }
    "|"                { return symbol(sym.OR); }
    "!"                { return symbol(sym.NEGATE); }

    "="                { return symbol(sym.EQ); }
    "<"                { return symbol(sym.LT); }
    ">"                { return symbol(sym.GT); }    
    "=="               { return symbol(sym.EQEQ); }
    "<="               { return symbol(sym.LEQ); }
    ">="               { return symbol(sym.GEQ); }
    "!="               { return symbol(sym.NEQ); }
        
    "("                { return symbol(sym.LPAREN); }
    ")"                { return symbol(sym.RPAREN); }
    "{"                { return symbol(sym.LBRACE); }
    "}"                { return symbol(sym.RBRACE); }    
    "["                { return symbol(sym.LBRACKET); }
    "]"                { return symbol(sym.RBRACKET); }

    {Integer}          { return symbol(sym.INTEGER, new Integer(yytext())); }
    {Identifier}       { return symbol(sym.ID, yytext());}
   
    /* Don't do anything if whitespace is found */
    {WhiteSpace}       { /* ignore */ }
    {Comment}          { /* ignore */ }  

}

<STRING> {
  \"                { yybegin(YYINITIAL); 
                    return symbol(sym.STRING, string.toString()); }
                    
  [^\n\r\"\\]+      { string.append(yytext()); }
  \\t               { string.append("\\t"); }
  \\n               { string.append("\\n"); }
  \\r               { string.append("\\r"); }
  \\\"              { string.append("\""); }
  \\\\              { string.append('\\'); }
  \\0x {HexNumber}  { int k = Integer.parseInt(yytext().substring(3), 16);
                      if(k > 31 && k < 127) string.append((char) k);
                      else string.append(yytext()); }
  \\x {HexNumber}   { int k = Integer.parseInt(yytext().substring(2), 16);
                      if(k > 31 && k < 127) string.append((char) k);
                      else string.append(yytext()); }
  \\ {Integer}      { int k = Integer.parseInt(yytext().substring(1));
                      if(k > 31 && k < 127) string.append((char) k);
                      else string.append(yytext()); }
}

<CHAR> {
  \'                { yybegin(YYINITIAL);
                      if(string.length() == 0) return symbol(sym.error, "error:empty character literal");
                      return symbol(sym.CHARACTER, string.toString()); }
  
  [^\n\r\'\\]+      { string.append(yytext()); }
  \\t               { string.append("\\t"); }
  \\n               { string.append("\\n"); }
  \\r               { string.append("\\r"); }
  \\\"              { string.append("\""); }
  \\                { string.append('\\'); }
  \\0x {HexNumber}  { int k = Integer.parseInt(yytext().substring(3), 16);
                      if(k > 31 && k < 127) string.append((char) k);
                      else string.append(yytext()); }
  \\ {Integer}      { int k = Integer.parseInt(yytext().substring(1));
                      if(k > 31 && k < 127) string.append((char) k);
                      else string.append(yytext()); }
}

/* No token was found for the input so through an error.  Print out an
   Illegal character message with the illegal character that was found. */
[^]                    { throw new Error("Illegal character <"+yytext()+">"); }
