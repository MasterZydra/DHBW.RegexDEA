package main;

import java.util.SortedMap;
import java.util.TreeMap;

public class SecondVisitor implements Visitor {
    private SortedMap<Integer, FollowposTableEntry> followposTableEntries = new TreeMap<>();

    public void visit(OperandNode node) {
        FollowposTableEntry followposTableEntry = new FollowposTableEntry(node.position, node.symbol);
        followposTableEntries.put(node.position, followposTableEntry);
    }

    public void visit(BinOpNode node) {
        if (node.operator.equals("°")) {
            SyntaxNode leftNode = (SyntaxNode) node.left;
            for (int i : leftNode.lastpos) {
                SyntaxNode rightNode = (SyntaxNode) node.right;
                followposTableEntries.get(i).followpos.addAll(rightNode.firstpos);
            }
        }
    }

    public void visit(UnaryOpNode node) {
        if (node.operator.equals("*") || node.operator.equals("+")) {
            SyntaxNode innerNode = (SyntaxNode) node.subNode;
            for (int i : innerNode.lastpos) {
                followposTableEntries.get(i).followpos.addAll(innerNode.firstpos);
            }
        }
    }

    public SortedMap<Integer, FollowposTableEntry> getFollowPosTableEntries() {
        return this.followposTableEntries;
    }
}
