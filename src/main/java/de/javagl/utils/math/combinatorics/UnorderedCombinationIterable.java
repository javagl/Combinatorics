/*
 * www.javagl.de - Utilities
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
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A class providing an iterator over all combinations of a certain number 
 * of elements from a given set, ignoring the order of the elements. For 
 * a set S with n = |S|, there are are (n+k-1)/(k!*(n-1)!) ways of 
 * choosing k elements from the set when the order of the elements in the
 * resulting set should be ignored. This is, for example, the number of
 * distinct results when throwing k dices with n sides. Example:<br />
 * <pre>
 * S = { A,B,C,D }, n = |S| = 4
 * k = 2 
 * m = (n+k-1)/(k!*(n-1)!) = 10
 * 
 * Unordered combinations of length 2:
 * [A, A]
 * [A, B]
 * [A, C]
 * [A, D]
 * [B, B]
 * [B, C]
 * [B, D]
 * [C, C]
 * [C, D]
 * [D, D]
 * </pre>
 *  
 * @param <T> The type of the elements
 */
public final class UnorderedCombinationIterable<T> implements Iterable<List<T>>
{
    /**
     * The input elements
     */
    private final List<T> input;
    
    /**
     * The length of the combinations that should be returned
     */
    private final int length;
    
    /**
     * The total number of elements that the iterator will provide
     */
    private final int numElements;
    
    /**
     * Internally used array for selecting the combinations
     */
    private final List<Integer> positions;
 
    /**
     * Creates a new iterable over all combinations of the given number
     * of elements of the given input elements, ignoring the order of
     * the elements.
     * 
     * @param length The number of elements in the combinations
     * @param input The input elements
     */
    public UnorderedCombinationIterable(int length, List<T> input)
    {
        this.length = length;
        this.input = input;
 
        int numPositions = input.size() + length - 1;
        
        // Computation of (n+k-1)!, (n-1)! and k! with BigInteger 
        // to avoid overflow 
        BigInteger npkm1f =  Utils.factorial(numPositions);
        BigInteger nm1f = Utils.factorial(input.size() - 1);
        BigInteger kf = Utils.factorial(length);
        BigInteger divisor = kf.multiply(nm1f);
        BigInteger result = npkm1f.divide(divisor);
        numElements = (int)result.longValue();
        
        positions = new ArrayList<Integer>();
        for (int i = 0; i < numPositions; i++)
        {
            positions.add(i);
        }
    }
 
    @Override
    public Iterator<List<T>> iterator()
    {
        if (length == 0)
        {
            return Collections.<List<T>>singletonList(
                Collections.<T>emptyList()).iterator();
        }
        return new Iterator<List<T>>()
        {
            /**
             * The index of the current combination
             */
            private int current = 0;
            
            /**
             * The iterator for selecting the positions that will 
             * be converted into a combination.
             */
            private final Iterator<List<Integer>> positionChoiceIterator;
 
            // Initialization of the positionChoiceIterator
            {
                ChoiceIterable<Integer> positionChoiceIterable = 
                    new ChoiceIterable<Integer>(length, positions);
                positionChoiceIterator = positionChoiceIterable.iterator();
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
                current++;
                return toSelection(positionChoiceIterator.next());
            }
 
            /**
             * Converts the given positions into a combination.
             * 
             * @param positionChoice The positions
             * @return The combination
             */
            private List<T> toSelection(List<Integer> positionChoice)
            {
                // Selecting k elements from n elements, ignoring the 
                // order of the selected elements, may be formulated
                // as selecting k elements from (n+k-1) elements 
                // obeying the order. 
                // The positionChoiceIterator provides choices of k 
                // elements from (n+k-1) elements, and they are converted
                // to the corresponding selection of elements here.
                // 
                // For example:
                // 4 elements should be selected from a set of 6 elements,
                // and the order of the selected elements is not relevant
                // (like when rolling 4 six-sided dices). 
                // The positionChoiceIterator will be used for choosing
                // 4 elements from (6+4-1)=9 elements. If it provides the 
                // choice 0,3,4,7 then this will be translated into a 
                // selection in the following way:
                // 
                // Positions array                        : 012345678
                // Choice done by positionChoiceIterator  : 0  34  7
                // For interpreting this choice, place 
                // asterisks at the respective positions
                // filling the remaining positions with 
                // consecutive numbers                    : *12**34*5
                // This pattern can then be interpreted as...
                // - take 0 once
                // - take 2 twice
                // - take 4 once
                // resulting the the selection 0,2,2,4
 
                List<T> result = new ArrayList<T>(length);
                result.addAll(Collections.<T>nCopies(length, null));
                int currentValue = 0;
                int currentIndex = 0;
                for (int x = 0; x < positions.size(); x++)
                {
                    if (x == positionChoice.get(currentIndex))
                    {
                        result.set(currentIndex, input.get(currentValue));
                        currentIndex++;
                    }
                    else
                    {
                        currentValue++;
                    }
                    if (currentIndex >= positionChoice.size())
                    {
                        break;
                    }
                }
                return result;
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
