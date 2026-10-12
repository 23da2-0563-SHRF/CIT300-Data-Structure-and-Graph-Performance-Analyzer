package graph;

/** Manual fixed-capacity, simple undirected graph with integer vertex labels.
 * Vertices and neighbors are visited in vertex insertion order, not edge order.
 * Traversals return only the component reachable from the requested start.
 */
public class GraphDataStructure {
    private final int[] vertices;
    private final boolean[][] edges;
    private int size;

    /** @throws IllegalArgumentException if capacity is not positive */
    public GraphDataStructure(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        vertices = new int[capacity];
        edges = new boolean[capacity][capacity];
    }

    /** Returns false for duplicate labels or a full graph without changing it. */
    public boolean addVertex(int value) {
        if (search(value) != -1 || size == vertices.length) {
            return false;
        }
        vertices[size++] = value;
        return true;
    }

    /** Returns the insertion index of a vertex, or -1 if missing. */
    public int search(int value) {
        for (int i = 0; i < size; i++) {
            if (vertices[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /** Returns false for missing endpoints, duplicate edges, or self-loops.
     * Rejected edges never add vertices or alter adjacency relationships.
     */
    public boolean addEdge(int first, int second) {
        int a = search(first);
        int b = search(second);
        if (a == -1 || b == -1 || a == b || edges[a][b]) {
            return false;
        }
        edges[a][b] = true;
        edges[b][a] = true;
        return true;
    }

    /** Returns adjacency lists in insertion order; an empty graph displays []. */
    public String display() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(vertices[i]).append(": [");
            boolean first = true;
            for (int j = 0; j < size; j++) {
                if (edges[i][j]) {
                    if (!first) {
                        result.append(", ");
                    }
                    result.append(vertices[j]);
                    first = false;
                }
            }
            result.append(']');
        }
        return result.append(']').toString();
    }

    /** Manual array queue; marks on enqueue so each vertex enters at most once.
     * Returns an empty array for a missing start, including an empty graph.
     * With the adjacency matrix, traversal costs O(V squared) time and O(V) space.
     */
    public int[] bfs(int start) {
        int index = search(start);
        if (index == -1) {
            return new int[0];
        }
        boolean[] visited = new boolean[size];
        int[] queue = new int[size];
        int front = 0;
        int rear = 0;
        queue[rear++] = index;
        visited[index] = true;
        while (front < rear) {
            int current = queue[front++];
            for (int next = 0; next < size; next++) {
                if (edges[current][next] && !visited[next]) {
                    visited[next] = true;
                    queue[rear++] = next;
                }
            }
        }
        int[] result = new int[rear];
        for (int i = 0; i < rear; i++) {
            result[i] = vertices[queue[i]];
        }
        return result;
    }

    /** Manual stack of the active path, avoiding recursive call-stack limits.
     * Returns an empty array for a missing start. O(V squared) time, O(V) space.
     */
    public int[] dfs(int start) {
        int index = search(start);
        if (index == -1) {
            return new int[0];
        }
        boolean[] visited = new boolean[size];
        int[] stack = new int[size];
        int[] nextNeighbor = new int[size];
        int[] order = new int[size];
        int depth = 0;
        int count = 0;
        stack[depth++] = index;
        visited[index] = true;
        order[count++] = vertices[index];
        while (depth > 0) {
            int current = stack[depth - 1];
            int next = nextNeighbor[current];
            while (next < size && (!edges[current][next] || visited[next])) {
                next++;
            }
            nextNeighbor[current] = next + 1;
            if (next == size) {
                depth--;
            } else {
                visited[next] = true;
                order[count++] = vertices[next];
                stack[depth++] = next;
            }
        }
        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = order[i];
        }
        return result;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return vertices.length;
    }

    public boolean isEmpty() {
        return size == 0;
    }
}
