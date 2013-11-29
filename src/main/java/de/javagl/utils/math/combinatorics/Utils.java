/*
 * www.javagl.de - Utilities - Combinatorics
 *
 * Copyright (c) 2008-2013 Marco Hutter - http://www.javagl.de
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 */

package de.javagl.utils.math.combinatorics;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility methods used in the combinatorics package
 */
public class Utils
{
    /**
     * Utility method for computing the factorial n! of a number n.
     * The factorial of a number n is n*(n-1)*(n-2)*...*1, or more
     * formally:<br />
     * 0! = 1 <br />
     * 1! = 1 <br />
     * n! = n*(n-1)!<br />
     *
     * @param n The number of which the factorial should be computed
     * @return The factorial, i.e. n!
     */
    public static BigInteger factorial(int n)
    {
        BigInteger f = BigInteger.ONE;
        for (int i = 2; i <= n; i++)
        {
            f = f.multiply(BigInteger.valueOf(i));
        }
        return f;
    }    
    /**
     * A magic utility method that happens to return the number of
     * bits that are set to '1' in the given number.
     *  
     * @param n The number whose bits should be counted
     * @return The number of bits that are '1' in n
     */
    public static int countBits(int n)
    {
        int m = n - ((n >> 1) & 033333333333) - ((n >> 2) & 011111111111);
        return ((m + (m >> 3)) & 030707070707) % 63;
    }
    
    /**
     * Add all elements from the given iterable into the given collection
     * 
     * @param <T> A type that is related to the elements 
     * @param iterable The iterable
     * @param collection The collection
     */
    public static <T> void addAll(
        Iterable<? extends T> iterable, Collection<? super T> collection)
    {
        for (T t : iterable)
        {
            collection.add(t);
        }
    }
    
    /**
     * Returns all elements from the given iterable as a list
     * 
     * @param <T> A type that is related to the elements 
     * @param iterable The iterable
     * @return The list
     */
    public static <T> List<T> asList(Iterable<? extends T> iterable)
    {
        List<T> list = new ArrayList<T>();
        addAll(iterable, list);
        return list;
    }

    /**
     * Returns all elements from the given iterable as a set
     * 
     * @param <T> A type that is related to the elements 
     * @param iterable The iterable
     * @return The set
     */
    public static <T> Set<T> asSet(Iterable<? extends T> iterable)
    {
        Set<T> set = new LinkedHashSet<T>();
        addAll(iterable, set);
        return set;
    }
    
    /**
     * Private constructor to prevent instantiation
     */
    private Utils()
    {
        
    }
}
