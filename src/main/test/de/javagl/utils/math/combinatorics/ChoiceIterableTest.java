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
 * Test cases for the {@link ChoiceIterable} class.
 */
@RunWith(JUnit4.class)
public class ChoiceIterableTest
{
    /**
     * Basic test for the {@link ChoiceIterable} class
     */
    @Test
    public void testBasic()
    {
        int sampleSize = 2;
        List<String> input = Arrays.asList("A", "B", "C", "D");
        
        Set<List<String>> actual = 
            Utils.asSet(new ChoiceIterable<String>(sampleSize, input));
        
        Set<List<String>> expected = new HashSet<List<String>>(Arrays.asList(
            Arrays.asList("A", "B"),
            Arrays.asList("A", "C"),
            Arrays.asList("A", "D"),
            Arrays.asList("B", "C"),
            Arrays.asList("B", "D"),
            Arrays.asList("C", "D")));
        assertEquals(expected, actual);
    }
}
