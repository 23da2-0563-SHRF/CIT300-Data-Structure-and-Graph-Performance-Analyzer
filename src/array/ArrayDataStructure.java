package array;

/** A fixed-capacity integer array. Duplicate values are allowed. */
public class ArrayDataStructure {
    private final int[] elements;
    private int size;

    /** @throws IllegalArgumentException if capacity is not positive */
    public ArrayDataStructure(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be positive.");
        }
        elements = new int[capacity];
    }

    /** Appends a value; returns false without changing data when full. */
    public boolean insert(int value) {
        if (size == elements.length) {
            return false;
        }
        elements[size++] = value;
        return true;
    }

    /** Deletes the first matching value; returns false if absent. */
    public boolean delete(int value) {
        int index = search(value);
        if (index == -1) {
            return false;
        }
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        elements[--size] = 0;
        return true;
    }

    /** Returns the first matching zero-based index, or -1 if absent. */
    public int search(int value) {
        for (int i = 0; i < size; i++) {
            if (elements[i] == value) {
                return i;
            }
        }
        return -1;
    }

    /** Returns a display string containing only the occupied elements. */
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

    public int size() {
        return size;
    }

    public int capacity() {
        return elements.length;
    }

    /** Returns an independent copy of occupied elements for searching. */
    public int[] toArray() {
        int[] copy = new int[size];
        for (int i = 0; i < size; i++) {
            copy[i] = elements[i];
        }
        return copy;
    }
}
