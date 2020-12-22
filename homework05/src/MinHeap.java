import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
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
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is invalid. Cannot add elements from null data");
        }
        backingArray = (T[]) new Comparable[(2 * data.size()) + 1];
        for (T e : data) {
            if (e == null) {
                throw new IllegalArgumentException("Input data is invalid. Cannot add elements from null data");
            }
            size++;
            backingArray[size] = e;
        }

        int startIndex = size / 2;
        for (int j = startIndex; j >= 1; j--) {
            heapifyDown(backingArray, size, j);
        }
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Input data is not valid. Cannot be added to the Heap");
        }
        if (size == 0) {
            backingArray[1] = data;
        } else if (size == backingArray.length - 1) {
            T[] tempArr = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                tempArr[i + 1] = backingArray[i + 1];
            }
            backingArray = tempArr;
            backingArray[size + 1] = data;
            heapifyUp(size + 1);
        } else if (size < backingArray.length - 1) {
            backingArray[size + 1] = data;
            heapifyUp(size + 1);
        }
        size++;
    }

    /**
     * helper method to heapify up. This method takes index as a param and
     * heapifies up to make the order property of the min heap correct.
     *
     * @param index the data to add
     */
    private void heapifyUp(int index) {
        int parentIndex = index / 2;
        if (parentIndex != 0) {
            if (backingArray[parentIndex].compareTo(backingArray[index]) > 0) {
                T temp = backingArray[index];
                backingArray[index] = backingArray[parentIndex];
                backingArray[parentIndex] = temp;
                heapifyUp(parentIndex);
            }
        }
    }

    /**
     * helper method to heapfiy down. This method takes index as a param and
     * heapifies down to make the order property of the min heap correct.
     *
     * @param arr backing array of the heap
     * @param size the size of the backing array of the heap
     * @param index the index of the backing array.
     */
    private void heapifyDown(T[] arr, int size, int index) {
        int rootIndex = index;
        int leftChild = index * 2;
        int rightChild = (index * 2) + 1;

        if (leftChild <= size && arr[rootIndex].compareTo(arr[leftChild]) > 0) {
            rootIndex = leftChild;
        }
        if (rightChild <= size && arr[rootIndex].compareTo(arr[rightChild]) > 0) {
            rootIndex = rightChild;
        }
        if (rootIndex != index) {
            T temp = arr[index];
            arr[index] = arr[rootIndex];
            arr[rootIndex] = temp;

            heapifyDown(arr, size, rootIndex);
        }
    }
    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty. Cannot remove any element from the null heap");
        }
        T removed = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        heapifyDown(backingArray, size, 1);

        return removed;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty. Cannot retrieve any element from the null heap");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}