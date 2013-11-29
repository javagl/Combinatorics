package de.javagl.utils.math.combinatorics;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Test cases for the {@link PowerSetIterable} class.
 */
@RunWith(JUnit4.class)
public class PowerSetIterableTest
{
    /**
     * Basic test for the {@link PowerSetIterable} class
     */
    @Test
    public void testBasic()
    {
        List<String> input = Arrays.asList("A", "B", "C");

        Set<List<String>> actual = 
            Utils.asSet(new PowerSetIterable<String>(input));
        
        Set<List<String>> expected = new HashSet<List<String>>(Arrays.asList(
            Arrays.<String>asList(),
            Arrays.asList("A"),
            Arrays.asList("B"),
            Arrays.asList("A", "B"),
            Arrays.asList("C"),
            Arrays.asList("A", "C"),
            Arrays.asList("B", "C"),
            Arrays.asList("A", "B", "C")));
        assertEquals(expected, actual);
    }
}
