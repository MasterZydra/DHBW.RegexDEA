package test;

/** Standalone tests for parser
 * @author 6439456
 */

import static org.junit.Assert.*;
import static test.Comparer.*;

import main.*;
import org.junit.Test;

public class ParserTest {
    @Test(expected = Exception.class)
    public void invalidSyntax_Hash() {
        Parser parser = new Parser("(a)");
        Visitable syntaxTree = parser.parse();
    }

    @Test(expected = Exception.class)
    public void invalidSyntax_ParenthesisClosing() {
        Parser parser = new Parser("(a#");
        Visitable syntaxTree = parser.parse();
    }

    @Test(expected = Exception.class)
    public void invalidSyntax_ParenthesisOpening() {
        Parser parser = new Parser("a)#");
        Visitable syntaxTree = parser.parse();
    }

    @Test(expected = Exception.class)
    public void invalidSyntax_Operator() {
        Parser parser = new Parser("(+a)#");
        Visitable syntaxTree = parser.parse();
    }

    @Test(expected = Exception.class)
    public void invalidSyntax_OperatorUnknown() {
        Parser parser = new Parser("(a.b|cd)#");
        Visitable syntaxTree = parser.parse();
    }

    @Test
    public void validSyntax_Concat() {
        Parser parser = new Parser("(abc)#");
        Visitable syntaxTree = parser.parse();

        Visitable left = new OperandNode("a");
        ((OperandNode) left).position = 1;
        Visitable right = new OperandNode("b");
        ((OperandNode) right).position = 2;
        left = new BinOpNode("°", left, right);
        right = new OperandNode("c");
        ((OperandNode) right).position = 3;
        left = new BinOpNode("°", left, right);
        right = new OperandNode("#");
        ((OperandNode) right).position = 4;
        Visitable refTree = new BinOpNode("°", left, right);

        assertTrue(treeCmp(syntaxTree, refTree));
    }

    @Test
    public void validSyntax_Alternative() {
        Parser parser = new Parser("(a|b)#");
        Visitable syntaxTree = parser.parse();

        Visitable left = new OperandNode("a");
        ((OperandNode) left).position = 1;
        Visitable right = new OperandNode("b");
        ((OperandNode) right).position = 2;
        left = new BinOpNode("|", left, right);
        right = new OperandNode("#");
        ((OperandNode) right).position = 3;
        Visitable refTree = new BinOpNode("°", left, right);

        assertTrue(treeCmp(syntaxTree, refTree));
    }

    @Test
    public void validSyntax_KleeneStar() {
        Parser parser = new Parser("(a*)#");
        Visitable syntaxTree = parser.parse();

        Visitable subNode = new OperandNode("a");
        ((OperandNode) subNode).position = 1;
        Visitable left = new UnaryOpNode("*", subNode);
        Visitable right = new OperandNode("#");
        ((OperandNode) right).position = 2;
        Visitable refTree = new BinOpNode("°", left, right);

        assertTrue(treeCmp(syntaxTree, refTree));
    }

    @Test
    public void validSyntax_KleenePlus() {
        Parser parser = new Parser("(a+)#");
        Visitable syntaxTree = parser.parse();

        Visitable subNode = new OperandNode("a");
        ((OperandNode) subNode).position = 1;
        Visitable left = new UnaryOpNode("+", subNode);
        Visitable right = new OperandNode("#");
        ((OperandNode) right).position = 2;
        Visitable refTree = new BinOpNode("°", left, right);

        assertTrue(treeCmp(syntaxTree, refTree));
    }

    @Test
    public void validSyntax_Option() {
        Parser parser = new Parser("(a?)#");
        Visitable syntaxTree = parser.parse();

        Visitable subNode = new OperandNode("a");
        ((OperandNode) subNode).position = 1;
        Visitable left = new UnaryOpNode("?", subNode);
        Visitable right = new OperandNode("#");
        ((OperandNode) right).position = 2;
        Visitable refTree = new BinOpNode("°", left, right);

        assertTrue(treeCmp(syntaxTree, refTree));
    }

    @Test
    public void validSyntax_Complex() {
        Parser parser = new Parser("((a|b)*abb)#");
        Visitable syntaxTree = parser.parse();

        Visitable left = new OperandNode("a");
        ((OperandNode) left).position = 1;
        Visitable right = new OperandNode("b");
        ((OperandNode) right).position = 2;
        left = new BinOpNode("|", left, right);
        left = new UnaryOpNode("*", left);
        right = new OperandNode("a");
        ((OperandNode) right).position = 3;
        left = new BinOpNode("°", left, right);
        right = new OperandNode("b");
        ((OperandNode) right).position = 4;
        left = new BinOpNode("°", left, right);
        right = new OperandNode("b");
        ((OperandNode) right).position = 5;
        left = new BinOpNode("°", left, right);
        right = new OperandNode("#");
        ((OperandNode) right).position = 6;
        Visitable refTree = new BinOpNode("°", left, right);

        assertTrue(treeCmp(syntaxTree, refTree));
    }
}
