package test;

import main.BinOpNode;
import main.OperandNode;
import main.UnaryOpNode;
import main.Visitable;

public final class Comparer {
    public static boolean treeCmp(Visitable visitable1, Visitable visitable2)
    {
        if (visitable1 == visitable2) return true;
        if (visitable1 == null) return false;
        if (visitable2 == null) return false;
        if (visitable1.getClass() != visitable2.getClass()) return false;
        if (visitable1.getClass() == OperandNode.class)
        {
            OperandNode op1 = (OperandNode) visitable1;
            OperandNode op2 = (OperandNode) visitable2;
            return op1.position == op2.position && op1.symbol.equals(op2.symbol);
        }
        if (visitable1.getClass() == UnaryOpNode.class)
        {
            UnaryOpNode op1 = (UnaryOpNode) visitable1;
            UnaryOpNode op2 = (UnaryOpNode) visitable2;
            return op1.operator.equals(op2.operator)
                && treeCmp(op1.subNode, op2.subNode);
        }
        if (visitable1.getClass() == BinOpNode.class)
        {
            BinOpNode op1 = (BinOpNode) visitable1;
            BinOpNode op2 = (BinOpNode) visitable2;
            return op1.operator.equals(op2.operator)
                && treeCmp(op1.left, op2.left)
                && treeCmp(op1.right, op2.right);
        }
        throw new IllegalStateException("Invalid node type");
    }

    public static boolean firstVisitorEquals(Visitable expected, Visitable visited)
    {
        if (expected == null && visited == null) return true;
        if (expected == null || visited == null) return false;
        if (expected.getClass() != visited.getClass()) return false;
        if (expected.getClass() == BinOpNode.class)
        {
            BinOpNode op1 = (BinOpNode) expected;
            BinOpNode op2 = (BinOpNode) visited;
            return op1.nullable.equals(op2.nullable) &&
                    op1.firstpos.equals(op2.firstpos) &&
                    op1.lastpos.equals(op2.lastpos) &&
                    firstVisitorEquals(op1.left, op2.left) &&
                    firstVisitorEquals(op1.right, op2.right);
        }
        if (expected.getClass() == UnaryOpNode.class)
        {
            UnaryOpNode op1 = (UnaryOpNode) expected;
            UnaryOpNode op2 = (UnaryOpNode) visited;
            return op1.nullable.equals(op2.nullable) &&
                    op1.firstpos.equals(op2.firstpos) &&
                    op1.lastpos.equals(op2.lastpos) &&
                    firstVisitorEquals(op1.subNode, op2.subNode);
        }
        if (expected.getClass() == OperandNode.class)
        {
            OperandNode op1 = (OperandNode) expected;
            OperandNode op2 = (OperandNode) visited;
            return op1.nullable.equals(op2.nullable) &&
                    op1.firstpos.equals(op2.firstpos) &&
                    op1.lastpos.equals(op2.lastpos);
        }
        throw new IllegalStateException(
                String.format( "Beide Wurzelknoten sind Instanzen der Klasse %1$s !"
                                + " Dies ist nicht erlaubt!",
                        expected.getClass().getSimpleName())
        );
    }
}
