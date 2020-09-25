import java.util.NoSuchElementException;

/**
 * Your implementation of an array deque.
 *
 * @author Jared Raiola
 * @userid jraiola3
 * @GTID 903293358
 * @version 1.0
 */
public class ArrayDeque<T> {

    /**
     * The initial capacity of the ArrayDeque.
     */
    public static final int INITIAL_CAPACITY = 11;

    // Do not add new instance variables.
    private T[] backingArray;
    private int front;
    private int back;
    private int size;

    /**
     * Constructs a new ArrayDeque with an initial capacity of
     * the {@code INITIAL_CAPACITY} constant above.
     */
    public ArrayDeque() {
        this.front = 0;
        this.back = 0;
        this.size = 0;
        this.backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Adds the data to the front of the deque.
     *
     * If sufficient space is not available in the backing array, you should
     * regrow it to double the current capacity. If a regrow is necessary,
     * you should copy elements to the beginning of the new array and reset
     * front to the beginning of the array (and move {@code back}
     * appropriately). After the regrow, the new data should be at index 0 of
     * the array.
     *
     * This method must run in amortized O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        int length = backingArray.length;
        if (data == null) {
            throw new IllegalArgumentException("The data is null, null data"
                    + "cannot be added to the deque");
        } else if (size == 0) {
            size++;
            front = length - 1;
            backingArray[length - 1] = data;
        } else if (size == length) {
            T[] newBack = (T[]) new Object[2 * length];
            for (int iter = 0; iter < length; iter++) {
                newBack[iter + 1] = backingArray[mod(front + iter,
                        size)];
            }
            backingArray = newBack;
            backingArray[0] = data;
            size++;
            back = size;
            front = 0;
        } else {
            backingArray[mod(front - 1,
                    length)] = data;
            size++;
            front = mod(front - 1, length);
        }
    }

    /**
     * Adds the data to the back of the deque.
     *
     * If sufficient space is not available in the backing array, you should
     * regrow it to double the current capacity. If a regrow is necessary,
     * you should copy elements to the front of the new array and reset
     * front to the beginning of the array (and move {@code back}
     * appropriately).
     *
     * This method must run in amortized O(1) time.
     *
     * @param data the data to add to the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        int length = backingArray.length;
        if (data == null) {
            throw new IllegalArgumentException("The data is null, null data"
                    + "cannot be added to the deque");
        } else if (size == 0) {
            size++;
            back++;
            backingArray[0] = data;
        } else if (size == length) {
            T[] newBack = (T[]) new Object[2 * length];
            for (int iter = 0; iter < length; iter++) {
                newBack[iter] = backingArray[mod(front + iter,
                        size)];
            }
            backingArray = newBack;
            backingArray[length] = data;
            size++;
            back = size;
            front = 0;
        } else {
            backingArray[back] = data;
            size++;
            back = mod(1 + back, length);
        }
    }

    /**
     * Removes the data at the front of the deque.
     *
     * Do not shrink the backing array.
     *
     * If the deque becomes empty as a result of this call, you should
     * explicitly reset front and back to the beginning of the array.
     *
     * You should replace any spots that you remove from with null. Failure to
     * do so will result in a major loss of points.
     *
     * This method must run in O(1) time.
     *
     * @return the data formerly at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        int length = backingArray.length;
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty, there "
                    + "is nothing to remove");
        } else if (size - 1 == 0) {
            T dataRemoved = backingArray[front];
            backingArray[front] = null;
            back = 0;
            size--;
            front = 0;
            return dataRemoved;
        } else {
            T dataRemoved = backingArray[front];
            backingArray[front] = null;
            size--;
            front = mod(1 + front, length);
            return dataRemoved;
        }
    }

    /**
     * Removes the data at the back of the deque.
     *
     * Do not shrink the backing array.
     *
     * If the deque becomes empty as a result of this call, you should
     * explicitly reset front and back to the beginning of the array.
     *
     * You should replace any spots that you remove from with null. Failure to
     * do so will result in a major loss of points.
     *
     * This method must run in O(1) time.
     *
     * @return the data formerly at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        int length = backingArray.length;
        if (size == 0) {
            throw new NoSuchElementException("The deque is empty, there "
                    + "is nothing to remove");
        } else if (size - 1 == 0) {
            T dataRemoved = backingArray[front];
            backingArray[front] = null;
            back = 0;
            size--;
            front = 0;
            return dataRemoved;
        } else {
            T dataRemoved = backingArray[mod(back - 1,
                    length)];
            backingArray[mod(back - 1, length)] = null;
            size--;
            back = mod(back - 1, length);
            return dataRemoved;
        }
    }

    /**
     * Returns the smallest non-negative remainder when dividing {@code index}
     * by {@code modulo}. So, for example, if modulo is 5, then this method will
     * return either 0, 1, 2, 3, or 4, depending on what the remainder is.
     *
     * This differs from using the % operator in that the % operator returns
     * the smallest answer with the same sign as the dividend. So, for example,
     * (-5) % 6 => -5, but with this method, mod(-5, 6) = 1.
     *
     * Examples:
     * mod(-3, 5) => 2
     * mod(11, 6) => 5
     * mod(-7, 7) => 0
     *
     * DO NOT MODIFY THIS METHOD. This helper method is here to make the math
     * part of the circular behavior easier to work with.
     *
     * @param index the number to take the remainder of
     * @param modulo the divisor to divide by
     * @return the remainder in its smallest non-negative form
     * @throws java.lang.IllegalArgumentException if the modulo is non-positive
     */
    private static int mod(int index, int modulo) {
        // DO NOT MODIFY!
        if (modulo <= 0) {
            throw new IllegalArgumentException("The modulo must be positive.");
        } else {
            int newIndex = index % modulo;
            return newIndex >= 0 ? newIndex : newIndex + modulo;
        }
    }

    /**
     * Returns the number of elements in the deque.
     *
     * Runs in O(1) for all cases.
     * 
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }

    /**
     * Returns the backing array of this deque.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the backing array
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }
}