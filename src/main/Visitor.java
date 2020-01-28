package main;

public interface Visitor {
    void visit(OperandNode node);
    void visit(BinOpNode node);
    void visit(UnaryOpNode node);
}
