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
    
    /**
     * Test for empty inputs 
     */
    @Test
    public void testEmptyInput()
    {
        List<String> input = Arrays.asList();
        
        Iterable<List<String>> iterable = 
            new PowerSetIterable<String>(input);
        Iterator<List<String>> iterator = iterable.iterator();
        
        // The power set of the empty set is the set containing the empty set
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
        List<String> input = Arrays.asList("A", "B", "C", "D");
        Iterable<List<String>> iterable = 
            new PowerSetIterable<String>(input);
        Iterator<List<String>> iterator = iterable.iterator();
        while (iterator.hasNext())
        {
            iterator.next();
        }
        // This call should throw the NoSuchElementException
        iterator.next();
    }
    
}
