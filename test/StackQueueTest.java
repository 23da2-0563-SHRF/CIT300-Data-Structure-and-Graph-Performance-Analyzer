package test;

import stack.StackDataStructure;
import queue.QueueDataStructure;

/** Standalone Member 2 checks; failures throw AssertionError without requiring -ea. */
public class StackQueueTest {
    private static int checks;

    public static void main(String[] args) {
        testStack();
        testQueue();
        System.out.println("PASS: " + checks + " checks; 0 failures.");
    }

    private static void testStack() {
        int initialChecks = checks;
        StackDataStructure stack = new StackDataStructure(4);
        check(stack.size() == 0 && stack.capacity() == 4 && stack.isEmpty() && !stack.isFull(), "stack construction");
        check(stack.display().equals("[]"), "empty stack display");
        expectException(IllegalStateException.class, stack::pop, "Stack is empty.", "empty pop");
        expectException(IllegalStateException.class, stack::peek, "Stack is empty.", "empty stack peek");
        check(stack.size() == 0 && stack.display().equals("[]"), "stack underflow preserves state");
        check(stack.push(5), "push one item");
        check(stack.peek() == 5 && stack.size() == 1 && stack.display().equals("[5]"), "peek one item without removal");
        check(stack.push(-2) && stack.push(0) && stack.push(5), "push negative zero duplicate");
        check(stack.display().equals("[5, -2, 0, 5]") && stack.size() == 4 && stack.isFull(), "stack order and full state");
        check(!stack.push(99), "full push rejected");
        check(stack.display().equals("[5, -2, 0, 5]") && stack.size() == 4 && stack.peek() == 5, "overflow preserves stack");
        check(stack.peek() == 5 && stack.peek() == 5 && stack.size() == 4, "repeated peek does not remove");
        check(stack.pop() == 5 && stack.size() == 3 && !stack.isFull(), "pop top duplicate");
        check(stack.pop() == 0 && stack.pop() == -2 && stack.pop() == 5, "repeated pop LIFO");
        check(stack.isEmpty() && stack.display().equals("[]") && stack.capacity() == 4, "drained stack");
        expectException(IllegalStateException.class, stack::pop, "Stack is empty.", "pop after draining");
        expectException(IllegalStateException.class, stack::peek, "Stack is empty.", "peek after draining");
        check(stack.push(Integer.MIN_VALUE) && stack.push(Integer.MAX_VALUE), "reuse stack and integer extremes");
        check(stack.display().equals("[-2147483648, 2147483647]"), "stack boundary display");
        check(stack.peek() == Integer.MAX_VALUE && stack.pop() == Integer.MAX_VALUE && stack.pop() == Integer.MIN_VALUE, "stack boundary LIFO");
        expectException(IllegalArgumentException.class, () -> new StackDataStructure(0), "Capacity must be positive.", "zero stack capacity");
        expectException(IllegalArgumentException.class, () -> new StackDataStructure(-1), "Capacity must be positive.", "negative stack capacity");
        StackDataStructure single = new StackDataStructure(1);
        check(single.push(0) && single.isFull() && !single.push(1) && single.peek() == 0, "capacity one stack full");
        check(single.pop() == 0 && single.isEmpty() && single.push(-1) && single.pop() == -1, "capacity one stack reuse");
        check(stack.isEmpty() && stack.capacity() == 4, "independent stacks");
        System.out.println("Stack: " + (checks - initialChecks) + " checks passed (display order: bottom to top).");
    }

    private static void testQueue() {
        int initialChecks = checks;
        QueueDataStructure queue = new QueueDataStructure(4);
        check(queue.size() == 0 && queue.capacity() == 4 && queue.isEmpty() && !queue.isFull(), "queue construction");
        check(queue.display().equals("[]"), "empty queue display");
        expectException(IllegalStateException.class, queue::dequeue, "Queue is empty.", "empty dequeue");
        expectException(IllegalStateException.class, queue::peek, "Queue is empty.", "empty queue peek");
        check(queue.size() == 0 && queue.display().equals("[]"), "queue underflow preserves state");
        check(queue.enqueue(5), "enqueue one item");
        check(queue.peek() == 5 && queue.size() == 1 && queue.display().equals("[5]"), "front without removal");
        check(queue.enqueue(-2) && queue.enqueue(0) && queue.enqueue(5), "enqueue negative zero duplicate");
        check(queue.display().equals("[5, -2, 0, 5]") && queue.size() == 4 && queue.isFull(), "queue order and full state");
        check(!queue.enqueue(99), "full enqueue rejected");
        check(queue.display().equals("[5, -2, 0, 5]") && queue.size() == 4 && queue.peek() == 5, "overflow preserves queue");
        check(queue.peek() == 5 && queue.peek() == 5 && queue.size() == 4, "repeated front does not remove");
        check(queue.dequeue() == 5 && queue.size() == 3 && !queue.isFull(), "dequeue first duplicate");
        check(queue.dequeue() == -2, "dequeue negative FIFO");
        check(queue.enqueue(7) && queue.enqueue(8) && queue.isFull(), "reuse vacated slots with wraparound");
        check(queue.display().equals("[0, 5, 7, 8]") && queue.peek() == 0, "wrapped display and front");
        check(!queue.enqueue(9) && queue.display().equals("[0, 5, 7, 8]"), "wrapped overflow preserves data");
        check(queue.dequeue() == 0 && queue.dequeue() == 5 && queue.dequeue() == 7 && queue.dequeue() == 8, "wrapped repeated dequeue FIFO");
        check(queue.isEmpty() && queue.display().equals("[]") && queue.capacity() == 4, "drained queue");
        expectException(IllegalStateException.class, queue::dequeue, "Queue is empty.", "dequeue after draining");
        expectException(IllegalStateException.class, queue::peek, "Queue is empty.", "peek after draining");
        check(queue.enqueue(Integer.MIN_VALUE) && queue.enqueue(Integer.MAX_VALUE), "reuse queue and integer extremes");
        check(queue.display().equals("[-2147483648, 2147483647]"), "queue boundary display");
        check(queue.peek() == Integer.MIN_VALUE && queue.dequeue() == Integer.MIN_VALUE && queue.dequeue() == Integer.MAX_VALUE, "queue boundary FIFO");
        expectException(IllegalArgumentException.class, () -> new QueueDataStructure(0), "Capacity must be positive.", "zero queue capacity");
        expectException(IllegalArgumentException.class, () -> new QueueDataStructure(-1), "Capacity must be positive.", "negative queue capacity");
        QueueDataStructure single = new QueueDataStructure(1);
        check(single.enqueue(0) && single.isFull() && !single.enqueue(1) && single.peek() == 0, "capacity one queue full");
        check(single.dequeue() == 0 && single.isEmpty() && single.enqueue(-1) && single.dequeue() == -1, "capacity one queue reuse");
        check(queue.isEmpty() && queue.capacity() == 4, "independent queues");
        for (int round = 0; round < 5; round++) {
            for (int i = 0; i < 4; i++) {
                check(queue.enqueue(round * 4 + i), "repeated wrap enqueue " + round + ":" + i);
            }
            check(queue.isFull() && !queue.enqueue(100) && queue.peek() == round * 4, "repeated wrap full " + round);
            for (int i = 0; i < 4; i++) {
                check(queue.dequeue() == round * 4 + i, "repeated wrap FIFO " + round + ":" + i);
            }
            check(queue.isEmpty() && queue.display().equals("[]"), "repeated wrap empty " + round);
        }
        System.out.println("Queue: " + (checks - initialChecks) + " checks passed (display order: front to rear).");
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError(description);
        }
        checks++;
    }

    private static void expectException(Class<? extends RuntimeException> type, Runnable operation,
            String message, String description) {
        try {
            operation.run();
        } catch (RuntimeException actual) {
            if (!type.isInstance(actual) || !message.equals(actual.getMessage())) {
                throw new AssertionError("Wrong exception: " + description, actual);
            }
            checks++;
            return;
        }
        throw new AssertionError("Expected " + type.getSimpleName() + ": " + description);
    }
}
