package queue;

/** A manually implemented fixed-capacity circular integer queue. Duplicates are allowed. */
public class QueueDataStructure {
    private final int[] elements;
    private int front;
    private int rear;
    private int size;

    /** @throws IllegalArgumentException if capacity is not positive */
    public QueueDataStructure(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        elements = new int[capacity];
    }

    /** O(1): adds at the rear; returns false without changing data when full. */
    public boolean enqueue(int value) {
        if (isFull()) {
            return false;
        }
        elements[rear] = value;
        rear = nextIndex(rear);
        size++;
        return true;
    }

    /** O(1): removes the front without shifting. @throws IllegalStateException if empty */
    public int dequeue() {
        requireNotEmpty();
        int value = elements[front];
        elements[front] = 0;
        front = nextIndex(front);
        size--;
        return value;
    }

    /** O(1): reads the front without removal. @throws IllegalStateException if empty */
    public int peek() {
        requireNotEmpty();
        return elements[front];
    }

    /** O(n): displays occupied elements from front to rear, including wrapped data. */
    public String display() {
        StringBuilder result = new StringBuilder("[");
        int index = front;
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                result.append(", ");
            }
            result.append(elements[index]);
            index = nextIndex(index);
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

    private int nextIndex(int index) {
        return index == elements.length - 1 ? 0 : index + 1;
    }

    private void requireNotEmpty() {
        if (isEmpty()) {
            throw new IllegalStateException("Queue is empty.");
        }
    }
}
