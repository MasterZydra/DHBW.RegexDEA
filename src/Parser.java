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

    private Visitable start(Visitable visitable) {
        // TODO Implement
        return  null;
    }

    private Visitable regExp(Visitable visitable) {
        // TODO Implement
        Visitable termTree = term(null);
        return re(termTree);
    }

    private Visitable re(Visitable visitable) {
        // TODO Implement
        return  null;
    }

    private Visitable term(Visitable visitable) {
        // TODO Implement
        return  null;
    }

    private Visitable factor(Visitable visitable) {
        // TODO Implement
        return  null;
    }

    private Visitable hOp(Visitable visitable) {
        // TODO Implement
        return  null;
    }

    private Visitable elem(Visitable visitable) {
        // TODO Implement
        return  null;
    }

    private Visitable alphanum(Visitable visitable) {
        // TODO Implement
        return  null;
    }
}