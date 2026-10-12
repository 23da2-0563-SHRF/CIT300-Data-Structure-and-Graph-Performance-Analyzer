# CIT300 Data Structure and Graph Performance Analyzer

**CIT300 Graded Practical Assignment 2**

## Project Overview

A Java console-based educational application demonstrating manual implementation of core data structures, searching algorithms, graph traversal, and simple performance analysis. Each component exposes a small public API and has standalone regression tests.

**Project status:** The complete console application, performance analyzer, all member components, and all five permanent test suites are integrated into `main`. PR #6 merged final integration at `0f4c5c0`; `feature/readme-finalization` is based on this integrated version.

## Assignment Objective

Implement and demonstrate fundamental data structures and algorithms without replacing them with library implementations. Explain their behavior, compare appropriate operations, verify correctness through permanent tests, and connect measured observations with theoretical time and space complexity.

## Features

| Component | Supported functionality |
|---|---|
| Array | Insert, delete the first matching value, search, display |
| Stack | Push, pop, peek, display; last-in, first-out (LIFO) behavior |
| Queue | Enqueue, dequeue, peek/front, display; first-in, first-out (FIFO) behavior |
| Linked list | Insert, delete the first matching value, search, display |
| Searching | Manual Linear Search and Binary Search; manual ascending sorted copy |
| Graph | Add vertex, add edge, display graph, vertex search, BFS, DFS |
| Graph validation | Disconnected graph handling; duplicate vertex/edge rejection; self-loop and missing-endpoint rejection |
| Console integration | Integrated menus, safe input handling, display-all option, measured performance demonstrations and theoretical Big-O explanations |

Small runtime differences do not prove theoretical complexity. Performance output is an educational demonstration, not an accurate comparative benchmark.

## Data Structures and Algorithms Implemented

| Implementation | Design and public operations |
|---|---|
| `ArrayDataStructure` | Fixed-capacity integer array: `insert`, `delete`, `search`, `display`, `size`, `capacity`, `toArray` |
| `StackDataStructure` | Fixed-capacity array stack: `push`, `pop`, `peek`, `display`, `size`, `capacity`, `isEmpty`, `isFull` |
| `QueueDataStructure` | Fixed-capacity circular array queue: `enqueue`, `dequeue`, `peek`, `display`, `size`, `capacity`, `isEmpty`, `isFull` |
| `LinkedListDataStructure` | Singly linked integer list with head and tail: `insert`, `delete`, `search`, `display`, `size`, `isEmpty` |
| `SearchAlgorithms` | Static `linearSearch`, `binarySearch`, and insertion-sort-based `sortedCopy` |
| `GraphDataStructure` | Simple undirected graph backed by an adjacency matrix: `addVertex`, `addEdge`, `search`, `display`, `bfs`, `dfs`, `size`, `capacity`, `isEmpty` |
| `PerformanceAnalyzer` | Static `demonstrate` and `format`; reuses existing component APIs |

The required structures and algorithms are implemented manually for educational purposes. The implementation does **not** use Java Collections Framework classes to replace the required structures, including `ArrayList`, `java.util.LinkedList`, `java.util.Stack`, `java.util.Queue`, `ArrayDeque`, `HashMap`, or `HashSet`. It uses neither `Arrays.sort()` nor `Collections.sort()`, and no built-in search method replaces manual Linear Search or Binary Search. `Scanner` is used for console input.

## Team Members and Contributions

| Member | Student ID | GitHub | Contribution |
|---|---|---|---|
| A.H.M. Sharfan | 23da2-0563 | [23da2-0563-SHRF](https://github.com/23da2-0563-SHRF) | Array Data Structure, Linear Search, Binary Search, Final Integration, Performance Analysis, README / project documentation |
| MRM. Ansir Siman | 23da2-0742 | [ansir-2004](https://github.com/ansir-2004) | Stack and Queue |
| Salhan Burhan | 23da2-0926 | [Salhanburhan88](https://github.com/Salhanburhan88) | Linked List |
| K. Ahamed Sanaj | 23da2-0541 | [KamarudeenAhamedSanaj](https://github.com/KamarudeenAhamedSanaj) | Graph, BFS, and DFS |

## Technologies Used

- Java and the Java Development Kit (JDK), including `javac` and `java`.
- Primitive arrays, manually linked nodes, `StringBuilder`, and console input/output.
- Standalone Java regression tests with explicit checks and `AssertionError`; no external test framework is required.
- Git and GitHub for feature branches, meaningful commits, pull requests, review, and integration.

## Project Structure

The following tracked files are present in this checkout:

```text
CIT300-Data-Structure-and-Graph-Performance-Analyzer/
|-- .gitignore
|-- README.md
|-- src/
|   |-- Main.java
|   |-- array/ArrayDataStructure.java
|   |-- searching/SearchAlgorithms.java
|   |-- stack/StackDataStructure.java
|   |-- queue/QueueDataStructure.java
|   |-- linkedlist/LinkedListDataStructure.java
|   |-- graph/GraphDataStructure.java
|   `-- performance/PerformanceAnalyzer.java
`-- test/
    |-- ArraySearchingTest.java
    |-- StackQueueTest.java
    |-- LinkedListTest.java
    |-- GraphTraversalTest.java
    `-- FinalIntegrationTest.java
```


`build/` is the separate compilation output directory and is ignored by Git. Local IDE files and generated outputs are not part of the source listing above.

## How to Compile and Run

Install a JDK and ensure `javac` and `java` are available on `PATH`. Run these commands in **Windows CMD**, not PowerShell:

```bat
cd /d "C:\Users\Administrator\eclipse-workspace\CIT300-Data-Structure-and-Graph-Performance-Analyzer"
if not exist build mkdir build
javac -Xlint:all -d build src\Main.java src\array\*.java src\searching\*.java src\stack\*.java src\queue\*.java src\linkedlist\*.java src\graph\*.java src\performance\*.java test\*.java
java -cp build Main
```

Run all five permanent regression tests after compilation:

```bat
java -cp build test.ArraySearchingTest
java -cp build test.StackQueueTest
java -cp build test.LinkedListTest
java -cp build test.GraphTraversalTest
java -cp build FinalIntegrationTest
```

The compilation command includes all source packages and all five tests. The four component tests belong to package `test`; `Main` and `FinalIntegrationTest` have no package declaration. Compile the current sources before running the application or tests so generated classes match this checkout.

## Main Console Menu

The current integrated `src/Main.java` displays:

```text
1. Array Operations
2. Stack Operations
3. Queue Operations
4. Linked List Operations
5. Searching
6. Graph Operations
7. Performance Comparison
8. Display All Results / Demonstration
9. Exit
```

| Menu | Submenu numbering |
|---|---|
| Array / Linked List | 1. Insert; 2. Delete first match; 3. Search; 4. Display |
| Stack | 1. Push; 2. Pop; 3. Peek; 4. Display (bottom to top) |
| Queue | 1. Enqueue; 2. Dequeue; 3. Peek/front; 4. Display (front to rear) |
| Searching | 1. Linear search; 2. Binary search on sorted copy |
| Graph | 1. Add vertex; 2. Add edge; 3. Display graph; 4. Search vertex; 5. BFS; 6. DFS |

Every submenu uses `0. Back`. Searching operates on values entered through Array Operations. Binary-search indexes refer to the sorted copy, while linear-search indexes refer to the original array. Option 8 displays the current structures and runs a demonstration using separate sample instances. Invalid integers and menu selections are handled safely, as are empty stack/queue operations and end of input.

## Testing and Verification

The verified permanent results from the final integration phase are:

| Regression test | Checks passed | Failed |
|---|---:|---:|
| `ArraySearchingTest` | 41 | 0 |
| `StackQueueTest` | 104 (Stack: 25; Queue: 79) | 0 |
| `LinkedListTest` | 168 | 0 |
| `GraphTraversalTest` | 243 | 0 |
| `FinalIntegrationTest` | 40 | 0 |
| **Total** | **596** | **0** |

`javac -Xlint:all` passed with no compiler warnings or errors. The final Main smoke test passed. These are the verified permanent results from final integration, not a new test execution during README finalization. All five test files are present in the current integrated checkout.

Tests cover component behavior, empty/full conditions where applicable, integer boundaries, duplicates, graph cycles and disconnected components, traversal order, and preservation of data after rejected operations. The final integration test additionally exercises console workflows, invalid numeric/menu input, EOF handling, sample performance output, and complexity explanations. Explicit checks run without requiring `java -ea`.

## Performance / Complexity Analysis

The current `PerformanceAnalyzer` demonstrates:

- Linear Search versus Binary Search on already sorted integer arrays of sizes 16, 256, and 4096, searching for the final element. Output includes indexes, elapsed nanoseconds, and the number of elements inspected by linear search.
- Array and linked-list searches for 40 in `[10, 20, 30, 40]`, followed by deletion of 10. Array deletion shifts three surviving elements; linked-list head deletion reconnects references without shifting values. The shift count is derived from this sample, not internal instrumentation.
- Stack peek/pop returning 40 (LIFO) versus queue front/dequeue returning 10 (FIFO).
- BFS and DFS on vertices 1 through 5 with edges 1-2, 1-3, 2-4, and 3-5. BFS returns `[1, 2, 3, 4, 5]`; DFS returns `[1, 2, 4, 3, 5]`. Both report five visited vertices and elapsed nanoseconds.

Timing uses `System.nanoTime()` around single search or traversal API calls, excluding setup and display. Array/list and stack/queue demonstrations show behavior with theoretical explanations rather than measured timings. These are not rigorous JVM benchmarks: hardware, JVM warm-up/JIT compilation, timer overhead, and system load affect values. **Small runtime differences do not prove theoretical complexity.**

Let n be the number of stored elements, V the number of graph vertices, and C the graph capacity. Worst-case bounds unless stated otherwise:

| Operation | Time | Explanation |
|---|---|---|
| Array append | O(1) | Appends within fixed capacity; returns false when full |
| Array search / delete / display | O(n) | Search scans; deletion can shift survivors; display visits stored elements |
| Stack push / pop / peek | O(1) | Operates at the array's top |
| Circular queue enqueue / dequeue / peek | O(1) | Updates front/rear without shifting |
| Stack / queue display | O(n) | Visits occupied elements |
| Linked-list append | O(1) | Maintains a tail reference |
| Linked-list search / delete / display | O(n) | Deletion by value may scan for its first match |
| Linear Search | O(n) | Sequential inspection; O(1) auxiliary space |
| Binary Search loop | O(log n) | Narrows the search interval; O(1) auxiliary space |
| Complete `binarySearch` API | O(n) | Performs O(n) sorted-input validation before the search loop |
| `sortedCopy` | O(n^2) | Manual insertion sort; O(n) space for a separate copy |
| Graph vertex search / add vertex / add edge | O(V) | Resolves vertex labels by linear search |
| Graph display | O(V^2) | Scans the occupied adjacency matrix |
| BFS / DFS | O(V^2) | Adjacency-matrix neighbor scans; O(V) auxiliary space |

The graph allocates O(C^2) storage for its adjacency matrix. O(V+E) traversal bounds commonly associated with adjacency-list graphs do not describe this implementation. Sorting is a separate preprocessing cost and is excluded from the search timings.

## GitHub Collaboration Workflow

Members developed their assigned components on separate feature branches, used meaningful commits, and submitted pull requests for review and merging into `main`. Local Git history confirms merges of the array/searching, supplementary permanent array tests, stack/queue, linked-list, and graph work. PR #6 merged the final console integration, performance analyzer, and integration test into `main`.

| Branch | Work |
|---|---|
| `feature/array-searching` | Array, Linear Search, Binary Search |
| `feature/member1-array-tests` | Permanent Array/Searching test coverage |
| `feature/stack-queue` | Stack and Queue |
| `feature/linked-list` | Linked List |
| `feature/graph-traversal` | Graph, BFS, DFS |
| `feature/final-integration` | Console integration, performance demonstrations, integration testing |
| `feature/readme-finalization` | Final README documentation |

Documentation follows the same branch and pull-request workflow. This README change has not been committed or pushed as part of finalization.

## Known Design Decisions / Limitations

- The integrated interface is console-based and stores data only for the running session; there is no persistence or graphical interface.
- Data values and graph labels are Java integers. Search indexes are zero-based; missing values return -1.
- Array, stack, queue, and graph have fixed capacities. The integrated console sets each capacity to 100. The linked list has no fixed capacity.
- Array, stack, queue, and linked list allow duplicate values. Array/list deletion removes only the first match; searches return the first match.
- Stack/queue removal or peek on an empty structure throws `IllegalStateException`; the integrated console catches and displays its message.
- Binary Search requires ascending sorted input and validates it. `sortedCopy` preserves the original data using manual insertion sort.
- The graph is simple, undirected, and unweighted. Duplicate vertex labels, duplicate edges (including reversed edges), self-loops, and edges with missing endpoints are rejected.
- BFS/DFS visit only the component reachable from the chosen start. A missing start returns an empty traversal. Vertex insertion order determines neighbor order, making traversal deterministic for a fixed graph and start.
- DFS uses an explicit manual stack rather than recursion. The matrix representation prioritizes educational clarity over sparse-graph memory efficiency.
- Performance values vary by hardware and JVM. Theoretical growth rates are more informative than tiny single-call timing differences.

## Final Status

All required components, console integration, performance analysis, and five permanent test suites are integrated into `main` through PR #6. Verified final results are **596 permanent checks passed, 0 failed**, successful `javac -Xlint:all` compilation without warnings or errors, and a successful final Main smoke test.

README finalization is confined to `feature/readme-finalization`. Only README.md was updated during this documentation task; no Java sources, tests, or other project files were changed.
