# Autocomplete Engine

A Java implementation of an autocomplete system built using three distinct data structures, each implementing a common `Autocomplete` interface. Designed and analyzed trade-offs between array-based and tree-based approaches for prefix matching across datasets of 100,000+ terms, including real-world city names and DNA subsequence search.

## Implementations

- **SequentialSearchAutocomplete** — stores terms in an unsorted ArrayList; linear scan for all matches, O(N)
- **BinarySearchAutocomplete** — maintains a sorted ArrayList; uses binary search to find the first match in O(log N), then collects K matches
- **TernarySearchTreeAutocomplete** — character-level ternary search tree with recursive insert and collectAll traversal; efficient prefix lookup with O(log N + K) search

## Key Concepts

Data Structures, Algorithms, Asymptotic Analysis, Binary Search, Ternary Search Trees, Java Generics, Collections Framework

## Runtime Comparison

| Implementation | addAll | allMatches |
|---|---|---|
| SequentialSearch | O(1) | O(N) |
| BinarySearch | O(N log N) | O(log N + K) |
| TernarySearchTree | O(1) | O(log N + K) |
| TreeSet (reference) | O(log N) | O(log N + K) |

Experimental analysis confirmed that sequential search scales linearly for `allMatches` while tree-based and binary search implementations stay near constant time for large N.
