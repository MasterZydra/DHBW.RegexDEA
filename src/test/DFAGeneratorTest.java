package test;

import main.*;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class DFAGeneratorTest
{
    @Test
    public void test1()
    {
        Set<Integer> firstposRoot = new HashSet<>(Arrays.asList(1, 2));

        DFAGenerator generator = new DFAGenerator(generateFollowposTable1(), firstposRoot);
        generator.fillTransitionTable();

        Map<DFAState, Map<Character, DFAState>> expectedMap = generateExpectedMap1();

        assertEquals(expectedMap, generator.getStateTransitionTable());
    }

    private SortedMap<Integer, FollowposTableEntry> generateFollowposTable1()
    {
        SortedMap<Integer, FollowposTableEntry> followposTable = new TreeMap<>();
        FollowposTableEntry entry = new FollowposTableEntry(1, "a");
        entry.followpos.addAll(Arrays.asList(1, 2, 3, 4, 5));
        followposTable.put(1, entry);
        entry = new FollowposTableEntry(2, "b");
        entry.followpos.addAll(Arrays.asList(1, 2, 3, 4, 5));
        followposTable.put(2, entry);
        entry = new FollowposTableEntry(3, "b");
        entry.followpos.addAll(Arrays.asList(3, 4, 5));
        followposTable.put(3, entry);
        entry = new FollowposTableEntry(4, "c");
        entry.followpos.addAll(Arrays.asList(3, 4, 5));
        followposTable.put(4, entry);
        entry = new FollowposTableEntry(5, "d");
        entry.followpos.add(6);
        followposTable.put(5, entry);
        entry = new FollowposTableEntry(6, "#");
        followposTable.put(6, entry);

        return followposTable;
    }

    private Map<DFAState, Map<Character, DFAState>> generateExpectedMap1()
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

    @Test
    public void test2()
    {
        Set<Integer> firstposRoot = new HashSet<>(Arrays.asList(1, 2, 3));

        DFAGenerator generator = new DFAGenerator(generateFollowposTable2(), firstposRoot);
        generator.fillTransitionTable();

        Map<DFAState, Map<Character, DFAState>> expectedMap = generateExpectedMap2();

        assertEquals(expectedMap, generator.getStateTransitionTable());
    }

    private SortedMap<Integer, FollowposTableEntry> generateFollowposTable2()
    {
        SortedMap<Integer, FollowposTableEntry> followposTable = new TreeMap<>();
        FollowposTableEntry entry = new FollowposTableEntry(1, "a");
        entry.followpos.addAll(Arrays.asList(1, 2, 3));
        followposTable.put(1, entry);
        entry = new FollowposTableEntry(2, "b");
        entry.followpos.addAll(Arrays.asList(1, 2, 3));
        followposTable.put(2, entry);
        entry = new FollowposTableEntry(3, "a");
        entry.followpos.add(4);
        followposTable.put(3, entry);
        entry = new FollowposTableEntry(4, "b");
        entry.followpos.add(5);
        followposTable.put(4, entry);
        entry = new FollowposTableEntry(5, "b");
        entry.followpos.add(6);
        followposTable.put(5, entry);
        entry = new FollowposTableEntry(6, "#");
        followposTable.put(6, entry);

        return followposTable;
    }

    private Map<DFAState, Map<Character, DFAState>> generateExpectedMap2()
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
