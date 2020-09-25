import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of HashMap.
 * 
 * @author Jared Raiola
 * @userid jraiola3
 * @GTID 903293358
 * @version 1.0
 */
public class HashMap<K, V> {

    // DO NOT MODIFY OR ADD NEW GLOBAL/INSTANCE VARIABLES
    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        size = 0;
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the HashMap.
     * If an entry in the HashMap already has this key, replace the entry's
     * value with the new one passed in.
     *
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     *
     * At the start of the method, you should check to see if the array would
     * violate the max load factor after adding the data (regardless of
     * duplicates). For example, let's say the array is of length 5 and the
     * current size is 3 (LF = 0.6). For this example, assume that no elements
     * are removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate.
     *
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     *
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key key to add into the HashMap
     * @param value value to add into the HashMap
     * @throws IllegalArgumentException if key or value is null
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot input null key to "
                    + "HashMap");
        }
        if (value == null) {
            throw new IllegalArgumentException("Cannot input null value to "
                    + "HashMap");
        }
        if ((double) (size + 1) / table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }

        int index = Math.abs(key.hashCode() % table.length);

        //Looking to see if there is a duplicate key
        int count = 0;
        while (table[index] != null && count != table.length) {
            if (table[index].getKey().equals(key)) {
                V tempValue = table[index].getValue();
                table[index].setValue(value);
                if (table[index].isRemoved()) {
                    table[index].setRemoved(false);
                    size++;
                    return null;
                }
                return tempValue;
            }
            if (index != table.length - 1) {
                index++;
            } else {
                index = 0;
            }
            count++;
        }

        //No duplicate, returns null
        index = Math.abs(key.hashCode() % table.length);
        count = 0;
        MapEntry<K, V> newEntry = new MapEntry<>(key, value);
        while (table[index] != null && !table[index].isRemoved()
                && count != table.length) {
            if (index != table.length - 1) {
                index++;
            } else {
                index = 0;
            }
            count++;
        }
        if (count == table.length) {
            table[count + 1] = newEntry;
            return null;
        }
        table[index] = newEntry;
        size++;
        return null;
    }

    /**
     * Removes the entry with a matching key from the HashMap.
     *
     * @param key the key to remove
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key does not exist
     * @return the value previously associated with the key
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot remove a null key "
                    + "from HashMap");
        }

        int index = Math.abs(key.hashCode() % table.length);
        if (table[index].isRemoved()) {
            throw new NoSuchElementException("The key has already been "
                    + "removed");
        }
        int count = 0;
        while (table[index] != null && !table[index].isRemoved()
                && count != table.length) {
            if (key.equals(table[index].getKey())) {
                V removedValue = table[index].getValue();
                table[index].setRemoved(true);
                size--;
                return removedValue;
            }
            if (index != table.length - 1) {
                index++;
            } else {
                index = 0;
            }
            count++;
        }
        throw new NoSuchElementException(key.toString() + " does not "
                + "exist in the HashMap");

    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     * @return the value associated with the given key
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot get a value for "
                    + "a null key");
        }
        int index = Math.abs(key.hashCode() % table.length);
        //Cycles from the index to the next removed spot searching for
        //the key, if it is not there it will throw an exception.
        int count = 0;
        while (table[index] != null && count != table.length) {
            if (key.equals(table[index].getKey())) {
                V value = table[index].getValue();
                if (!table[index].isRemoved()) {
                    return value;
                } else {
                    throw new NoSuchElementException(key.toString()
                            + " does not exist in the HashMap");
                }
            }
            if (index != table.length - 1) {
                index++;
            } else {
                index = 0;
            }
            count++;
        }
        throw new NoSuchElementException(key.toString() + " does not "
                + "exist in the HashMap");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for
     * @throws IllegalArgumentException if key is null
     * @return whether or not the key is in the map
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot search for a null "
                    + "key in the HashMap");
        }
        int index = Math.abs(key.hashCode() % table.length);
        //Cycles from the index to the next removed spot searching for
        //the key, if it is not there it will return false.
        int count = 0;
        while (count != table.length) {
            if (table[index] != null && !table[index].isRemoved()) {
                if (key.equals(table[index].getKey())) {
                    return true;
                }
            }
            if (index != table.length - 1) {
                index++;
            } else {
                index = 0;
            }
            count++;
        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * Use {@code java.util.HashSet}.
     *
     * @return set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                keySet.add(table[i].getKey());
            }
        }
        return keySet;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use {@code java.util.ArrayList} or {@code java.util.LinkedList}.
     *
     * You should iterate over the table in order of increasing index and add 
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> values = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                values.add(table[i].getValue());
            }
        }
        return values;
    }

    /**
     * Resize the backing table to {@code length}.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Remember that you cannot just simply copy the entries over to the new
     * array.
     *
     * Also, since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't need to check for duplicates.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is less than the number of
     * items in the hash map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("Length inputted is less "
                    + "than current size of the HashMap");
        }
        MapEntry<K, V>[] newTable = table;
        table = (MapEntry<K, V>[]) new MapEntry[length];
        for (MapEntry<K, V> curr : newTable) {
            if (curr != null) {
                if (!curr.isRemoved()) {
                    this.resizePut(curr.getKey(), curr.getValue());
                }
            }
        }
    }

    /**
     * Private helper used to ignore exception of calling
     * resize a second time.
     *
     *
     * @param key the key passed in
     * @param value the value passed in
     */
    private void resizePut(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot input null key to "
                    + "HashMap");
        }
        if (value == null) {
            throw new IllegalArgumentException("Cannot input null value to "
                    + "HashMap");
        }

        int count = 0;
        int index = Math.abs(key.hashCode() % table.length);
        count = 0;
        MapEntry<K, V> newEntry = new MapEntry<>(key, value);
        while (table[index] != null && !table[index].isRemoved()
                && count != table.length) {
            if (index != table.length - 1) {
                index++;
            } else {
                index = 0;
            }
            count++;
        }
        if (count == table.length) {
            table[count + 1] = newEntry;
        }
        table[index] = newEntry;
    }

    /**
     * Clears the table and resets it to {@code INITIAL_CAPACITY}.
     */
    public void clear() {
        size = 0;
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
    }
    
    /**
     * Returns the number of elements in the map.
     *
     * DO NOT USE OR MODIFY THIS METHOD!
     *
     * @return number of elements in the HashMap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * DO NOT USE THIS METHOD IN YOUR CODE. IT IS FOR TESTING ONLY.
     *
     * @return the backing array of the data structure, not a copy.
     */
    public MapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

}