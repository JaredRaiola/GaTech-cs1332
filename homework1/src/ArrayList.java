
/**
 * Your implementation of an ArrayList.
 *
 * @author Jared Raiola
 * @userid jraiola3
 * @GTID 903293358
 * @version 1.0
 */
public class ArrayList<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * The initial capacity of the array list.
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * Constructs a new ArrayList.
     *
     * You may add statements to this method.
     */
    public ArrayList() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Resizes the backing array, doubling the capacity.
     */
    private void resize() {
        T[] newBack = (T[]) new Object[backingArray.length * 2];

        for (int i = 0; i < backingArray.length; i++) {
            newBack[i] = backingArray[i];
        }

        backingArray = newBack;
    }

    /**
     * Adds the element to the index specified.
     *
     * Remember that this add may require elements to be shifted.
     *
     * Adding to index {@code size} should be amortized O(1),
     * all other adds are O(n).
     *
     * @param index The index where you want the new element.
     * @param data The data to add to the list.
     * @throws java.lang.IndexOutOfBoundsException if index is negative
     * or index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("Index is negative");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("Index is greater than the "
                    + "size of the ArrayList");
        }
        //Basically write a for loop that will shift everything over one.
        if (size + 1 > backingArray.length) {
            resize();
        }

        if (size == 0) {
            backingArray[0] = data;
        } else {
            for (int i = size - 1; i >= index; i--) {
                backingArray[i + 1] = backingArray[i];
            }

            backingArray[index] = data;
        }

        size++;
    }

    /**
     * Add the given data to the front of your array list.
     *
     * Remember that this add may require elements to be shifted.
     * 
     * Must be O(n).
     *
     * @param data The data to add to the list.
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data");
        }

        addAtIndex(0, data);
    }

    /**
     * Add the given data to the back of your array list.
     *
     * Must be amortized O(1).
     *
     * @param data The data to add to the list.
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data");
        }
        addAtIndex(size, data);
    }

    /**
     *Remember that this remove may require elements to be shifted.
     *
     * This method should be O(1) for index {@code size - 1} and O(n) in 
     * all other cases.
     *
     * @param index The index of the element
     * @return The object that was formerly at that index.
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index is negative");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("Index is greater than "
                    + "the size of the ArrayList");
        }
        T data = backingArray[index];

        for (int i = index; i < size - 1; i++) {
            backingArray[i] = backingArray[i + 1];
        }

        backingArray[size - 1] = null;
        size--;
        return data;
    }

    /**
     * Remove the first element in the list and return it.
     *
     * Remember that this remove may require elements to be shifted.
     *
     * Must be O(n).
     *
     * @return The data from the front of the list or null if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            return null;
        } else {
            T data = backingArray[0];
            for (int i = 0; i < size - 1; i++) {
                backingArray[i] = backingArray[i + 1];
            }

            backingArray[size - 1] = null;
            size--;

            return data;
        }
    }

    /**
     * Remove the last element in the list and return it.
     * 
     * Must be O(1).
     *
     * @return The data from the back of the list or null if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            return null;
        }

        T data = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;

        return data;
    }

    /**
     * Returns the element at the given index.
     *
     * Must be O(1).
     *
     * @param index The index of the element
     * @return The data stored at that index.
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index is negative");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("Index is "
                    + "greater than the size of the ArrayList");
        }
        return backingArray[index];
    }

    /**
     * Return a boolean value representing whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {

        return size == 0;
    }

    /**
     * Clear the list. Reset the backing array to a new array of the initial
     * capacity.
     *
     * Must be O(1).
     */
    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Return the size of the list as an integer.
     *
     * For grading purposes only. DO NOT USE THIS METHOD IN YOUR CODE!
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * Return the backing array for this list.
     *
     * For grading purposes only. DO NOT USE THIS METHOD IN YOUR CODE!
     *
     * @return the backing array for this list
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }
}
