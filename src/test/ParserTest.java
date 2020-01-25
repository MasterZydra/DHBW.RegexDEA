package test;

import static org.junit.Assert.*;

import main.Parser;
import main.Visitable;
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
}

/*
8.1 Standalone-TestfürdenParser(erfolgreicherParse-Vorgang)
Formulieren Sie ein oder mehrere Junit-Tests wie folgt:
Eingabe
• Ein String, der einen gültigen regulären Ausdruck darstellt
• Ein Syntaxbaum hartverdrahtet erstellt (erwartetes Ergebnis einer Auswertung des regulären
Ausdrucks)
Durchführung
• Vom Parser den regulären Ausdruck analysieren und den Syntaxbaum erstellen lassen
Abschließender Test
• Der Test ist genau dann erfolgreich, wenn der hartverdrahtete Syntaxbaum (das erwartete Ergebnis) gleich dem erzeugten Syntaxbaum (das aktuelle Ergebnis) ist (Kapitel 4.2)
*/
