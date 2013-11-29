Combinatorics
===============

Utility classes for combinatorics.

General information
-------------------

The classes in this library are mainly intended as small utility
classes and demo cases for various combinatorial computations.
The focus is on simplicity, understandability and ease of use.
They are not intended as a "combinatorics framework", but merely
as a quarry for code snippets.

Each kind of combinatorial computation that is offered by these
classes comes as an `Iterable`. Thus, they can easily be used in 
`foreach` loops. They usually expect their input as a `List`,
and provide combinations of elements as new `List`s.

Example:
```
List<String> input = Arrays.asList("A", "B", "C");
PermutationIterable<String> permutationIterable = 
    new PermutationIterable<String>(input);
for (List<String> permutation : permutationIterable)
{
    System.out.println(permutation);
}
```

Output:
```
[A, B, C]
[A, C, B]
[B, A, C]
[B, C, A]
[C, A, B]
[C, B, A]
```

Each class contains a JavaDoc class comment explaining what
it actually computes, and an example showing the input 
and output. 

An advantage of offering the computation results via an
`Iterable` is that the result can be computed on the
fly. However, when a collection with all resulting elements
is required, such a collection can be created using one
of the methods in the `Utils` class. 

Example:
```
List<String> input = Arrays.asList("A", "B", "C");
List<List<String>> permutations =
    Utils.asList(new PermutationIterable<String>(input));
```
