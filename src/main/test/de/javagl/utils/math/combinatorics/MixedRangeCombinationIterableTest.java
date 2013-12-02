package de.javagl.utils.math.combinatorics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Test cases for the {@link MixedRangeCombinationIterable} class.
 */
@RunWith(JUnit4.class)
public class MixedRangeCombinationIterableTest
{
    /**
     * Basic test for the {@link MixedRangeCombinationIterable} class
     */
    @Test
    public void testBasic()
    {
        List<List<String>> input = new ArrayList<List<String>>();
        input.add(Arrays.asList("A", "B", "C"));
        input.add(Arrays.asList("D", "E"));
        input.add(Arrays.asList("A", "E"));
        
        Set<List<String>> actual = 
            Utils.asSet(new MixedRangeCombinationIterable<String>(input));
        
        Set<List<String>> expected = new HashSet<List<String>>(Arrays.asList(
            Arrays.asList("A", "D", "A"),
            Arrays.asList("A", "D", "E"),
            Arrays.asList("A", "E", "A"),
            Arrays.asList("A", "E", "E"),
            Arrays.asList("B", "D", "A"),
            Arrays.asList("B", "D", "E"),
            Arrays.asList("B", "E", "A"),
            Arrays.asList("B", "E", "E"),
            Arrays.asList("C", "D", "A"),
            Arrays.asList("C", "D", "E"),
            Arrays.asList("C", "E", "A"),
            Arrays.asList("C", "E", "E")));
        assertEquals(expected, actual);
    }
    
    /**
     * Test for empty inputs
     */
    @Test
    public void testEmptyInput()
    {
        List<List<String>> input = new ArrayList<List<String>>();
        input.add(Arrays.asList("A", "B", "C"));
        input.add(Arrays.<String>asList());
        input.add(Arrays.asList("A", "E"));
        
        Iterable<List<String>> iterable = 
            new MixedRangeCombinationIterable<String>(input);
        Iterator<List<String>> iterator = iterable.iterator();
        assertFalse(iterator.hasNext());
    }
    
    
    
    /**
     * Test whether the 'next()' method of the iterator throws a 
     * NoSuchElementException when the iterator is exhausted
     */
    @Test(expected=NoSuchElementException.class)
    public void testNextWhenExhausted()
    {
        List<List<String>> input = new ArrayList<List<String>>();
        input.add(Arrays.asList("A", "B", "C"));
        input.add(Arrays.asList("D", "E"));
        input.add(Arrays.asList("A", "E"));
        Iterable<List<String>> iterable = 
            new MixedRangeCombinationIterable<String>(input);
        Iterator<List<String>> iterator = iterable.iterator();
        while (iterator.hasNext())
        {
            iterator.next();
        }
        // This call should throw the NoSuchElementException
        iterator.next();
    }
    
}
