package test;

import linkedlist.LinkedListDataStructure;

/** Member 3 regression tests using explicit checks; no -ea flag is required. */
public class LinkedListTest {
    private static int checks;

    public static void main(String[] args) {
        LinkedListDataStructure list = new LinkedListDataStructure();
        check(list.size() == 0 && list.isEmpty(), "empty construction");
        check(list.display().equals("[]"), "empty display");
        check(list.search(0) == -1, "empty search");
        check(!list.delete(0), "empty deletion");
        check(list.isEmpty() && list.size() == 0 && list.display().equals("[]"), "empty deletion preserves state");
        check(list.insert(5), "insert into empty list");
        check(list.size() == 1 && !list.isEmpty() && list.display().equals("[5]"), "singleton state");
        check(list.search(5) == 0, "singleton search");
        check(list.insert(-2) && list.insert(0) && list.insert(5), "multiple insertions");
        check(list.display().equals("[5, -2, 0, 5]") && list.size() == 4, "insertion order and duplicate display");
        check(list.search(5) == 0, "first duplicate index");
        check(list.search(-2) == 1, "negative middle search");
        check(list.search(0) == 2, "zero search");
        check(list.search(99) == -1, "missing search");
        check(!list.delete(99), "missing deletion");
        check(list.size() == 4 && list.display().equals("[5, -2, 0, 5]"), "missing deletion preserves contents");
        check(list.delete(5), "delete head duplicate");
        check(list.display().equals("[-2, 0, 5]") && list.size() == 3 && list.search(5) == 2, "only first duplicate removed");
        check(list.delete(0), "delete middle");
        check(list.display().equals("[-2, 5]") && list.size() == 2 && list.search(5) == 1, "middle references reconnect");
        check(list.delete(5), "delete tail");
        check(list.display().equals("[-2]") && list.size() == 1, "tail deletion state");
        check(list.insert(7) && list.display().equals("[-2, 7]"), "append after tail deletion");
        check(list.search(7) == 1, "last element search");
        check(list.delete(-2) && list.display().equals("[7]"), "head deletion retains tail");
        check(list.delete(7), "delete only node");
        check(list.isEmpty() && list.size() == 0 && list.display().equals("[]"), "singleton deletion empties list");
        check(!list.delete(7) && list.search(7) == -1, "missing operations after draining");
        check(list.insert(0) && list.display().equals("[0]") && list.search(0) == 0, "reuse after draining");
        check(list.delete(0) && list.isEmpty(), "delete zero singleton");
        check(list.insert(Integer.MIN_VALUE) && list.insert(Integer.MAX_VALUE), "insert integer extremes");
        check(list.display().equals("[-2147483648, 2147483647]"), "boundary display");
        check(list.search(Integer.MIN_VALUE) == 0 && list.search(Integer.MAX_VALUE) == 1, "boundary searches");
        LinkedListDataStructure other = new LinkedListDataStructure();
        check(other.isEmpty() && other.insert(42), "independent construction");
        check(list.size() == 2 && list.display().equals("[-2147483648, 2147483647]") && other.display().equals("[42]"), "independent contents");
        check(list.delete(Integer.MIN_VALUE) && list.search(Integer.MAX_VALUE) == 0, "delete minimum head");
        check(list.delete(Integer.MAX_VALUE) && list.isEmpty(), "delete maximum singleton");
        check(other.search(42) == 0 && other.size() == 1, "other list survives deletions");
        check(other.insert(42) && other.insert(42), "all duplicate insertions");
        check(other.delete(42) && other.display().equals("[42, 42]") && other.size() == 2, "all duplicates delete first only");
        check(other.delete(42) && other.delete(42) && other.isEmpty(), "drain all duplicates");
        check(other.insert(-1) && other.insert(0) && other.display().equals("[-1, 0]"), "duplicate drain resets tail");
        for (int round = 0; round < 3; round++) {
            for (int i = 0; i < 20; i++) {
                check(list.insert(i), "repeated insertion " + round + ":" + i);
            }
            check(list.size() == 20 && list.search(19) == 19, "repeated growth size and last index");
            for (int i = 19; i >= 0; i--) {
                check(list.delete(i) && list.size() == i, "repeated tail deletion " + round + ":" + i);
            }
            check(list.isEmpty() && list.display().equals("[]"), "repeated drain");
        }
        System.out.println("PASS: " + checks + " checks; 0 failures.");
    }

    private static void check(boolean condition, String description) {
        if (!condition) {
            throw new AssertionError(description);
        }
        checks++;
    }
}
