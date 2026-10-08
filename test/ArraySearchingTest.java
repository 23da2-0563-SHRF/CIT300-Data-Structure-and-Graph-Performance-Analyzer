package test;

import array.ArrayDataStructure;
import searching.SearchAlgorithms;

/** Standalone tests: failures throw AssertionError, even without java -ea. */
public class ArraySearchingTest {
    private static int checks;

    public static void main(String[] args) {
        ArrayDataStructure array = new ArrayDataStructure(4);
        check(array.size() == 0 && array.capacity() == 4, "initial size/capacity");
        check(array.display().equals("[]"), "empty display");
        check(array.search(0) == -1, "empty search excludes unused slots");
        check(!array.delete(0), "empty deletion");
        check(array.insert(9), "first insertion");
        check(array.insert(-2) && array.insert(9) && array.insert(0), "multiple insertions");
        check(array.display().equals("[9, -2, 9, 0]"), "display insertion order");
        check(array.size() == 4, "full size");
        check(!array.insert(12), "full-array rejection");
        check(array.display().equals("[9, -2, 9, 0]"), "full rejection preserves data");
        check(array.search(9) == 0 && array.search(0) == 3, "successful search");
        check(array.search(12) == -1, "missing search");
        check(!array.delete(12) && array.size() == 4, "missing deletion preserves size");
        check(array.delete(9), "successful deletion");
        check(array.display().equals("[-2, 9, 0]") && array.size() == 3,
                "first duplicate removed and elements shifted");
        check(array.insert(7), "reuse capacity after deletion");
        int[] copy = array.toArray();
        copy[0] = 100;
        check(array.search(-2) == 0, "copy cannot mutate internal data");
        check(array.delete(7) && array.delete(-2) && array.delete(9) && array.delete(0),
                "delete last, first and remaining elements");
        check(array.size() == 0 && array.display().equals("[]"), "empty after deletions");
        expectInvalid(() -> new ArrayDataStructure(0), "zero capacity");
        expectInvalid(() -> new ArrayDataStructure(-1), "negative capacity");

        int[] unsorted = {9, -2, 9, 0};
        check(SearchAlgorithms.linearSearch(unsorted, 9) == 0, "linear first duplicate");
        check(SearchAlgorithms.linearSearch(unsorted, 0) == 3, "linear last element");
        check(SearchAlgorithms.linearSearch(unsorted, 8) == -1, "linear missing");
        int[] sorted = SearchAlgorithms.sortedCopy(unsorted);
        check(sorted[0] == -2 && sorted[1] == 0 && sorted[2] == 9 && sorted[3] == 9,
                "manual sorted copy");
        check(unsorted[0] == 9 && unsorted[1] == -2, "sorting preserves original");
        check(SearchAlgorithms.binarySearch(sorted, -2) == 0, "binary first element");
        check(SearchAlgorithms.binarySearch(sorted, 0) == 1, "binary middle element");
        check(SearchAlgorithms.binarySearch(sorted, 9) == 2, "binary first duplicate");
        check(SearchAlgorithms.binarySearch(sorted, -3) == -1
                && SearchAlgorithms.binarySearch(sorted, 5) == -1
                && SearchAlgorithms.binarySearch(sorted, 10) == -1, "binary missing ranges");
        check(SearchAlgorithms.binarySearch(new int[] {1, 2, 3}, 3) == 2, "binary last element");
        check(SearchAlgorithms.binarySearch(new int[] {4}, 4) == 0
                && SearchAlgorithms.binarySearch(new int[] {4}, 5) == -1, "singleton binary");
        check(SearchAlgorithms.binarySearch(new int[] {2, 2, 2}, 2) == 0, "all duplicates");
        int[] empty = {};
        check(SearchAlgorithms.linearSearch(empty, 1) == -1, "empty linear");
        check(SearchAlgorithms.binarySearch(empty, 1) == -1, "empty binary");
        check(SearchAlgorithms.sortedCopy(empty).length == 0, "empty sorted copy");
        expectInvalid(() -> SearchAlgorithms.linearSearch(null, 1), "null linear");
        expectInvalid(() -> SearchAlgorithms.binarySearch(null, 1), "null binary");
        expectInvalid(() -> SearchAlgorithms.sortedCopy(null), "null sorted copy");
        expectInvalid(() -> SearchAlgorithms.binarySearch(unsorted, 9), "unsorted binary");
        int[] extremes = SearchAlgorithms.sortedCopy(new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE, 0});
        check(SearchAlgorithms.binarySearch(extremes, Integer.MIN_VALUE) == 0
                && SearchAlgorithms.binarySearch(extremes, Integer.MAX_VALUE) == 2, "integer boundaries");

        System.out.println("Demo: linear search on [9, -2, 9, 0] finds 9 at index "
                + SearchAlgorithms.linearSearch(unsorted, 9));
        System.out.println("Demo: binary search on sorted copy [-2, 0, 9, 9] finds 9 at index "
                + SearchAlgorithms.binarySearch(sorted, 9));
        System.out.println("Indexes refer to each supplied array; binary search requires sorted input.");
        System.out.println("PASS: " + checks + " checks.");
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError(description);
        }
        checks++;
    }

    private static void expectInvalid(Runnable operation, String description) {
        try {
            operation.run();
        } catch (IllegalArgumentException expected) {
            checks++;
            return;
        }
        throw new AssertionError("Expected IllegalArgumentException: " + description);
    }
}
