package main;

import java.util.Map;

public class GenericLexer
{
    private Map<DFAState, Map<Character, DFAState>> stateTransitionsTable;

    public GenericLexer(Map<DFAState, Map<Character, DFAState>> stateTransitionTable)
    {
        this.stateTransitionsTable = stateTransitionTable;
    }

    public boolean match(String input)
    {
        char[] testable = input.toCharArray();

        DFAState state = getInitialState();

        for (char letter: testable)
        {
            state = stateTransitionsTable.get(state).get(letter);

            if(state == null)
            {
                return false;
            }
        }

        return state.isAcceptingState;
    }

    private DFAState getInitialState ()
    {
        for(Map.Entry<DFAState, Map<Character, DFAState>> entry : stateTransitionsTable.entrySet())
        {
            if(entry.getKey().index == 1)
            {
                return entry.getKey();
            }
        }

        return null;
    }
}
