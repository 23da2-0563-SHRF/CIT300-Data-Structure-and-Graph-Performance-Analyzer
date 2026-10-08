package stack;

/** A manually implemented fixed-capacity integer stack. Duplicates are allowed. */
public class StackDataStructure {
    private final int[] elements;
    private int size;

    /** @throws IllegalArgumentException if capacity is not positive */
    public StackDataStructure(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        elements = new int[capacity];
    }

    /** O(1): appends at the top; returns false without changing data when full. */
    public boolean push(int value) {
        if (isFull()) {
            return false;
        }
        elements[size++] = value;
        return true;
    }

    /** O(1): removes the top. @throws IllegalStateException if empty */
    public int pop() {
        requireNotEmpty();
        int value = elements[--size];
        elements[size] = 0;
        return value;
    }

    /** O(1): reads the top without removal. @throws IllegalStateException if empty */
    public int peek() {
        requireNotEmpty();
        return elements[size - 1];
    }

    /** O(n): displays occupied elements from bottom to top (top is on the right). */
    public String display() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(elements[i]);
        }
        return result.append(']').toString();
    }

    /** O(1). */
    public int size() {
        return size;
    }

    /** O(1). */
    public int capacity() {
        return elements.length;
    }

    /** O(1). */
    public boolean isEmpty() {
        return size == 0;
    }

    /** O(1). */
    public boolean isFull() {
        return size == elements.length;
    }

    private void requireNotEmpty() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack is empty.");
        }
    }
}
