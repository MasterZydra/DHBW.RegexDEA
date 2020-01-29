package main;

import java.util.*;

public class DFAGenerator
{
    private Map<DFAState, Map<Character, DFAState>> stateTransitionTable;

    private SortedMap<Integer, FollowposTableEntry> followposTableEntries;
    private Set<Integer> firstpos;

    private Set<Character> inputAlphabet;
    private Queue<DFAState> qStates;
    private int lastPosition;
    private int index;

    public DFAGenerator(SortedMap<Integer, FollowposTableEntry> followposTableEntries, Set<Integer> firstpos)
    {
        this.followposTableEntries = followposTableEntries;
        this.firstpos = firstpos;
        qStates = new LinkedList<>();
        qStates.add(new DFAState(1, false, firstpos));
        determineInputAlphabet();
        index = 1;
    }

    private void determineInputAlphabet()
    {
        inputAlphabet = new HashSet<>();
        followposTableEntries.forEach((k,v) ->
        {
            if(v.symbol.equals("#"))
            {
                lastPosition = v.position;
            }
            else if(!inputAlphabet.contains(v.symbol.charAt(0)))
            {
                inputAlphabet.add(v.symbol.charAt(0));
            }
        });
    }

    public void fillTransitionTable()
    {
        stateTransitionTable = new HashMap<>();
        DFAState s, next;
        Map<Character, DFAState> innerEntries;

        while (!qStates.isEmpty())
        {
            innerEntries = new HashMap<>();
            s = qStates.poll();
            stateTransitionTable.put(s, innerEntries);
            for(char symbol : inputAlphabet)
            {
                next = calculateNext(s, symbol);
                innerEntries.put(symbol, next);
                if(next!=null
                    && !stateTransitionTable.containsKey(next)
                    && !qStates.contains(next))
                {
                    qStates.add(next);
                }
            }
        }
    }

    private DFAState calculateNext(DFAState s, char a)
    {
        Set<Integer> nextPositionsSet = new HashSet<>();
        followposTableEntries.forEach((k,v) ->
        {
            if(s.positionsSet.contains(v.position) && v.symbol.equals(Character.toString(a)))
            {
                nextPositionsSet.addAll(v.followpos);
            }
        });

        if(nextPositionsSet.isEmpty())
        {
            return null;
        }

        boolean isAcceptingState = nextPositionsSet.contains(lastPosition);
        index++;
        return new DFAState(index, isAcceptingState, nextPositionsSet);
    }

    public Map<DFAState, Map<Character, DFAState>> getStateTransitionTable()
    {
        return stateTransitionTable;
    }
}
