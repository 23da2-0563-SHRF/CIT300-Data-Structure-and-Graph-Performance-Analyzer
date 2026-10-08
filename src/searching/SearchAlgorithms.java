package searching;

/** Manual search algorithms for integer arrays. Null inputs are rejected. */
public final class SearchAlgorithms {
    private SearchAlgorithms() {
    }

    /** Returns the first matching index, or -1 for absent/empty data. */
    public static int linearSearch(int[] data, int target) {
        requireData(data);
        for (int i = 0; i < data.length; i++) {
            if (data[i] == target) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Input must be sorted in ascending (nondecreasing) order.
     * Returns the first matching index, or -1 for absent/empty data.
     * Unsorted input throws IllegalArgumentException rather than giving a
     * misleading result. This safety validation costs O(n); the binary search
     * loop itself costs O(log n). Returned indexes refer to the supplied array.
     */
    public static int binarySearch(int[] data, int target) {
        requireData(data);
        for (int i = 1; i < data.length; i++) {
            if (data[i - 1] > data[i]) {
                throw new IllegalArgumentException("Binary search requires ascending sorted data.");
            }
        }
        int low = 0;
        int high = data.length - 1;
        int found = -1;
        while (low <= high) {
            int middle = low + (high - low) / 2;
            if (data[middle] < target) {
                low = middle + 1;
            } else {
                if (data[middle] == target) {
                    found = middle;
                }
                high = middle - 1;
            }
        }
        return found;
    }

    /**
     * Makes an ascending sorted copy using manual insertion sort.
     * Use this copy with binarySearch; the original data is unchanged.
     * Sorting costs O(n squared) in the worst case and is separate from search.
     */
    public static int[] sortedCopy(int[] data) {
        requireData(data);
        int[] sorted = new int[data.length];
        for (int i = 0; i < data.length; i++) {
            sorted[i] = data[i];
        }
        for (int i = 1; i < sorted.length; i++) {
            int value = sorted[i];
            int j = i - 1;
            while (j >= 0 && sorted[j] > value) {
                sorted[j + 1] = sorted[j];
                j--;
            }
            sorted[j + 1] = value;
        }
        return sorted;
    }

    private static void requireData(int[] data) {
        if (data == null) {
            throw new IllegalArgumentException("Data must not be null.");
        }
    }
}
