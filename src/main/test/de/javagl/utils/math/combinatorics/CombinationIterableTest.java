package de.javagl.utils.math.combinatorics;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

/**
 * Test cases for the {@link CombinationIterable} class.
 */
@RunWith(JUnit4.class)
public class CombinationIterableTest
{
    /**
     * Basic test for the {@link CombinationIterable} class
     */
    @Test
    public void testBasic()
    {
        int sampleSize = 2;
        List<String> input = Arrays.asList("A", "B", "C");
        
        Set<List<String>> actual = 
            Utils.asSet(new CombinationIterable<String>(sampleSize, input));
        
        Set<List<String>> expected = new HashSet<List<String>>(Arrays.asList(
            Arrays.asList("A", "A"),
            Arrays.asList("A", "B"),
            Arrays.asList("A", "C"),
            Arrays.asList("B", "A"),
            Arrays.asList("B", "B"),
            Arrays.asList("B", "C"),
            Arrays.asList("C", "A"),
            Arrays.asList("C", "B"),
            Arrays.asList("C", "C")));
        assertEquals(expected, actual);
    }
}
