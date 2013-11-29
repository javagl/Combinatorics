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
 * Test cases for the {@link PermutationIterable} class.
 */
@RunWith(JUnit4.class)
public class PermutationIterableTest
{
	/**
	 * Basic test for the {@link PermutationIterable} class
	 */
    @Test
    public void testBasic()
    {
        List<String> input = Arrays.asList("A", "B", "C");

        Set<List<String>> actual = 
            Utils.asSet(new PermutationIterable<String>(input));
        
        Set<List<String>> expected = new HashSet<List<String>>(Arrays.asList(
        	Arrays.asList("A", "B", "C"),
        	Arrays.asList("A", "C", "B"),
        	Arrays.asList("B", "A", "C"),
        	Arrays.asList("B", "C", "A"),
        	Arrays.asList("C", "A", "B"),
        	Arrays.asList("C", "B", "A")));
        assertEquals(expected, actual);
    }
}
