public class FirstVisitor implements Visitor {


    public void visitAll(Visitable node)
    {

    }

    @Override
    public void visit(OperandNode node) {
        if (node.symbol=="Epsilon")
        {
            node.nullable=true;
        }
        else
        {
            node.nullable=false;
            node.firstpos.add(node.position);
            node.lastpos.add(node.position);
        }
    }

    @Override
    public void visit(BinOpNode node) {
        if (node.operator=="|")
        {
            node.nullable=node.left.
        }

        if (node.operator=="*")
        {

        }

    }

    @Override
    public void visit(UnaryOpNode node) {

    }
}
