package main;

public class DepthFirstIterator {
    public static void traverse(Visitable root, Visitor Visitor) {
        if (root instanceof OperandNode) {
            root.accept(Visitor);
            return;
        }
        if (root instanceof BinOpNode) {
            BinOpNode opNode = (BinOpNode) root;
            DepthFirstIterator.traverse(opNode.left, Visitor);
            DepthFirstIterator.traverse(opNode.right, Visitor);
            opNode.accept(Visitor);
            return;
        }
        if (root instanceof UnaryOpNode)
        {
            UnaryOpNode opNode = (UnaryOpNode) root;
            DepthFirstIterator.traverse(opNode.subNode, Visitor);
            opNode.accept(Visitor);
            return;
        }
        throw new RuntimeException("Instance root has a bad type!");
    }
}
