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
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A class providing an iterator over all choices of a certain number of 
 * elements from a given set. For a set S with n = |S|, there are are 
 * n!/(k!*(n-k)!) ways of choosing k elements from the set. This is 
 * the number of possible samples when doing sampling without 
 * replacement. Example:<br />
 * <pre>
 * S = { A,B,C,D }, n = |S| = 4
 * k = 2 
 * m = n!/(k!*(n-k)!) = 6
 * 
 * Choices:
 * [A, B]
 * [A, C]
 * [A, D]
 * [B, C]
 * [B, D]
 * [C, D]
 * </pre>
 * 
 * @param <T> The type of the elements
 */
public final class ChoiceIterable<T> implements Iterable<List<T>>
{
    /**
     * The input elements
     */
    private final List<T> input;
    
    /**
     * The size of one sample
     */
    private final int sampleSize;
    
    /**
     * The total number of elements that the iterator will provide
     */
    private final long numElements;
 
    /**
     * Creates an iterable over all choices of 'sampleSize' 
     * elements taken from the given array.
     *  
     * @param sampleSize The sample size
     * @param input The input elements
     */
    public ChoiceIterable(int sampleSize, List<T> input)
    {
        this.sampleSize = sampleSize;
        this.input = input;
 
        // Computation of n!, k! and (n-k)! with BigInteger to avoid overflow 
        BigInteger nf = Utils.factorial(input.size());
        BigInteger kf = Utils.factorial(sampleSize);
        BigInteger nmkf = Utils.factorial(input.size() - sampleSize);
        BigInteger divisor = kf.multiply(nmkf);
        BigInteger result = nf.divide(divisor);
        numElements = result.longValue();    
    }

    @Override
    public Iterator<List<T>> iterator()
    {
        return new Iterator<List<T>>()
        {
            /**
             * The element counter
             */
            private int current = 0;
            
            /**
             * The indices of the elements that are currently chosen
             */
            private final int chosen[] = new int[sampleSize];
 
            // Initialization of first choice
            {
                for (int i = 0; i < sampleSize; i++)
                {
                    chosen[i] = i;
                }
            }
 
            
            @Override
            public boolean hasNext()
            {
                return current < numElements;
            }
 
            @Override
            public List<T> next()
            {
                if (!hasNext())
                {
                    throw new NoSuchElementException("No more elements");
                }
                
                List<T> result = new ArrayList<T>(sampleSize);
                for (int i = 0; i < sampleSize; i++)
                {
                    result.add(input.get(chosen[i]));
                }
                current++;
                if (current < numElements)
                {
                    increase(sampleSize - 1, input.size() - 1);
                }
                return result;
            }
 
            
            /**
             * Increase the index of the element number 'n'
             * 
             * @param n The index of the chosen element to increase
             * @param max The maximum value for the index
             */
            private void increase(int n, int max)
            {
                // The fist choice when choosing 3 of 5 elements consists
                // of 0,1,2. Subsequent choices are created by increasing
                // the last element of this sequence:
                // 0,1,3
                // 0,1,4
                // until the last element of the choice has reached the
                // maximum value. Then, the earlier elements of the 
                // sequence are increased recursively, while obeying the 
                // maximum value each element may have so that there may 
                // still be values assigned to the subsequent elements.
                // For the example: 
                // - The element with index 2 may have maximum value 4.
                // - The element with index 1 may have maximum value 3.
                // - The element with index 0 may have maximum value 2.
                // Each time that the value of one of these elements is
                // increased, the subsequent elements will simply receive
                // the subsequent values.
                if (chosen[n] < max)
                {
                    chosen[n]++;
                    for (int i = n + 1; i < sampleSize; i++)
                    {
                        chosen[i] = chosen[i - 1] + 1;
                    }
                }
                else
                {
                    increase(n - 1, max - 1);
                }
            }
 
            @Override
            public void remove()
            {
                throw new UnsupportedOperationException(
                    "May not remove elements from a choice");
            }
        };
    }
}
