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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A class providing an iterator over all combinations of a certain number
 * of elements of a given set. For a set S with n = |S|, there are are n^k 
 * combinations of k elements of the set. This is the number of possible
 * samples when doing sampling with replacement. Example:<br />
 * <pre>
 * S = { A,B,C }, n = |S| = 3
 * k = 2 
 * m = n^k = 9
 * 
 * Combinations:
 * [A, A]
 * [A, B]
 * [A, C]
 * [B, A]
 * [B, B]
 * [B, C]
 * [C, A]
 * [C, B]
 * [C, C]
 * </pre>
 *  
 * @param <T> The type of the elements
 */
public final class CombinationIterable<T> implements Iterable<List<T>>
{
    /**
     * The input elements
     */
    private final List<T> input;
    
    /**
     * The sample size
     */
    private final int sampleSize;
    
    /**
     * The total number of elements that the iterator will provide
     */
    private final int numElements;
 
    /**
     * Creates an iterable over all multisets of 
     * 'sampleSize' elements of the given array.
     *  
     * @param sampleSize The sample size
     * @param input The input elements
     */
    public CombinationIterable(int sampleSize, List<T> input)
    {
        this.sampleSize = sampleSize;
        this.input = input;
        numElements = (int) Math.pow(input.size(), sampleSize);
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
                increase();
                current++;
                return result;
            }
 
            /**
             * Increases the k-ary representation of the selection of 
             * elements by one.
             */
            private void increase()
            {
                // The array of 'chosen' elements for a set of size n 
                // effectively is a number represented in k-ary form, 
                // and thus, this method does nothing else than count. 
                // For example, when choosing 2 elements of a set with 
                // n=10, the contents of 'chosen' would represent all
                // values 
                // 00, 01, 02,... 09,
                // 10, 11, 12,... 19,
                // ...
                // 90, 91, 92, ...99
                // with each digit indicating the index of the element
                // of the input array that should be placed at the
                // respective position of the output array.
                int index = chosen.length - 1;
                while (index >= 0)
                {
                    if (chosen[index] < input.size() - 1)
                    {
                        chosen[index]++;
                        return;
                    }
                    chosen[index] = 0;
                    index--;
                }
            }
 
            @Override
            public void remove()
            {
                throw new UnsupportedOperationException(
                    "May not remove elements from a combination");
            }
        };
    }
}
