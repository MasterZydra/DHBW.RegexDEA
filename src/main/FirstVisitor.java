package main;


//@autor 1325791 
public class FirstVisitor implements Visitor {

    @Override
    public void visit(OperandNode node) {
        if (node.symbol==null)
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
        if (node.operator.equals("|"))
        {
            node.nullable=((SyntaxNode)node.left).nullable | ((SyntaxNode)node.right).nullable;

            node.firstpos.addAll(((SyntaxNode)node.left).firstpos);
            node.firstpos.addAll(((SyntaxNode)node.right).firstpos);

            node.lastpos.addAll(((SyntaxNode)node.left).lastpos);
            node.lastpos.addAll(((SyntaxNode)node.right).lastpos);
        }

        if (node.operator.equals("°"))
        {
            node.nullable=((SyntaxNode)node.left).nullable & ((SyntaxNode)node.right).nullable;

            if (((SyntaxNode)node.left).nullable){
                node.firstpos.addAll(((SyntaxNode)node.left).firstpos);
                node.firstpos.addAll(((SyntaxNode)node.right).firstpos);
            }
            else {
                node.firstpos.addAll(((SyntaxNode)node.left).firstpos);
            }

            if (((SyntaxNode)node.right).nullable){
                node.lastpos.addAll(((SyntaxNode)node.left).lastpos);
                node.lastpos.addAll(((SyntaxNode)node.right).lastpos);
            }
            else {
                node.lastpos.addAll(((SyntaxNode)node.right).lastpos);
            }
        }
    }

    @Override
    public void visit(UnaryOpNode node) {
        if (node.operator.equals("*"))
        {
            node.nullable=true;
            node.firstpos.addAll(((SyntaxNode)node.subNode).firstpos);
            node.lastpos.addAll(((SyntaxNode)node.subNode).lastpos);
        }

        if (node.operator.equals("+")){
            node.nullable=((SyntaxNode)node.subNode).nullable;
            node.firstpos.addAll(((SyntaxNode)node.subNode).firstpos);
            node.lastpos.addAll(((SyntaxNode)node.subNode).lastpos);
        }

        if (node.operator.equals("?")){
            node.nullable=true;
            node.firstpos.addAll(((SyntaxNode)node.subNode).firstpos);
            node.lastpos.addAll(((SyntaxNode)node.subNode).lastpos);
        }
    }
}
