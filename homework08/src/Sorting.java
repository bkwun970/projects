import java.util.*;

/**
 * Your implementation of various sorting algorithms.
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

public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot proceed. Either array or the comparator is null");
        }
        for (int n = 0; n < arr.length - 1; n++) {
            // real index
            int i = n;
            while (i >= 0 && comparator.compare(arr[i], arr[i + 1]) > 0) {
                swap(arr, i, i + 1);
                i--;
            }
        }
    }

    /**
     * Swap helper method for sort methods
     *
     * @param <T> data type to swap
     * @param arr passed in array
     * @param index1 first index
     * @param index2 second index
     */
    private static <T> void swap(T[] arr, int index1, int index2) {
        T tmp = arr[index1];
        arr[index1] = arr[index2];
        arr[index2] = tmp;
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot proceed. Either array or the comparator is null");
        }
        boolean swapsMade = true;
        int startInd = 0;
        int endInd = arr.length - 1;
        int lastSwap = 0;

        while (swapsMade) {
            swapsMade = false;
            for (int i = startInd; i < endInd; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    // strict comparison
                    swap(arr, i, i + 1);
                    swapsMade = true;
                    lastSwap = i;
                }
            }
            endInd = lastSwap;

            if (swapsMade) {
                swapsMade = false;
                for (int i = endInd; i > startInd; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        swap(arr, i - 1, i);
                        swapsMade = true;
                        lastSwap = i;
                    }
                }
                startInd = lastSwap;
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */

    // divide and conquer algorithm
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("Cannot proceed. Either array or the comparator is null");
        }
        if (arr.length <= 1) {
            return;
        }

        int leftLength = arr.length / 2;
        int rightLength = arr.length - leftLength;

        T[] leftArr = (T[]) new Object[leftLength];
        T[] rightArr = (T[]) new Object[rightLength];

        for (int i = 0; i < leftLength; i++) {
            leftArr[i] = arr[i];
        }
        for (int i = leftLength; i < arr.length; i++) {
            rightArr[i - leftLength] = arr[i];
        }
        mergeSort(leftArr, comparator);
        mergeSort(rightArr, comparator);

        int i = 0; // left index
        int j = 0; // right index

        while (i < leftLength && j < rightLength) {
            if (comparator.compare(leftArr[i], rightArr[j]) <= 0) {
                arr[i + j] = leftArr[i];
                i++;
            } else {
                arr[i + j] = rightArr[j];
                j++;
            }
        }
        while (i < leftLength) {
            arr[i + j] = leftArr[i];
            i++;
        }
        while (j < rightLength) {
            arr[i + j] = rightArr[j];
            j++;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) pass through of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    // wicked fast
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Null array cannot be sorted");
        }
        if (arr.length == 0) {
            return;
        }
        List<Integer>[] buckets = new ArrayList[20];

        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }

        int n = arr.length;
        int max = arr[0];
        int divisor = 1;

        // getting k - number of digits in longest number
        for (Integer e : arr) {
            if (Math.abs(e) > max) {
                max = Math.abs(e);
            } else if (Integer.MIN_VALUE == e) {
                max = Integer.MIN_VALUE;
                break;
            }
        }
        int k = 0;
        while (max != 0) {
            max = max / 10;
            k++;
        }

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < n; j++) {
                int digit = (arr[j] / divisor) % 10;
                buckets[digit + 9].add(arr[j]);
            }
            int index = 0;
            for (int q = 0; q < buckets.length; q++) {
                while(!buckets[q].isEmpty()) {
                    arr[index] = buckets[q].remove(0);
                    index++;
                }
            }
            divisor *= 10;
        }
    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator, Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("There is a null parameter passed in. Please check again.");
        }
        quickSort(arr, comparator, rand, 0, arr.length - 1);
    }

    /**
     * helper method for quickSort method
     *
     * @param <T> datatype
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @param start start index of the array
     * @param end the end index of the array
     */
    // divide and conquer algorithm
    // unstable because of long swaps
    private static <T> void quickSort(T[] arr, Comparator<T> comparator, Random rand, int start, int end) {
        if (end - start < 1) {
            return;
        }

        int pivotIdx = rand.nextInt(end - start + 1) + start;
        T pivotVal = arr[pivotIdx];
        swap(arr, start, pivotIdx);

        int left = start + 1;
        int right = end;

        // while left and right haven't crossed
        while (left <= right) {
            // not crossed && arr[left] is smaller than or equal to pivotVal
            while (left <= right && comparator.compare(arr[left], pivotVal) <= 0) {
                left++;
            }
            // not crossed && arr[right] is greater than or equal to pivotVal
            while (left <= right && comparator.compare(arr[right], pivotVal) >= 0) {
                // move right index to find something that is smaller than pivotVal
                right--;
            }
            // not crossed
            if (left <= right) {
                swap(arr, left, right);
                left++;
                right--;
            }
        }
        // part 3
        swap(arr, start, right);
        quickSort(arr, comparator, rand, start, right - 1);
        quickSort(arr, comparator, rand, right + 1, end);
    }
}