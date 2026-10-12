package performance;

import array.ArrayDataStructure;
import searching.SearchAlgorithms;
import stack.StackDataStructure;
import queue.QueueDataStructure;
import linkedlist.LinkedListDataStructure;
import graph.GraphDataStructure;

/** Educational API measurements, not a rigorous JVM benchmark. */
public final class PerformanceAnalyzer {
    private PerformanceAnalyzer() { }

    /** Fresh samples leave console data unchanged. */
    public static String demonstrate() {
        StringBuilder out = new StringBuilder("Performance demonstration (separate sample data)\n");
        out.append("Times: single API calls in nanoseconds, excluding setup and display.\n")
                .append("JIT, timer overhead and system load affect times; tiny differences do not prove complexity.\n");
        for (int n : new int[] {16, 256, 4096}) {
            int[] sorted = new int[n];
            for (int i = 0; i < n; i++) { sorted[i] = i; }
            long begin = System.nanoTime();
            int linear = SearchAlgorithms.linearSearch(sorted, n - 1);
            long linearTime = System.nanoTime() - begin;
            begin = System.nanoTime();
            int binary = SearchAlgorithms.binarySearch(sorted, n - 1);
            long binaryTime = System.nanoTime() - begin;
            out.append("n=").append(n).append(", target=").append(n - 1)
                    .append(": linear index=").append(linear).append(", ns=").append(linearTime)
                    .append("; binary index=").append(binary).append(", ns=").append(binaryTime)
                    .append("; linear inspected elements=").append(linear + 1).append('\n');
        }
        out.append("Linear search: O(n) worst-case time, O(1) auxiliary space.\n")
                .append("Binary search loop: O(log n) time, O(1) auxiliary space; this API first validates\n")
                .append("ascending order in O(n), so the complete binarySearch call is O(n).\n")
                .append("Inputs are already sorted. sortedCopy uses insertion sort: O(n^2) worst-case time,\n")
                .append("O(n) copy space; sorting is separate and excluded from timings.\n");
        ArrayDataStructure array = new ArrayDataStructure(4);
        LinkedListDataStructure list = new LinkedListDataStructure();
        StackDataStructure stack = new StackDataStructure(4);
        QueueDataStructure queue = new QueueDataStructure(4);
        for (int value : new int[] {10, 20, 30, 40}) {
            array.insert(value); list.insert(value); stack.push(value); queue.enqueue(value);
        }
        out.append("Array before deletion: ").append(array.display()).append('\n');
        out.append("Array search 40 index=").append(array.search(40)).append('\n');
        array.delete(10);
        out.append("Array delete 10: ").append(array.display())
                .append("; 3 surviving elements shifted (derived from this sample).\n")
                .append("Array append O(1) within fixed capacity; search/delete/display O(n) worst case.\n");
        out.append("Linked list search 40 index=").append(list.search(40)).append('\n');
        list.delete(10);
        out.append("Linked list delete 10: ").append(list.display()).append('\n')
                .append("Linked list append O(1) using tail; search/delete/display O(n) worst case.\n")
                .append("Deleting the head needs no element shifts; deletion by value may scan.\n");
        out.append("Stack top=").append(stack.peek()).append(", pop=").append(stack.pop())
                .append(", remaining=").append(stack.display()).append(" (LIFO).\n");
        out.append("Queue front=").append(queue.peek()).append(", dequeue=").append(queue.dequeue())
                .append(", remaining=").append(queue.display()).append(" (FIFO).\n")
                .append("Stack push/pop/peek and circular queue enqueue/dequeue/peek: O(1); display O(n).\n");
        GraphDataStructure graph = new GraphDataStructure(5);
        for (int vertex = 1; vertex <= 5; vertex++) { graph.addVertex(vertex); }
        graph.addEdge(1, 2); graph.addEdge(1, 3); graph.addEdge(2, 4); graph.addEdge(3, 5);
        long begin = System.nanoTime();
        int[] bfs = graph.bfs(1);
        long bfsTime = System.nanoTime() - begin;
        begin = System.nanoTime();
        int[] dfs = graph.dfs(1);
        long dfsTime = System.nanoTime() - begin;
        out.append("Graph: ").append(graph.display()).append('\n');
        out.append("BFS from 1: ").append(format(bfs)).append("; visited=").append(bfs.length)
                .append("; ns=").append(bfsTime).append('\n');
        out.append("DFS from 1: ").append(format(dfs)).append("; visited=").append(dfs.length)
                .append("; ns=").append(dfsTime).append('\n');
        out.append("BFS explores levels using a queue; DFS follows paths using a stack.\n")
                .append("Both visit only reachable vertices; neighbors follow vertex insertion order.\n")
                .append("This adjacency-matrix implementation: O(V^2) worst-case traversal time, O(V) auxiliary space.\n")
                .append("Graph storage O(C^2) for capacity C; vertex search/add and edge add O(V); display O(V^2).\n")
                .append("Adjacency-list traversals can be O(V+E); that is not this implementation.\n");
        return out.toString();
    }

    /** Formats primitive arrays without collections. */
    public static String format(int[] values) {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < values.length; i++) {
            if (i > 0) { result.append(", "); }
            result.append(values[i]);
        }
        return result.append(']').toString();
    }
}
