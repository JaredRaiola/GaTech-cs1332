/**
 * Your implementation of a non-circular doubly linked list with a tail pointer.
 *
 * @author Jared Raiola
 * @userid jraiola3
 * @GTID 903293358
 * @version 1.0
 */
public class DoublyLinkedList<T> {
    // Do not add new instance variables or modify existing ones.
    private LinkedListNode<T> head;
    private LinkedListNode<T> tail;
    private int size;

    /**
     * Adds the element to the index specified.
     *
     * Adding to indices 0 and {@code size} should be O(1), all other cases are
     * O(n).
     *
     * @param index the requested index for the new element
     * @param data the data for the new element
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index > size
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data");
        } else if (index < 0) {
            throw new IndexOutOfBoundsException("Index is negative");
        } else if (index > size) {
            throw new IndexOutOfBoundsException("Index is greater than the "
                    + "size of the DoublyLinkedList");
        }

        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            int pos = 0;
            LinkedListNode<T> temp = head.getNext();
            while (pos != index) {
                pos++;
                temp = temp.getNext();
            }

            LinkedListNode<T> newNode
                    = new LinkedListNode<>(temp, data, temp.getNext());
            temp.setNext(newNode);
            temp.getNext().setPrevious(newNode);
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data");
        }

        LinkedListNode<T> newNode = new LinkedListNode<>(null, data, head);
        size++;

        if (tail == null) {
            tail = newNode;
        }

        if (head != null) {
            head.setPrevious(newNode);
        }

        head = newNode;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1) for all cases.
     *
     * @param data the data for the new element
     * @throws java.lang.IllegalArgumentException if data is null.
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data");
        }

        LinkedListNode<T> newNode = new LinkedListNode<>(tail, data, null);
        size++;

        if (head == null) {
            head = newNode;
        }

        if (tail != null) {
            tail.setNext(newNode);
        }
        tail = newNode;
    }

    /**
     * Removes and returns the element from the index specified.
     *
     * Removing from index 0 and {@code size - 1} should be O(1), all other
     * cases are O(n).
     *
     * @param index the requested index to be removed
     * @return the data formerly located at index
     * @throws java.lang.IndexOutOfBoundsException if index is negative or
     * index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index is negative");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index is greater than "
                    + "the size of the ArrayList");
        }

        if (isEmpty()) {
            return null;
        }

        if (index == 0) {
            T temp = head.getData();
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return temp;
        }

        if (index == (size - 1)) {
            T temp = tail.getData();
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return temp;
        }

        LinkedListNode<T> ahead;
        LinkedListNode<T> behind;

        int pos = 1;
        LinkedListNode<T> temp = head.getNext();

        while (pos != index) {
            pos++;
            temp = temp.getNext();
        }

        T tempData = temp.getData();
        ahead = temp.getPrevious();
        behind = temp.getNext();

        ahead.setNext(behind);
        behind.setPrevious(ahead);
        size--;

        return tempData;
    }

    /**
     * Removes and returns the element at the front of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the front, null if empty list
     */
    public T removeFromFront() {
        return removeAtIndex(0);
    }

    /**
     * Removes and returns the element at the back of the list. If the list is
     * empty, return {@code null}.
     *
     * Must be O(1) for all cases.
     *
     * @return the data formerly located at the back, null if empty list
     */
    public T removeFromBack() {
        return removeAtIndex(size - 1);
    }

    /**
     * Returns the index of the last occurrence of the passed in data in the
     * list or -1 if it is not in the list.
     *
     * If data is in the tail, should be O(1). In all other cases, O(n).
     *
     * @param data the data to search for
     * @throws java.lang.IllegalArgumentException if data is null
     * @return the index of the last occurrence or -1 if not in the list
     */
    public int lastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot find null data");
        }

        if (isEmpty()) {
            return -1;
        }

        if (tail.getData() == data) {
            return size;
        }

        if (tail.getPrevious() == null) {
            return -1;
        }

        int pos = 1;

        LinkedListNode<T> temp = tail.getPrevious();

        while (temp != null) {
            if (temp.getData().equals(data)) {
                return (size - 1) - pos;
            }
            temp = temp.getPrevious();
            pos++;
        }

        return -1;
    }

    /**
     * Returns the element at the specified index.
     *
     * Getting the head and tail should be O(1), all other cases are O(n).
     *
     * @param index the index of the requested element
     * @return the object stored at index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or
     * index >= size
     */
    public T get(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index is negative");
        } else if (index >= size) {
            throw new IndexOutOfBoundsException("Index is greater than "
                    + "the size of the ArrayList");
        }

        if (index == 0) {
            return head.getData();
        } else if (index == (size - 1)) {
            return tail.getData();
        }

        int pos = 1;
        LinkedListNode<T> temp = head.getNext();

        while (pos != index) {
            pos++;
            temp = temp.getNext();
        }

        return temp.getData();
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length {@code size} holding all of the objects in
     * this list in the same order from head to tail
     */
    public Object[] toArray() {
        Object[] arrayOfDLL;
        arrayOfDLL = new Object[size];

        if (isEmpty()) {
            return arrayOfDLL;
        }

        int pos = 1;
        LinkedListNode<T> temp = head.getNext();
        arrayOfDLL[0] = head.getData();

        while (pos != size) {
            arrayOfDLL[pos] = temp.getData();
            temp = temp.getNext();
            pos++;
        }
        return arrayOfDLL;
    }

    /**
     * Returns a boolean value indicating if the list is empty.
     *
     * Must be O(1) for all cases.
     *
     * @return true if empty; false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the list of all data and resets the size.
     *
     * Must be O(1) for all cases.
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Returns the number of elements in the list.
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
     * Returns the head node of the linked list.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the head of the linked list
     */
    public LinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the linked list.
     * Normally, you would not do this, but it's necessary for testing purposes.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return node at the tail of the linked list
     */
    public LinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}