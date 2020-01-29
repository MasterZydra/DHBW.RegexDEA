package test;

import static org.junit.Assert.*;
import static test.Comparer.*;

import main.*;
import org.junit.Test;

public class FirstVisitorTest {

    @Test
    public void firstVisitorTreeEquals(){

        FirstVisitor firstVisitor=new FirstVisitor();

        Visitable right = new OperandNode("b");
        ((SyntaxNode)right).nullable=false;
        ((SyntaxNode)right).firstpos.add(2);
        ((SyntaxNode)right).lastpos.add(2);
        ((OperandNode)right).position=2;

        Visitable left = new OperandNode("a");
        ((SyntaxNode)left).nullable=false;
        ((SyntaxNode)left).firstpos.add(1);
        ((SyntaxNode)left).lastpos.add(1);
        ((OperandNode)left).position=1;

        left = new BinOpNode("|", left, right);
        ((SyntaxNode)left).nullable=false;
        ((SyntaxNode)left).firstpos.add(1);
        ((SyntaxNode)left).firstpos.add(2);
        ((SyntaxNode)left).lastpos.add(1);
        ((SyntaxNode)left).lastpos.add(2);

        left = new UnaryOpNode("*", left);
        ((SyntaxNode)left).nullable=true;
        ((SyntaxNode)left).firstpos.add(1);
        ((SyntaxNode)left).firstpos.add(2);
        ((SyntaxNode)left).lastpos.add(1);
        ((SyntaxNode)left).lastpos.add(2);

        right = new OperandNode("a");
        ((SyntaxNode)right).nullable=false;
        ((SyntaxNode)right).firstpos.add(3);
        ((SyntaxNode)right).lastpos.add(3);
        ((OperandNode)right).position=3;

        left = new BinOpNode("°", left, right);
        ((SyntaxNode)left).nullable=false;
        ((SyntaxNode)left).firstpos.add(1);
        ((SyntaxNode)left).firstpos.add(2);
        ((SyntaxNode)left).firstpos.add(3);
        ((SyntaxNode)left).lastpos.add(3);

        right = new OperandNode("b");
        ((SyntaxNode)right).nullable=false;
        ((SyntaxNode)right).firstpos.add(4);
        ((SyntaxNode)right).lastpos.add(4);
        ((OperandNode)right).position=4;

        left = new BinOpNode("°", left, right);
        ((SyntaxNode)left).nullable=false;
        ((SyntaxNode)left).firstpos.add(1);
        ((SyntaxNode)left).firstpos.add(2);
        ((SyntaxNode)left).firstpos.add(3);
        ((SyntaxNode)left).lastpos.add(4);


        right = new OperandNode("b");
        ((SyntaxNode)right).nullable=false;
        ((SyntaxNode)right).firstpos.add(5);
        ((SyntaxNode)right).lastpos.add(5);
        ((OperandNode)right).position=5;

        left = new BinOpNode("°", left, right);
        ((SyntaxNode)left).nullable=false;
        ((SyntaxNode)left).firstpos.add(1);
        ((SyntaxNode)left).firstpos.add(2);
        ((SyntaxNode)left).firstpos.add(3);
        ((SyntaxNode)left).lastpos.add(5);


        right = new OperandNode("#");
        ((SyntaxNode)right).nullable=false;
        ((SyntaxNode)right).firstpos.add(6);
        ((SyntaxNode)right).lastpos.add(6);
        ((OperandNode)right).position=6;

        Visitable refTree = new BinOpNode("°", left, right);
        ((SyntaxNode)refTree).nullable=false;
        ((SyntaxNode)refTree).firstpos.add(1);
        ((SyntaxNode)refTree).firstpos.add(2);
        ((SyntaxNode)refTree).firstpos.add(3);
        ((SyntaxNode)refTree).lastpos.add(6);


        right = new OperandNode("b");
        ((OperandNode)right).position=2;

        left = new OperandNode("a");
        ((OperandNode)left).position=1;

        left = new BinOpNode("|", left, right);

        left = new UnaryOpNode("*", left);

        right = new OperandNode("a");
        ((OperandNode)right).position=3;

        left = new BinOpNode("°", left, right);

        right = new OperandNode("b");
        ((OperandNode)right).position=4;

        left = new BinOpNode("°", left, right);

        right = new OperandNode("b");
        ((OperandNode)right).position=5;

        left = new BinOpNode("°", left, right);

        right = new OperandNode("#");
        ((OperandNode)right).position=6;
        
        Visitable changeableTree = new BinOpNode("°", left, right);

        DepthFirstIterator.traverse(changeableTree,firstVisitor);


        assertTrue(firstVisitorEquals(refTree,changeableTree));

    }
}
