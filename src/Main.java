/**
 * Integrated console using the existing component public APIs.
 */
import java.util.Scanner;
import array.ArrayDataStructure;
import searching.SearchAlgorithms;
import stack.StackDataStructure;
import queue.QueueDataStructure;
import linkedlist.LinkedListDataStructure;
import graph.GraphDataStructure;
import performance.PerformanceAnalyzer;

public class Main {
    private final Scanner input = new Scanner(System.in);
    private final ArrayDataStructure array = new ArrayDataStructure(100);
    private final StackDataStructure stack = new StackDataStructure(100);
    private final QueueDataStructure queue = new QueueDataStructure(100);
    private final LinkedListDataStructure list = new LinkedListDataStructure();
    private final GraphDataStructure graph = new GraphDataStructure(100);

    public static void main(String[] args) { new Main().run(); }

    private void run() {
        System.out.println("CIT300 Analyzer. Fixed capacities: 100. Indexes are zero-based.");
        try {
            while (true) {
                System.out.println("\n1. Array Operations\n2. Stack Operations\n3. Queue Operations\n4. Linked List Operations\n5. Searching\n6. Graph Operations\n7. Performance Comparison\n8. Display All Results / Demonstration\n9. Exit");
                int choice = readInt("Choice: ");
                if (choice == 9) { System.out.println("Goodbye."); return; }
                if (choice >= 1 && choice <= 6) { submenu(choice); }
                else if (choice == 7) { System.out.print(PerformanceAnalyzer.demonstrate()); }
                else if (choice == 8) {
                    System.out.println("Array: " + array.display());
                    System.out.println("Stack (bottom to top): " + stack.display());
                    System.out.println("Queue (front to rear): " + queue.display());
                    System.out.println("Linked list: " + list.display());
                    System.out.println("Graph: " + graph.display());
                    System.out.print(PerformanceAnalyzer.demonstrate());
                } else { System.out.println("Invalid menu choice."); }
            }
        } catch (EndOfInput end) { System.out.println("\nEnd of input. Goodbye."); }
    }

    private void submenu(int component) {
        while (true) {
            if (component == 1 || component == 4) {
                System.out.println("\n1. Insert\n2. Delete first match\n3. Search\n4. Display\n0. Back");
            } else if (component == 2) {
                System.out.println("\n1. Push\n2. Pop\n3. Peek\n4. Display (bottom to top)\n0. Back");
            } else if (component == 3) {
                System.out.println("\n1. Enqueue\n2. Dequeue\n3. Peek/front\n4. Display (front to rear)\n0. Back");
            } else if (component == 5) {
                System.out.println("\nSearch current Array data; insert values in Array Operations.\n1. Linear search\n2. Binary search on sorted copy\n0. Back");
            } else {
                System.out.println("\n1. Add vertex\n2. Add edge\n3. Display graph\n4. Search vertex\n5. BFS\n6. DFS\n0. Back");
            }
            int operation = readInt("Operation: ");
            if (operation == 0) { return; }
            int maximum = component == 5 ? 2 : component == 6 ? 6 : 4;
            if (operation < 1 || operation > maximum) { System.out.println("Invalid menu choice."); continue; }
            try { operate(component, operation); }
            catch (IllegalArgumentException | IllegalStateException error) { System.out.println(error.getMessage()); }
        }
    }

    private void operate(int component, int operation) {
        if (component == 1 || component == 4) {
            boolean isArray = component == 1;
            if (operation == 4) { System.out.println(isArray ? array.display() : list.display()); return; }
            int value = readInt("Value: ");
            if (operation == 1) {
                System.out.println((isArray ? array.insert(value) : list.insert(value)) ? "Inserted." : "Array is full.");
            } else if (operation == 2) {
                System.out.println((isArray ? array.delete(value) : list.delete(value)) ? "Deleted first match." : "Value not found.");
            } else { showIndex(isArray ? array.search(value) : list.search(value)); }
        } else if (component == 2) {
            switch (operation) {
                case 1: System.out.println(stack.push(readInt("Value: ")) ? "Pushed." : "Stack is full."); break;
                case 2: System.out.println("Popped: " + stack.pop()); break;
                case 3: System.out.println("Top: " + stack.peek()); break;
                case 4: System.out.println(stack.display()); break;
                default: throw new IllegalArgumentException("Invalid operation.");
            }
        } else if (component == 3) {
            switch (operation) {
                case 1: System.out.println(queue.enqueue(readInt("Value: ")) ? "Enqueued." : "Queue is full."); break;
                case 2: System.out.println("Dequeued: " + queue.dequeue()); break;
                case 3: System.out.println("Front: " + queue.peek()); break;
                case 4: System.out.println(queue.display()); break;
                default: throw new IllegalArgumentException("Invalid operation.");
            }
        } else if (component == 5) {
            int target = readInt("Target: ");
            int[] data = array.toArray();
            if (operation == 1) {
                System.out.println("Original array: " + PerformanceAnalyzer.format(data));
                showIndex(SearchAlgorithms.linearSearch(data, target));
            } else {
                int[] sorted = SearchAlgorithms.sortedCopy(data);
                System.out.println("Sorted copy: " + PerformanceAnalyzer.format(sorted));
                System.out.println("Index refers to the sorted copy; original array is unchanged.");
                showIndex(SearchAlgorithms.binarySearch(sorted, target));
            }
        } else {
            switch (operation) {
                case 1: System.out.println(graph.addVertex(readInt("Vertex label: ")) ? "Vertex added." : "Duplicate vertex or graph is full."); break;
                case 2:
                    int first = readInt("First vertex: ");
                    int second = readInt("Second vertex: ");
                    System.out.println(graph.addEdge(first, second) ? "Undirected edge added." : "Rejected: missing endpoint, duplicate edge or self-loop."); break;
                case 3: System.out.println(graph.display()); break;
                case 4: showIndex(graph.search(readInt("Vertex label: "))); break;
                case 5:
                case 6:
                    int start = readInt("Start vertex: ");
                    int[] order = operation == 5 ? graph.bfs(start) : graph.dfs(start);
                    System.out.println(order.length == 0 ? "Start vertex not found." : "Reachable traversal: " + PerformanceAnalyzer.format(order)); break;
                default: throw new IllegalArgumentException("Invalid operation.");
            }
        }
    }

    private static void showIndex(int index) {
        System.out.println(index == -1 ? "Value not found." : "Found at index: " + index);
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (!input.hasNextLine()) { throw new EndOfInput(); }
            try { return Integer.parseInt(input.nextLine().trim()); }
            catch (NumberFormatException invalid) { System.out.println("Enter a valid 32-bit integer."); }
        }
    }

    private static final class EndOfInput extends RuntimeException {
        private static final long serialVersionUID = 1L;
    }
}
