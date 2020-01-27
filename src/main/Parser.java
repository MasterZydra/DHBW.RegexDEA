package main;

public class Parser {
    private final String input;
    private int position;

    public Parser(String input) {
        // Init properties
        this.position = 0;
        // Save parameter
        this.input = input;
    }

    /*
    * Parse given input into Visitable and check syntax.
     */
    public Visitable parse() {
        return this.start(null);
    }

    private void match(char symbol) {
        if ((this.input == null || "".equals(this.input)) ||
            (this.input.charAt(this.position) != symbol))
        {
            throw new RuntimeException("Syntax error!");
        }

        if (this.position >= this.input.length())
        {
            throw new RuntimeException("End of input reached!");
        }

        this.position++;
    }

    /*
    * Check if position is not longer than the length of the input.
    * Necessary in start() for '#' and '('RegExp')''#'.
     */
    private void assertEndOfInput() {
        if (this.position < this.input.length()) {
            throw new RuntimeException("No end of input reached!");
        }
    }

    /*
    * Get current character
     */
    private char curChar() {
        return this.input.charAt(this.position);
    }

    /********* Functions for none-terminal characters *********/

    private Visitable start(Visitable parameter) {
        if (this.curChar() == '(') {
            this.match('(');
            Visitable regExp = this.regExp(null);
            this.match(')');
            this.match('#');
            this.assertEndOfInput();
            // Prepare return value
            Visitable leaf = new OperandNode("#");
            return new BinOpNode("°", regExp, leaf);
        }
        else if (this.curChar() == '#') {
            this.match('#');
            this.assertEndOfInput();
            // Prepare return value
            return new OperandNode("#");
        }
        else throw new RuntimeException("Syntax error!");
    }

    private Visitable regExp(Visitable parameter) {
        if (Character.isLetter(this.curChar()) ||   // a..z, A..z
            Character.isDigit(this.curChar()) ||    // 0..9
            this.curChar() == '(')
        {
            // Prepare return value
            Visitable term = this.term(null);
            return this.re(term);
        }
        else throw new RuntimeException("Syntax error!");
    }

    private Visitable re(Visitable parameter) {
        if (this.curChar() == '|') {
            this.match('|');
            // Prepare return value
            Visitable term = this.term(null);
            Visitable root = new BinOpNode("|", parameter, term);
            return this.re(root);
        }
        else if (this.curChar() == ')') {
            return parameter;
        }
        else throw new RuntimeException("Syntax error!");
    }

    private Visitable term(Visitable parameter) {
        if (Character.isLetter(this.curChar()) ||   // a..z, A..z
            Character.isDigit(this.curChar()) ||    // 0..9
            this.curChar() == '(')
        {
            // Prepare return value
            Visitable factor = this.factor(null);
            Visitable term;
            if (parameter != null) {
                Visitable root = new BinOpNode("°", parameter, factor);
                term = this.term(root);
            } else {
                term = this.term(factor);
            }
            return term;
        }
        else if (this.curChar() == '|' ||
                 this.curChar() == ')')
        {
            return parameter;
        }
        else throw new RuntimeException("Syntax error!");
    }

    private Visitable factor(Visitable parameter) {
        if (Character.isLetter(this.curChar()) ||   // a..z, A..z
            Character.isDigit(this.curChar()) ||    // 0..9
            this.curChar() == '(' ||
            this.curChar() == '|' ||
            this.curChar() == ')')
        {
            // Prepare return value
            Visitable elem = this.elem(null);
            return this.hOp(elem);
        }
        else throw new RuntimeException("Syntax error!");
    }

    private Visitable hOp(Visitable parameter) {
        if (Character.isLetter(this.curChar()) ||   // a..z, A..z
            Character.isDigit(this.curChar()) ||    // 0..9
            this.curChar() == '(' ||
            this.curChar() == '|' ||
            this.curChar() == ')')
        {
            return parameter;
        }
        else if (this.curChar() == '*' ||
                 this.curChar() == '+' ||
                 this.curChar() == '?')
        {
            char curChar = this.curChar();
            this.match(curChar);
            String curStr = Character.toString(curChar);
            return new UnaryOpNode(curStr, parameter);
        }
        else throw new RuntimeException("Syntax error!");
    }

    private Visitable elem(Visitable parameter) {
        if (Character.isLetter(this.curChar()) ||
            Character.isDigit(this.curChar()))
        {
            return this.alphanum(null);
        }
        else if (this.curChar() == '(') {
            this.match('(');
            Visitable regExp = this.regExp(null);
            this.match(')');
            return regExp;
        }
        else throw new RuntimeException("Syntax error!");
    }

    private Visitable alphanum(Visitable parameter) {
        if (Character.isLetter(this.curChar()) ||
            Character.isDigit(this.curChar()))
        {
            char curChar = this.curChar();
            this.match(curChar);
            // Prepare return value
            String symbol = Character.toString(curChar);
            return new OperandNode(symbol);
        }
        else throw new RuntimeException("Syntax error!");
    }
}