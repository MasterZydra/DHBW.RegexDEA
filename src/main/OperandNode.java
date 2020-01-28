package main;

public class OperandNode extends SyntaxNode implements Visitable {
    public int position;
    public String symbol;

    public OperandNode(String symbol) {
        this.position = -1; // = not initialized
        this.symbol = symbol;
    }

    @Override
    public void accept(Visitor visitor)
    {
        visitor.visit(this);
    }
}
