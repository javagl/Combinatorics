package de.javagl.utils.math.combinatorics;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A class providing an iterator over all sequences without repetition
 * of a certain number of elements taken from a set. For a set S with
 * n=|S|, there are m=n!/(n-k)! different sequences of k elements
 * without repetition. Example: <br />
 * <pre>
 * S = { A,B,C,D }, n = |S| = 4
 * k = 2
 * m = n!/(n-k)! = 12
 *
 * Sequences without repetition:
 * [A, B]
 * [B, A]
 * [A, C]
 * [C, A]
 * [A, D]
 * [D, A]
 * [B, C]
 * [C, B]
 * [B, D]
 * [D, B]
 * [C, D]
 * [D, C]
 * </pre>
 *
 * @param <T> The type of the elements
 */
public final class SequenceWithoutRepetitionIterable<T> 
    implements Iterable<List<T>>
{
    /**
     * The input elements
     */
    private final List<T> input;
    
    /**
     * The size of each sample that will be provided by the iterator
     */
    private final int sampleSize;
 
    /**
     * Creates a new iterable over all sequences without repetition
     * of the given size from the given input elements
     *  
     * @param sampleSize The sample size
     * @param input The input elements
     */
    public SequenceWithoutRepetitionIterable(int sampleSize, List<T> input)
    {
        this.input = input;
        this.sampleSize = sampleSize;
    }
 
    @Override
    public Iterator<List<T>> iterator()
    {
        if (input.size() == 0 || sampleSize == 0)
        {
            return Collections.<List<T>>emptyList().iterator();
        }
        return new Iterator<List<T>>()
        {
            // Sequences without repetition may be constructed
            // by iterating over all permutations of all
            // choices of the given number of elements.
 
            /**
             * The iterator over all choices
             */
            private final Iterator<List<T>> choiceIterator =
                new ChoiceIterable<T>(sampleSize, input).iterator();
            
            /**
             * The iterator over all permutations of the current choice
             */
            private Iterator<List<T>> permutationIterator = null;
 
            @Override
            public boolean hasNext()
            {
                if (permutationIterator != null &&
                    permutationIterator.hasNext())
                {
                    return true;
                }
                if (choiceIterator.hasNext())
                {
                    List<T> nextChoice = choiceIterator.next();
                    permutationIterator =
                        new PermutationIterable<T>(nextChoice).iterator();
                }
                return permutationIterator.hasNext();
            }
 
            @Override
            public List<T> next()
            {
                if (!hasNext())
                {
                    throw new NoSuchElementException("No more elements");
                }
                if (permutationIterator == null)
                {
                    List<T> nextChoice = choiceIterator.next();
                    permutationIterator =
                        new PermutationIterable<T>(nextChoice).iterator();
                }
                return permutationIterator.next();
            }
 
            @Override
            public void remove()
            {
                throw new UnsupportedOperationException(
                    "May not remove elements from a "+
                    "SequenceWithoutRepetitionIterable");
            }
        };
    }
}