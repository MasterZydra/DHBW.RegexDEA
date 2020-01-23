public class Parser {
    private final String input;
    private int position;

    private Parser(String input) {
        this.position = 0;

        this.input = input;
    }

    private void match(char symbol) {
        if ((this.input == null || "".equals(this.input)) ||
            (this.input.charAt(this.position) != symbol))
        {
            throw new RuntimeException("Syntax error!");
        }

        if (this.position <= this.input.length())
        {
            throw new RuntimeException("End of input reached!");
        }

        this.position++;
    }

    /*
        // 1. wird benoetigt bei der Regel Start -> '(' RegExp ')''#'
        // 2. wird benoetigt bei der Regel Start -> '#'
        // 3. wird sonst bei keiner anderen Regel benoetigt
     */
    private void assertEndOfInput() {
        if (this.position < this.input.length()) {
            throw new RuntimeException("No end of input reached!");
        }
    }

    private char curChar() {
        return this.input.charAt(this.position);
    }

    private Visitable start(Visitable parameter) {
        // TODO Implement
        if (this.curChar() == '(') {
            this.match('(');
            Visitable regExp = this.regExp(null);
            this.match(')');
            this.match('#');
            this.assertEndOfInput();

            Visitable leaf = new OperandNode("#");
            Visitable root = new BinOpNode("°", regExp, leaf);
            return root;
        } else if (this.curChar() == '#') {
            return new OperandNode("#");
        } else {
            throw new RuntimeException("Syntax error!");
        }
    }

    private Visitable regExp(Visitable parameter) {
        // TODO Implement
        if (this.curChar() == '0' /* 0..9, A..Z, a...z*/ ||
                this.curChar() == '(') {
            // RegExp -> Term RE'
        } else {
            throw new RuntimeException("Syntax error!");
        }
        Visitable termTree = term(null);
        return re(termTree);
    }

    private Visitable re(Visitable parameter) {
        // TODO Implement
        if (this.curChar() == '|') {
            match('|');
            Visitable termTree = this.term(null);
            Visitable root = new BinOpNode("|", parameter, termTree);
            return this.re(root);
        } else if (this.curChar() == ')') {
            // RE' -> Eps
        } else {
            throw new RuntimeException("Syntax error!");
        }
    }

    private Visitable term(Visitable parameter) {
        // TODO Implement
        if (this.curChar() == '0' /* 0..9, A..Z, a...z*/) {
            if (parameter != null) {
                return this.term(new BinOpNode("°", parameter, this.factor(null)));
            } else if (this.curChar() == '|' ||
                    this.curChar() == ')') {
                return parameter;
            } else {
                throw new RuntimeException("Syntax error!");
            }
        }
        return  null;
    }

    private Visitable factor(Visitable parameter) {
        // TODO Implement
        return  null;
    }

    private Visitable hOp(Visitable parameter) {
        // TODO Implement
        return  null;
    }

    private Visitable elem(Visitable parameter) {
        // TODO Implement
        return  null;
    }

    private Visitable alphanum(Visitable parameter) {
        // TODO Implement
        if (Character.isLetter(this.curChar()) ||
                Character.isDigit(this.curChar())) {
            String symbol = Character.toString(this.curChar());
            return new OperandNode(symbol);
        } else {
            throw new RuntimeException("Syntax error!");
        }
    }
}