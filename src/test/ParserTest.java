package test;

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

        Visitable right = new OperandNode("b");
        Visitable left = new OperandNode("a");
        left = new BinOpNode("°", left, right);
        right = new OperandNode("c");
        left = new BinOpNode("°", left, right);
        right = new OperandNode("#");
        Visitable refTree = new BinOpNode("°", left, right);

        assertTrue(treeCmp(syntaxTree, refTree));
    }

    @Test
    public void validSyntax_Alternative() {
        Parser parser = new Parser("(a|b)#");
        Visitable syntaxTree = parser.parse();

        Visitable right = new OperandNode("b");
        Visitable left = new OperandNode("a");
        left = new BinOpNode("|", left, right);
        right = new OperandNode("#");
        Visitable refTree = new BinOpNode("°", left, right);

        assertTrue(treeCmp(syntaxTree, refTree));
    }

    @Test
    public void validSyntax_KleeneStar() {
        Parser parser = new Parser("(a*)#");
        Visitable syntaxTree = parser.parse();

        Visitable subNode = new OperandNode("a");
        Visitable left = new UnaryOpNode("*", subNode);
        Visitable right = new OperandNode("#");
        Visitable refTree = new BinOpNode("°", left, right);

        assertTrue(treeCmp(syntaxTree, refTree));
    }

    @Test
    public void validSyntax_KleenePlus() {
        Parser parser = new Parser("(a+)#");
        Visitable syntaxTree = parser.parse();

        Visitable subNode = new OperandNode("a");
        Visitable left = new UnaryOpNode("+", subNode);
        Visitable right = new OperandNode("#");
        Visitable refTree = new BinOpNode("°", left, right);

        assertTrue(treeCmp(syntaxTree, refTree));
    }

    @Test
    public void validSyntax_Option() {
        Parser parser = new Parser("(a?)#");
        Visitable syntaxTree = parser.parse();

        Visitable subNode = new OperandNode("a");
        Visitable left = new UnaryOpNode("?", subNode);
        Visitable right = new OperandNode("#");
        Visitable refTree = new BinOpNode("°", left, right);

        assertTrue(treeCmp(syntaxTree, refTree));
    }

    @Test
    public void validSyntax_Complex() {
        Parser parser = new Parser("((a|b)*abb)#");
        Visitable syntaxTree = parser.parse();

        Visitable right = new OperandNode("b");
        Visitable left = new OperandNode("a");
        left = new BinOpNode("|", left, right);
        left = new UnaryOpNode("*", left);
        right = new OperandNode("a");
        left = new BinOpNode("°", left, right);
        right = new OperandNode("b");
        left = new BinOpNode("°", left, right);
        right = new OperandNode("b");
        left = new BinOpNode("°", left, right);
        right = new OperandNode("#");
        Visitable refTree = new BinOpNode("°", left, right);

        assertTrue(treeCmp(syntaxTree, refTree));
    }
}
