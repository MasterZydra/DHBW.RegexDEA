import main.*;

import java.util.Map;
import java.util.Scanner;
import java.util.SortedMap;

public class RegexDEA {
    public static void main(String[] args){
        System.out.println(" ___   ____  __    ____  _         ___   ____   __");
        System.out.println("| |_) | |_  / /`_ | |_  \\ \\_/     | | \\ | |_   / /\\");
        System.out.println("|_| \\ |_|__ \\_\\_/ |_|__ /_/ \\     |_|_/ |_|__ /_/--\\");

        Scanner scanner = new Scanner(System.in);
        System.out.println("\n\nRegular expression (needs to be in parenthesis and end with '#'):");
        String input = scanner.next();

        Visitable syntaxTree = null;

        // 1. Parse to syntax tree
        Parser parser = new Parser(input);
        System.out.println("");
        try {
            syntaxTree = parser.parse();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
            return;
        }

        // 2. FirstVisitor
        FirstVisitor firstVisitor = new FirstVisitor();
        DepthFirstIterator.traverse(syntaxTree, firstVisitor);

        // 3. SecondVisitor
        SecondVisitor secondVisitor = new SecondVisitor();
        DepthFirstIterator.traverse(syntaxTree, secondVisitor);
        SortedMap<Integer, FollowposTableEntry> followPosTable = secondVisitor.getFollowPosTableEntries();

        // 4. DFA Generator
        DFAGenerator dfaGenerator = new DFAGenerator(followPosTable, ((SyntaxNode) syntaxTree).firstpos);
        dfaGenerator.fillTransitionTable();
        Map<DFAState, Map<Character, DFAState>> transTable = dfaGenerator.getStateTransitionTable();

        dfaGenerator.print();
        System.out.println("");

        // 5. Generic Lexer
        GenericLexer lexer = new GenericLexer(transTable);

        boolean endProgram = false, accept;
        while (!endProgram)
        {
            System.out.println("String to check:");
            input = scanner.next();

            accept = lexer.match(input);
            if(accept)
            {
                System.out.println("'" + input + "' is accepted.");
            }
            else
            {
                System.out.println("'" + input + "' is not accepted.");
            }

            System.out.println("Check another input? (NO)");
            input = scanner.next();
            if(input.toLowerCase().equals("no"))
            {
                endProgram = true;
            }
        }
    }
}
