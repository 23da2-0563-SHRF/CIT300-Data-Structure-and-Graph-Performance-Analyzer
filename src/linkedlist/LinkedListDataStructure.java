package linkedlist;

/** A manual singly linked integer list. Insert appends; duplicates are allowed. */
public class LinkedListDataStructure {
    private static final class Node {
        private final int value;
        private Node next;

        private Node(int value) {
            this.value = value;
        }
    }

    private Node head;
    private Node tail;
    private int size;

    /** Creates an empty list without a fixed capacity. */
    public LinkedListDataStructure() {
    }

    /** O(1): appends a value in insertion order, returning true on success. */
    public boolean insert(int value) {
        Node node = new Node(value);
        if (head == null) {
            head = node;
        } else {
            tail.next = node;
        }
        tail = node;
        size++;
        return true;
    }

    /** O(n): removes only the first match; returns false for missing/empty data. */
    public boolean delete(int value) {
        Node previous = null;
        Node current = head;
        while (current != null) {
            if (current.value == value) {
                if (previous == null) {
                    head = current.next;
                } else {
                    previous.next = current.next;
                }
                if (current == tail) {
                    tail = previous;
                }
                size--;
                return true;
            }
            previous = current;
            current = current.next;
        }
        return false;
    }

    /** O(n): returns the first matching zero-based index, or -1 if absent. */
    public int search(int value) {
        int index = 0;
        Node current = head;
        while (current != null) {
            if (current.value == value) {
                return index;
            }
            current = current.next;
            index++;
        }
        return -1;
    }

    /** O(n): returns all values in insertion order; an empty list displays []. */
    public String display() {
        StringBuilder result = new StringBuilder("[");
        Node current = head;
        while (current != null) {
            if (current != head) {
                result.append(", ");
            }
            result.append(current.value);
            current = current.next;
        }
        return result.append(']').toString();
    }

    /** O(1). */
    public int size() {
        return size;
    }

    /** O(1). */
    public boolean isEmpty() {
        return head == null;
    }
}
