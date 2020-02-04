package test;

import main.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.*;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class GenericLexerTest {

    @Parameter
    public String input;

    @Parameter(1)
    public boolean result1;

    @Parameter(2)
    public boolean result2;

    @Test
    public void test1()
    {
        GenericLexer lexer = new GenericLexer(generateMap1());

        assertEquals(result1, lexer.match(input));
    }

    @Test
    public void test2()
    {
        GenericLexer lexer = new GenericLexer(generateMap2());

        assertEquals(result2, lexer.match(input));
    }

    @Parameters
    public static Object[] parametersForTest() {
        return new Object[] {
                new Object[] { "acd", true, false },
                new Object[] { "bd", true, false },
                new Object[] { "aaccd", true, false },
                new Object[] { "aabb", false, true },
                new Object[] { "bababb", false, true },
                new Object[] { "abbbababb", false, true },
                new Object[] { "", false, false}
        };
    }

    private Map<DFAState, Map<Character, DFAState>> generateMap1()
    {
        Map<DFAState, Map<Character, DFAState>> map = new HashMap<>();

        DFAState s1 = new DFAState(1, false, new HashSet<>(Arrays.asList(1,2)));
        DFAState s2 = new DFAState(2, false, new HashSet<>(Arrays.asList(1,2,3,4,5)));
        DFAState s3 = new DFAState(3, false, new HashSet<>(Arrays.asList(3,4,5)));
        DFAState s4 = new DFAState(4, true, new HashSet<>(Collections.singletonList(6)));

        Map<Character, DFAState> innerEntries = new HashMap<>();
        innerEntries.put('a', s2);
        innerEntries.put('b', s2);
        innerEntries.put('c', null);
        innerEntries.put('d', null);
        map.put(s1, innerEntries);

        innerEntries = new HashMap<>();
        innerEntries.put('a', s2);
        innerEntries.put('b', s2);
        innerEntries.put('c', s3);
        innerEntries.put('d', s4);
        map.put(s2, innerEntries);

        innerEntries = new HashMap<>();
        innerEntries.put('a', null);
        innerEntries.put('b', s3);
        innerEntries.put('c', s3);
        innerEntries.put('d', s4);
        map.put(s3, innerEntries);

        innerEntries = new HashMap<>();
        innerEntries.put('a', null);
        innerEntries.put('b', null);
        innerEntries.put('c', null);
        innerEntries.put('d', null);
        map.put(s4, innerEntries);

        return map;
    }

    private Map<DFAState, Map<Character, DFAState>> generateMap2()
    {
        Map<DFAState, Map<Character, DFAState>> map = new HashMap<>();

        DFAState s1 = new DFAState(1, false, new HashSet<>(Arrays.asList(1,2,3)));
        DFAState s2 = new DFAState(2, false, new HashSet<>(Arrays.asList(1,2,3,4)));
        DFAState s3 = new DFAState(3, false, new HashSet<>(Arrays.asList(1,2,3,5)));
        DFAState s4 = new DFAState(4, true, new HashSet<>(Arrays.asList(1,2,3,6)));

        Map<Character, DFAState> innerEntries = new HashMap<>();
        innerEntries.put('a', s2);
        innerEntries.put('b', s1);
        map.put(s1, innerEntries);

        innerEntries = new HashMap<>();
        innerEntries.put('a', s2);
        innerEntries.put('b', s3);
        map.put(s2, innerEntries);

        innerEntries = new HashMap<>();
        innerEntries.put('a', s2);
        innerEntries.put('b', s4);
        map.put(s3, innerEntries);

        innerEntries = new HashMap<>();
        innerEntries.put('a', s2);
        innerEntries.put('b', s1);
        map.put(s4, innerEntries);

        return map;
    }
}
