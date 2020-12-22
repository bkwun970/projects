import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.List;
import java.util.HashSet;
/**
 * Your implementation of a LinearProbingHashMap.
 *
 * @author Byoungil Kwun
 * @version 1.0
 * @userid bkwun3
 * @GTID 903392084
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class LinearProbingHashMap<K, V> {

    /**
     * The initial capacity of the LinearProbingHashMap when created with the
     * default constructor.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /**
     * The max load factor of the LinearProbingHashMap
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    // Do not add new instance variables or modify existing ones.
    private LinearProbingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new LinearProbingHashMap.
     * <p>
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * <p>
     * Use constructor chaining.
     */
    public LinearProbingHashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Constructs a new LinearProbingHashMap.
     * <p>
     * The backing array should have an initial capacity of initialCapacity.
     * <p>
     * You may assume initialCapacity will always be positive.
     *
     * @param initialCapacity the initial capacity of the backing array
     */
    public LinearProbingHashMap(int initialCapacity) {
        table = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     * <p>
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     * <p>
     * Before actually adding any data to the HashMap, you should check to
     * see if the array would violate the max load factor if the data was
     * added. For example, let's say the array is of length 5 and the current
     * size is 3 (LF = 0.6). For this example, assume that no elements are
     * removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate. Be
     * careful to consider the differences between integer and double
     * division when calculating load factor.
     * <p>
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     * <p>
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   the key to add
     * @param value the value to add
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws java.lang.IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("input key or value cannot be null. Null data cannot be put in the map");
        }
        // checking to see if the array violates the max load factor.
        if ((double)(size + 1.0) / table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(table.length * 2 + 1);
        }

        int index = Math.abs(key.hashCode() % table.length);
        int firstDelIndex = -1;
        int count = 0;

        while (count != table.length) {
            int idx = (index + count) % table.length;

            if (table[idx] == null) {
                // 1. null
                if (firstDelIndex == -1) {
                    table[idx] = new LinearProbingMapEntry<>(key, value);
                    size++;
                    return null;
                } else {
                    table[firstDelIndex].setKey(key);
                    table[firstDelIndex].setValue(value);
                    table[firstDelIndex].setRemoved(false);
                    size++;
                    return null;
                }
            } else if (table[idx].isRemoved()) {
                // 2. deleted
                if (firstDelIndex == -1) {
                    firstDelIndex = idx;
                }
            } else if (table[idx].getKey().equals(key)) {
                // 3. duplicate
                    V oldValue = table[idx].getValue();
                    table[idx].setKey(key);
                    table[idx].setValue(value);
                    return oldValue;
            }
            count++;
        }
        return null;
    }
    /**
     * Removes the entry with a matching key from map by marking the entry as
     * removed.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot remove null key from the map.");
        }
        int index = Math.abs(key.hashCode() % table.length);
        int count = 0;
        int nonRemovedSeen = 0;
        while (count != table.length && nonRemovedSeen != size) {
            int idx = (index + count) % table.length;

            if (table[idx] != null && table[idx].getKey().equals(key)) {
                if (!table[idx].isRemoved()) {
                    V tmp = table[idx].getValue();
                    table[idx].setRemoved(true);
                    size--;
                    return tmp;
                }
            }
            if (table[idx] != null && !table[idx].isRemoved()) {
                nonRemovedSeen++;
            }
            count++;
        }
        throw new NoSuchElementException("Cannot find the input key from the map");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for in the map
     * @return the value associated with the given key
     * @throws java.lang.IllegalArgumentException if key is null
     * @throws java.util.NoSuchElementException   if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key cannot be searched. Please check again.");
        }
        int index = Math.abs(key.hashCode() % table.length);
        int count = 0;
        int nonRemovedSeen = 0;

        while (count != size && nonRemovedSeen != size) {
            int idx = (count + index) % table.length;

            if (table[idx] != null && table[idx].getKey().equals(key)) {
                if (!table[idx].isRemoved()) {
                    return table[idx].getValue();
                }
            }
            if (table[idx] != null && !table[idx].isRemoved()) {
                nonRemovedSeen++;
            }
            count++;
        }
        throw new NoSuchElementException("Input key is not in the map. Please check again.");
        }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for in the map
     * @return true if the key is contained within the map, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key cannot be searched. Please check again.");
        }
        int count = 0;
        int index = Math.abs(key.hashCode() % table.length);
        int nonRemovedSeen = 0;

        while (count != size && nonRemovedSeen != size) {
            int idx = (index + count) % table.length;

            if (table[idx] != null && table[idx].getKey().equals(key)) {
                if (!table[idx].isRemoved()) {
                    return true;
                }
            }
            if (table[idx] != null && !table[idx].isRemoved()) {
                nonRemovedSeen++;
            }
            count++;
        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     *
     * Use java.util.HashSet.
     *
     * @return the set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();

        if (size == 0) {
            return set;
        } else {
            int count = 0;
            int curIndex = 0;
            while (count != size) {
                if (table[curIndex] != null && !table[curIndex].isRemoved()) {
                    set.add(table[curIndex].getKey());
                    count++;
                }
                curIndex++;
            }
        }
        return set;
    }

    /**
     * Returns a List view of the values contained in this map.
     *
     * Use java.util.ArrayList or java.util.LinkedList.
     *
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        ArrayList<V> ans = new ArrayList<>();

        if (size == 0) {
            return ans;
        } else {
            int count = 0;
            int curIndex = 0;
            while (count != size) {
                if (table[curIndex] != null && !table[curIndex].isRemoved()) {
                    ans.add(table[curIndex].getValue());
                    count++;
                }
                curIndex++;
            }
        }
        return ans;
    }

    /**
     * Resize the backing table to length.
     *
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new array.
     *
     * @param length new length of the backing table
     * @throws java.lang.IllegalArgumentException if length is less than the
     *                                            number of items in the hash
     *                                            map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {throw new IllegalArgumentException();}
        LinearProbingMapEntry<K, V>[] newTable = new LinearProbingMapEntry[length];

        for (int i = 0; i < table.length; i++) {
            if (table[i] != null && !table[i].isRemoved()) {
                int index = Math.abs(table[i].getKey().hashCode() % newTable.length);

                while (newTable[index] != null) {
                    index = (index + 1) % newTable.length;
                }
                newTable[index] = table[i];
            }
        }
        table = newTable;
    }

    /**
     * Clears the map.
     *
     * Resets the table to a new array of the INITIAL_CAPACITY and resets the
     * size.
     *
     * Must be O(1).
     */
    public void clear() {
        table = (LinearProbingMapEntry<K, V>[]) new LinearProbingMapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the table of the map
     */
    public LinearProbingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the map
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
