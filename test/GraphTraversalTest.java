package test;

import graph.GraphDataStructure;

/** Member 4 standalone checks; failures throw AssertionError without -ea. */
public class GraphTraversalTest {
    private static int checks;

    public static void main(String[] args) {
        expectInvalid(0);
        expectInvalid(-1);
        GraphDataStructure empty = new GraphDataStructure(1);
        check(empty.isEmpty() && empty.size() == 0 && empty.capacity() == 1, "empty state");
        check(empty.display().equals("[]"), "empty display");
        check(empty.search(0) == -1, "unused slots are not vertices");
        check(!empty.addEdge(0, 1), "empty edge rejected");
        order(empty.bfs(0), new int[0], "empty BFS");
        order(empty.dfs(0), new int[0], "empty DFS");
        check(empty.addVertex(0), "singleton insertion");
        check(!empty.addVertex(0) && !empty.addVertex(1), "duplicate and capacity rejection");
        check(empty.size() == 1 && !empty.isEmpty() && empty.search(0) == 0, "singleton state");
        check(!empty.addEdge(0, 0), "singleton self-loop rejected");
        check(empty.display().equals("[0: []]"), "singleton adjacency");
        order(empty.bfs(0), new int[] {0}, "singleton BFS");
        order(empty.dfs(0), new int[] {0}, "singleton DFS");
        order(empty.bfs(1), new int[0], "missing BFS start");
        order(empty.dfs(1), new int[0], "missing DFS start");

        GraphDataStructure graph = new GraphDataStructure(8);
        int[] labels = {10, -2, 30, 0, 50, 60, 70, 80};
        for (int i = 0; i < labels.length; i++) {
            check(graph.addVertex(labels[i]), "vertex insertion " + i);
            check(graph.search(labels[i]) == i, "vertex index " + i);
        }
        check(!graph.addVertex(10) && !graph.addVertex(99) && graph.size() == 8, "full rejection");
        check(graph.search(99) == -1, "missing search");
        // Edge insertion order deliberately differs from vertex order.
        check(graph.addEdge(10, 30), "edge 10-30");
        check(graph.addEdge(10, -2), "edge 10--2");
        check(graph.addEdge(-2, 0), "edge -2-0");
        check(graph.addEdge(30, 50), "edge 30-50");
        check(graph.addEdge(0, 50), "cycle edge 0-50");
        check(graph.addEdge(60, 70), "disconnected edge");
        String adjacency = "[10: [-2, 30], -2: [10, 0], 30: [10, 50], 0: [-2, 50], "
                + "50: [30, 0], 60: [70], 70: [60], 80: []]";
        check(graph.display().equals(adjacency), "symmetric ordered adjacency");
        check(!graph.addEdge(10, -2), "duplicate edge");
        check(!graph.addEdge(-2, 10), "reverse duplicate edge");
        check(!graph.addEdge(10, 10), "self-loop");
        check(!graph.addEdge(99, 10), "missing first endpoint");
        check(!graph.addEdge(10, 99), "missing second endpoint");
        check(!graph.addEdge(98, 99), "both endpoints missing");
        check(graph.display().equals(adjacency) && graph.size() == 8, "rejections preserve graph");
        for (int repeat = 0; repeat < 3; repeat++) {
            order(graph.bfs(10), new int[] {10, -2, 30, 0, 50}, "cyclic BFS");
            order(graph.dfs(10), new int[] {10, -2, 0, 50, 30}, "cyclic DFS");
        }
        order(graph.bfs(50), new int[] {50, 30, 0, 10, -2}, "BFS alternative start");
        order(graph.dfs(50), new int[] {50, 30, 10, -2, 0}, "DFS alternative start");
        order(graph.bfs(60), new int[] {60, 70}, "disconnected BFS");
        order(graph.dfs(70), new int[] {70, 60}, "disconnected DFS");
        order(graph.bfs(80), new int[] {80}, "isolated BFS");
        order(graph.dfs(80), new int[] {80}, "isolated DFS");
        int[] returned = graph.bfs(10);
        returned[0] = 999;
        order(graph.bfs(10), new int[] {10, -2, 30, 0, 50}, "BFS result independence");
        returned = graph.dfs(10);
        returned[0] = 999;
        order(graph.dfs(10), new int[] {10, -2, 0, 50, 30}, "DFS result independence");
        check(graph.display().equals(adjacency), "traversal preserves graph");

        GraphDataStructure other = new GraphDataStructure(3);
        check(other.addVertex(Integer.MIN_VALUE), "minimum label");
        check(other.addVertex(Integer.MAX_VALUE), "maximum label");
        check(other.addVertex(0), "zero label");
        check(!other.addVertex(Integer.MIN_VALUE), "extreme duplicate");
        check(other.addEdge(Integer.MAX_VALUE, Integer.MIN_VALUE), "extreme edge");
        check(other.addEdge(0, Integer.MAX_VALUE), "zero edge");
        order(other.bfs(0), new int[] {0, Integer.MAX_VALUE, Integer.MIN_VALUE}, "extreme BFS");
        order(other.dfs(0), new int[] {0, Integer.MAX_VALUE, Integer.MIN_VALUE}, "extreme DFS");
        check(other.search(Integer.MIN_VALUE) == 0 && other.search(Integer.MAX_VALUE) == 1,
                "extreme searches");
        check(other.display().equals("[-2147483648: [2147483647], "
                + "2147483647: [-2147483648, 0], 0: [2147483647]]"), "extreme display");
        check(graph.display().equals(adjacency) && empty.display().equals("[0: []]"),
                "independent instances");

        GraphDataStructure dense = new GraphDataStructure(12);
        for (int i = 0; i < 12; i++) {
            check(dense.addVertex(i), "dense vertex " + i);
        }
        for (int i = 0; i < 12; i++) {
            for (int j = i + 1; j < 12; j++) {
                check(dense.addEdge(i, j), "dense edge " + i + ":" + j);
            }
        }
        int[] ascending = new int[12];
        for (int i = 0; i < 12; i++) {
            ascending[i] = i;
        }
        order(dense.bfs(0), ascending, "dense BFS visits once without queue overflow");
        order(dense.dfs(0), ascending, "dense DFS visits once without stack overflow");
        System.out.println("PASS: " + checks + " checks; 0 failures.");
    }

    private static void order(int[] actual, int[] expected, String description) {
        check(actual.length == expected.length, description + " length");
        for (int i = 0; i < expected.length; i++) {
            check(actual[i] == expected[i], description + " position " + i);
        }
    }

    private static void expectInvalid(int capacity) {
        try {
            new GraphDataStructure(capacity);
        } catch (IllegalArgumentException expected) {
            checks++;
            return;
        }
        throw new AssertionError("Expected invalid capacity: " + capacity);
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError(description);
        }
        checks++;
    }
}
