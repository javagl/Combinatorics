package de.javagl.utils.math.combinatorics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

 
/**
 * Sample for the combinatorics classes. 
 */
@SuppressWarnings("javadoc")
public class CombinatoricsSample
{
    /**
     * Runs all sample methods 
     * 
     * @param args not used
     */
    public static void main(String args[])
    {
        showPermutations(3);
        showSequenceWithoutRepetitions(4,2);
        showChoices(4, 2);
        showCombinations(4, 2);
        showUnorderedCombinations(4, 2);
        showMixedRangeCombinations();
        showPowerSet(3);
    }

    /**
     * Creates the sample input for the samples
     * 
     * @param n The size of the input
     * @return The input for the sample
     */
    private static List<String> createInput(int n)
    {
        List<String> input = new ArrayList<String>();
        for (int i = 0; i < n; i++)
        {
            input.add(String.valueOf((char) ('A' + i)));
        }
        return input;
    }
 
    private static void showPermutations(int n)
    { 
        List<String> input = createInput(n);
        PermutationIterable<String> pi = new PermutationIterable<String>(input);
        System.out.println("Permutations of " + n + " elements:");
        for (List<String> s : pi)
        {
            System.out.println(s);
        }
    }
 
    private static void showSequenceWithoutRepetitions(int n, int k)
    {
        List<String> input = createInput(n);
        SequenceWithoutRepetitionIterable<String> si =
            new SequenceWithoutRepetitionIterable<String>(k, input);
        System.out.println("Sequences without repetitions of length " + k +
            " taken from a set of " + n + " elements:");
        for (List<String> s : si)
        {
            System.out.println(s);
        }
    }    
    private static void showChoices(int n, int k)
    {
        List<String> input = createInput(n);
        ChoiceIterable<String> chi = new ChoiceIterable<String>(k, input);
        System.out.println("Choices of " + k + " elements taken " +
            "from a set of " + n + " elements:");
        for (List<String> s : chi)
        {
            System.out.println(s);
        }
    }
 
    private static void showCombinations(int n, int k)
    {
        List<String> input = createInput(n);
        CombinationIterable<String> ci = new CombinationIterable<String>(k,
            input);
        System.out.println("Combinations of " + k + " elements taken " +
            "from a set of " + n + " elements:");
        for (List<String> s : ci)
        {
            System.out.println(s);
        }
    }
 
    private static void showUnorderedCombinations(int n, int k)
    {
        List<String> input = createInput(n);
        UnorderedCombinationIterable<String> uci = 
            new UnorderedCombinationIterable<String>(k, input);
        System.out.println("Combinations of " + k + " elements taken " +
            "from a set of " + n + " elements,");
        System.out.println("ignoring the order of the selected elements:");
        for (List<String> s : uci)
        {
            System.out.println(s);
        }
    }

    private static void showMixedRangeCombinations()
    {
        List<List<String>> input = new ArrayList<List<String>>();
        input.add(Arrays.asList("A", "B", "C"));
        input.add(Arrays.asList("D", "E"));
        input.add(Arrays.asList("A", "E"));
        MixedRangeCombinationIterable<String> mrci = 
            new MixedRangeCombinationIterable<String>(input);
        System.out.println("Mixed range combinations of "+input+":");
        for (List<String> s : mrci)
        {
            System.out.println(s);
        }
    }
    
    private static void showPowerSet(int n)
    {
        List<String> input = createInput(n);
        PowerSetIterable<String> psi = new PowerSetIterable<String>(input);
        System.out.println("Power set of a set with " + n + " elements:");
        for (List<String> s : psi)
        {
            System.out.println(s);
        }
 
    }
 
}
 
 
 
 
 
 
 
 
 
