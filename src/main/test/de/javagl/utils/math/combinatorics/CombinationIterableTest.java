package de.javagl.utils.math.combinatorics;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

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
    
    /**
     * Test for empty inputs
     */
    @Test
    public void testEmptyInput()
    {
        int sampleSize = 2;
        List<String> input = Arrays.asList();
        
        Iterable<List<String>> iterable = 
            new CombinationIterable<String>(sampleSize, input);
        Iterator<List<String>> iterator = iterable.iterator();
        assertFalse(iterator.hasNext());
    }
    
    /**
     * Test for zero-size samples
     */
    @Test
    public void testEmptySample()
    {
        int sampleSize = 0;
        List<String> input = Arrays.asList("A", "B", "C", "D");
        
        Iterable<List<String>> iterable = 
            new CombinationIterable<String>(sampleSize, input);
        Iterator<List<String>> iterator = iterable.iterator();
        
        assertTrue(iterator.hasNext());
        assertEquals(Collections.emptyList(), iterator.next());
        assertFalse(iterator.hasNext());
    }
    
    /**
     * Test whether the 'next()' method of the iterator throws a 
     * NoSuchElementException when the iterator is exhausted
     */
    @Test(expected=NoSuchElementException.class)
    public void testNextWhenExhausted()
    {
        int sampleSize = 2;
        List<String> input = Arrays.asList("A", "B", "C", "D");
        Iterable<List<String>> iterable = 
            new CombinationIterable<String>(sampleSize, input);
        Iterator<List<String>> iterator = iterable.iterator();
        while (iterator.hasNext())
        {
            iterator.next();
        }
        // This call should throw the NoSuchElementException
        iterator.next();
    }
    
}
