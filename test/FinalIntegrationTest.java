import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import performance.PerformanceAnalyzer;

/** Permanent console smoke and educational-output checks; no -ea required. */
public class FinalIntegrationTest {
    private static int checks;

    public static void main(String[] args) throws Exception {
        String report = PerformanceAnalyzer.demonstrate();
        contains(report, "n=16, target=15: linear index=15", "small search");
        contains(report, "n=256, target=255: linear index=255", "medium search");
        contains(report, "n=4096, target=4095: linear index=4095", "large search");
        contains(report, "binary index=4095", "binary result");
        contains(report, "linear inspected elements=4096", "deterministic count");
        contains(report, "complete binarySearch call is O(n)", "validation cost");
        contains(report, "O(n^2)", "sort cost");
        contains(report, "tiny differences do not prove complexity", "measurement qualification");
        contains(report, "Array delete 10: [20, 30, 40]", "array sample");
        contains(report, "Linked list delete 10: [20, 30, 40]", "list sample");
        contains(report, "Stack top=40, pop=40", "LIFO sample");
        contains(report, "Queue front=10, dequeue=10", "FIFO sample");
        contains(report, "BFS from 1: [1, 2, 3, 4, 5]; visited=5; ns=", "BFS sample");
        contains(report, "DFS from 1: [1, 2, 4, 3, 5]; visited=5; ns=", "DFS sample");
        contains(report, "O(V^2) worst-case traversal time, O(V) auxiliary space", "matrix cost");
        check(PerformanceAnalyzer.format(new int[0]).equals("[]"), "empty formatting");
        check(PerformanceAnalyzer.format(new int[] {-1, 0, 2}).equals("[-1, 0, 2]"), "value formatting");
        // Exercise every required operation through the real Main entry point.
        String output = console("bad\n2147483648\n99\n"
                + "1\n8\n1\n9\n1\n-2\n1\n9\n3\n9\n4\n2\n9\n4\n0\n"
                + "5\n1\n9\n2\n9\n0\n"
                + "2\n2\n3\n1\n5\n3\n4\n2\n0\n"
                + "3\n2\n3\n1\n6\n3\n4\n2\n0\n"
                + "4\n1\n7\n3\n7\n4\n2\n7\n4\n0\n"
                + "6\n1\n10\n1\n20\n1\n10\n2\n10\n99\n2\n10\n20\n3\n4\n20\n5\n10\n6\n10\n5\n99\n0\n"
                + "7\n8\n9\n");
        contains(output, "Enter a valid 32-bit integer.", "invalid numeric input");
        contains(output, "Invalid menu choice.", "invalid menu input");
        contains(output, "[-2, 9]", "array delete");
        contains(output, "Original array: [-2, 9]", "linear input");
        contains(output, "Sorted copy: [-2, 9]", "binary input");
        contains(output, "Index refers to the sorted copy", "index semantics");
        contains(output, "Stack is empty.", "stack underflow");
        contains(output, "Top: 5", "stack peek");
        contains(output, "Popped: 5", "stack pop");
        contains(output, "Queue is empty.", "queue underflow");
        contains(output, "Front: 6", "queue peek");
        contains(output, "Dequeued: 6", "queue dequeue");
        contains(output, "[7]", "list display");
        contains(output, "Duplicate vertex or graph is full.", "duplicate graph vertex");
        contains(output, "Rejected: missing endpoint", "invalid edge");
        contains(output, "[10: [20], 20: [10]]", "graph display");
        contains(output, "Reachable traversal: [10, 20]", "graph traversal");
        contains(output, "Start vertex not found.", "missing traversal start");
        contains(output, "Array: [-2, 9]", "demo preserves console array");
        contains(output, "Linked list: []", "list deletion");
        contains(output, "Goodbye.", "normal exit");
        contains(console(""), "End of input. Goodbye.", "EOF at menu");
        contains(console("1\n1\n"), "End of input. Goodbye.", "EOF at value prompt");
        System.out.println("PASS: " + checks + " checks; 0 failures.");
    }

    private static String console(String script) throws Exception {
        InputStream savedInput = System.in;
        PrintStream savedOutput = System.out;
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        try (PrintStream capture = new PrintStream(bytes, true, "UTF-8")) {
            System.setIn(new ByteArrayInputStream(script.getBytes(StandardCharsets.UTF_8)));
            System.setOut(capture);
            Main.main(new String[0]);
        } finally {
            System.setIn(savedInput);
            System.setOut(savedOutput);
        }
        return new String(bytes.toByteArray(), StandardCharsets.UTF_8);
    }

    private static void contains(String text, String expected, String description) {
        check(text.contains(expected), description);
    }

    private static void check(boolean condition, String description) {
        if (!condition) { throw new AssertionError(description); }
        checks++;
    }
}
