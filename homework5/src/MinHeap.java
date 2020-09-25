import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a min heap.
 *
 * @author Jared Raiola
 * @userid jraiola3
 * @GTID 903293358
 * @version 1.0
 */
public class MinHeap<T extends Comparable<? super T>> {

    public static final int INITIAL_CAPACITY = 13;

    private T[] backingArray;
    private int size;
    // Do not add any more instance variables. Do not change the declaration
    // of the instance variables above.

    /**
     * Creates a Heap with an initial capacity of {@code INITIAL_CAPACITY}
     * for the backing array.
     *
     * Use the constant field provided. Do not use magic numbers!
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the Build Heap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     *
     * The data in the backingArray should be in the same order as it appears
     * in the ArrayList before you start the Build Heap Algorithm.
     *
     * The {@code backingArray} should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY from
     * the interface). Index 0 should remain empty, indices 1 to n should
     * contain the data in proper order, and the rest of the indices should
     * be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot put null data"
                    + "collection into the min heap.");
        }
        size = 0;
        backingArray = (T[]) new Comparable[data.size() * 2 + 1];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("Cannot add null data"
                        + "element into the min heap.");
            }
            size++;
            backingArray[i + 1] = data.get(i);
        }
        for (int i = size; i > 1; i--) {
            heapifyUp(i);
        }
    }

    /**
     * Private method used to shift smaller elements up the tree.
     *
     * @param i the index to search backwards from
     */
    private void heapifyUp(int i) {
        int curr = i;
        while (curr != 1 && ((backingArray[curr - 1] != null
                && backingArray[curr / 2].
                compareTo(backingArray[curr - 1]) > 0)
                || (backingArray[curr] != null
                && backingArray[curr / 2].
                compareTo(backingArray[curr]) > 0))) {
            if (backingArray[curr] != null && backingArray[curr - 1] != null) {
                if (backingArray[curr].
                        compareTo(backingArray[curr - 1]) > 0) {
                    swap(curr / 2, curr - 1);
                    curr /= 2;
                } else {
                    swap(curr / 2, curr);
                    curr /= 2;
                }
            } else if (backingArray[curr / 2].
                    compareTo(backingArray[curr - 1]) > 0) {
                swap(curr / 2, curr - 1);
            }
        }
    }

    /**
     * Private method used to swap two elements
     *
     * @param first one of the elements to swap
     * @param second the other element to swap
     */
    private void swap(int first, int second) {
        T temp = backingArray[second];
        backingArray[second] = backingArray[first];
        backingArray[first] = temp;
    }

    /**
     * Adds an item to the heap. If the backing array is full and you're trying
     * to add a new item, then double its capacity.
     *
     * @throws IllegalArgumentException if the item is null
     * @param item the item to be added to the heap
     */
    public void add(T item) {
        if (item == null) {
            throw new IllegalArgumentException("Cannot add a null Item to"
                    + "the heap.");
        }

        if (size == 0) {
            backingArray[1] = item;
            size++;
        } else {
            if (size + 1 > backingArray.length - 1) {
                T[] newBack = (T[]) new Comparable[backingArray.length * 2];
                for (int i = 1; i < backingArray.length; i++) {
                    newBack[i] = backingArray[i];
                }
                backingArray = newBack;
            }
            backingArray[size + 1] = item;
            size++;
            int curr = size;
            while (curr != 1 && backingArray[curr / 2].
                    compareTo(backingArray[curr]) > 0) {
                swap(curr, curr / 2);
                curr /= 2;
            }
        }
    }

    /**
     * Removes and returns the min item of the heap. Null out all elements not
     * existing in the heap after this operation. Do not decrease the capacity
     * of the backing array.
     *
     * @throws java.util.NoSuchElementException if the heap is empty
     * @return the removed item
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty, cannot "
                    + "remove from an empty heap.");
        }
        T dataRemoved = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        for (int i = size; i > 1; i--) {
            heapifyUp(i);
        }
        return dataRemoved;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element, null if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            return null;
        } else {
            return backingArray[1];
        }
    }

    /**
     * Returns if the heap is empty or not.
     *
     * @return true if the heap is empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap and returns array to {@code INITIAL_CAPACITY}.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * THIS METHOD IS ONLY FOR TESTING PURPOSES.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the number of elements in the heap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Return the backing array for the heap.
     *
     * For grading purposes only. DO NOT USE THIS METHOD IN YOUR CODE!
     *
     * @return the backing array for the heap
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

}