import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Jared Raiola
 * @userid jraiola3
 * @GTID 903293358
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement cocktail sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array passed in is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator passed "
                    + "in is null");
        }
        int len = arr.length;
        int swapInd = 0;
        int i = 0;
        boolean swap = true;
        while (i < len - 1 && swap) {
            swap = false;
            for (int j = swapInd; j < len - i - 1; j++) {
                if (comparator.compare(arr[j], arr[j + 1]) > 0) {
                    T pH = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = pH;
                    swap = true;
                    swapInd = j;

                }
            }
            if (swap) {
                swap = false;
                for (int j = swapInd; j > 0; j--) {
                    if (comparator.compare(arr[j], arr[j - 1]) < 0) {
                        T pH = arr[j];
                        arr[j] = arr[j - 1];
                        arr[j - 1] = pH;
                        swap = true;
                        swapInd = j + 1;

                    }
                }
                i++;
            }
        }
    }

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array passed in is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator passed "
                    + "in is null");
        }
        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0 && comparator.compare(arr[j - 1], arr[j]) > 0) {
                T placeHolder = arr[j - 1];
                arr[j - 1] = arr[j];
                arr[j] = placeHolder;
                j -= 1;
            }
        }
    }

    /**
     * Implement selection sort.
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n^2)
     *
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Note that there may be duplicates in the array.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void selectionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array passed in is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator passed "
                    + "in is null");
        }
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (comparator.compare(arr[j], arr[min]) < 0) {
                    min = j;
                }
            }
            T placeHolder = arr[i];
            arr[i] = arr[min];
            arr[min] = placeHolder;
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null) {
            throw new IllegalArgumentException("Array passed in is null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator passed "
                    + "in is null");
        }
        mergeHelp(arr, comparator);
    }

    /**
     * Helper for merge sort.
     *
     * @param <T> the data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */
    private static <T> void mergeHelp(T[] arr, Comparator<T> comparator) {
        if (arr.length > 1) {
            int middle = arr.length / 2;
            T[] left = (T[]) new Object[middle];
            T[] right = (T[]) new Object[arr.length - middle];
            for (int i = 0; i < middle; i++) {
                left[i] = arr[i];
            }
            for (int i = middle; i < arr.length; i++) {
                right[i - middle] = arr[i];
            }
            mergeHelp(left, comparator);
            mergeHelp(right, comparator);
            int length = arr.length;
            int mid = arr.length / 2;
            int leftInd = 0;
            int rightInd = 0;
            int curr = 0;
            while (leftInd < mid && rightInd < length - mid) {
                if (comparator.compare(left[leftInd], right[rightInd]) <= 0) {
                    arr[curr] = left[leftInd];
                    leftInd++;
                } else {
                    arr[curr] = right[rightInd];
                    rightInd++;
                }
                curr++;
            }
            while (leftInd < mid) {
                arr[curr] = left[leftInd];
                leftInd++;
                curr++;
            }
            while (rightInd < length - mid) {
                arr[curr] = right[rightInd];
                rightInd++;
                curr++;
            }
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
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * Do NOT use anything from the Math class except Math.abs
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("Null array passed in.");
        }
        LinkedList<Integer>[] count;
        count = (LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            count[i] = new LinkedList<>();
        }
        int mod = 10;
        long div = 1;
        boolean check = true;
        while (check) {
            check = false;
            for (int i = 0; i < arr.length; i++) {
                long beforeMod = arr[i] / div;
                if (beforeMod != 0) {
                    check = true;
                }
                long bucket = beforeMod % mod + 9;
                count[(int) bucket].add(arr[i]);
            }
            int index = 0;
            for (int i = 0; i < count.length; i++) {
                while (!count[i].isEmpty()) {
                    arr[index] = count[i].remove();
                    index++;
                }
            }
            div *= 10;
        }
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * You may assume that the array doesn't contain any null elements.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null or k is not in the range of 1 to arr.length
     * @param <T> data type to sort
     * @param k the index to retrieve data from + 1 (due to 0-indexing) if
     *          the array was sorted; the 'k' in "kth select"; e.g. if k ==
     *          1, return the smallest element in the array
     * @param arr the array that should be modified after the method
     * is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @return the kth smallest element
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                  Random rand) {
        if (k > arr.length) {
            throw new IllegalArgumentException("k is larger than the size "
                    + "of the array");
        }
        if (k < 1) {
            throw new IllegalArgumentException("k finds a negative index");
        }
        if (arr == null) {
            throw new IllegalArgumentException("The array passed in is"
                    + " null");
        }
        if (comparator == null) {
            throw new IllegalArgumentException("The comparator passed "
                    + " in is null");
        }
        return kthHelp(k, arr, comparator, rand, 0, arr.length - 1);
    }

    /**
     * Helper for kth select.
     *
     * @param <T> the data type to sort
     * @param k the index to receive data from + 1
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the random number seed
     * @param start parameter to start from in array
     * @param end parameter to end at in array
     * @return the kth smallest element
     */
    private static <T> T kthHelp(int k, T[] arr, Comparator<T> comparator,
                                 Random rand, int start, int end) {
        int pivotInd = start + rand.nextInt(end - start + 1);
        T placeHolder = arr[start];
        arr[start] = arr[pivotInd];
        arr[pivotInd] = placeHolder;
        T pivot = arr[start];
        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], pivot) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivot) >= 0) {
                j--;
            }
            if (i <= j) {
                T pHolder = arr[i];
                arr[i] = arr[j];
                arr[j] = pHolder;
                i++;
                j--;
            }
        }
        T newPlaceHolder = arr[start];
        arr[start] = arr[j];
        arr[j] = newPlaceHolder;
        if (j == (k - 1)) {
            return arr[j];
        } else if (j > (k - 1)) {
            return kthHelp(k, arr, comparator, rand, start, j - 1);
        } else if (j < (k - 1)) {
            return kthHelp(k, arr, comparator, rand, j + 1, end);
        } else {
            return null;
        }
    }
}
