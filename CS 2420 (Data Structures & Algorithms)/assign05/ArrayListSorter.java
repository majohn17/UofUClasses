package assign05;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Provides sorting methods to sort a generic array list in different ways
 * 
 * @author Jacob Montes and Matthew Johnsen
 * @version February 11, 2019
 */
public class ArrayListSorter {
	private static int threshold;

	/**
	 * Sorts the list using a merge sort.
	 * 
	 * @param items - the list to be sorted
	 */
	public static <T extends Comparable<? super T>> void mergesort(ArrayList<T> items) {
		setThreshold(50);
		ArrayList<T> temp = new ArrayList<T>(items.size());
		for (int i = 0; i < items.size(); i++) {
			temp.add(items.get(i));
		}
		mergesort(items, temp, 0, items.size() - 1);
	}

	/**
	 * Sorts the list using a merge sort.
	 * 
	 * @param items - the list to be sorted
	 * @param temp  - a temporary arrayList for sorted items
	 * @param left  - the left index
	 * @param right - the right index
	 */
	private static <T extends Comparable<? super T>> void mergesort(ArrayList<T> items, ArrayList<T> temp, int left,
			int right) {
		if (((right - left) + 1) <= threshold) {
			insertionSort(items, left, right);
		} else if (left < right) {
			int mid = (left + right) / 2;
			mergesort(items, temp, left, mid);
			mergesort(items, temp, mid + 1, right);
			merge(items, temp, left, mid, right);
		}
	}

	/**
	 * Sorts the array from the left to right indices.
	 * 
	 * @param items - the array that's being sorted
	 * @param temp  - a temporary arrayList being used in the sort
	 * @param left  - the left index
	 * @param mid   - the middle index
	 * @param right - the right index
	 */
	private static <T extends Comparable<? super T>> void merge(ArrayList<T> items, ArrayList<T> temp, int left,
			int mid, int right) {
		for (int i = left; i <= right; i++) {
			temp.set(i, items.get(i));
		}

		int l = left;
		int m = mid + 1;
		int a = left;

		while (l <= mid && m <= right) {
			if (temp.get(l).compareTo(temp.get(m)) < 0) {
				items.set(a, temp.get(l));
				l++;
			} else {
				items.set(a, temp.get(m));
				m++;
			}
			a++;
		}
		while (l <= mid) {
			items.set(a, temp.get(l));
			a++;
			l++;
		}
	}

	/**
	 * Sorts an array list from the left index to the right index
	 * 
	 * @param items - the list that will be sorted
	 * @param left  - the index of the beginning of the sorted section
	 * @param right - the index of the end of the sorted section
	 */
	private static <T extends Comparable<? super T>> void insertionSort(ArrayList<T> items, int left, int right) {
		for (int i = left; i <= right; i++) {
			T val = items.get(i);
			int j;
			for (j = i - 1; j >= left && items.get(j).compareTo(val) > 0; j--) {
				items.set(j + 1, items.get(j));
			}
			items.set(j + 1, val);
		}
	}

	/**
	 * Sets the value of threshold
	 * 
	 * @param value - the value threshold will be set to
	 */
	private static void setThreshold(int value) {
		threshold = value;
	}

	/**
	 * Sorts the list using a quick sort.
	 * 
	 * @param items - the list to be sorted
	 */
	public static <T extends Comparable<? super T>> void quicksort(ArrayList<T> items) {
		quicksort(items, 0, items.size() - 1);
	}

	/**
	 * Sorts a list based on the beginning and end indices.
	 * 
	 * @param items - the list being sorted
	 * @param left  - the beginning index
	 * @param right - the end index
	 */
	private static <T extends Comparable<? super T>> void quicksort(ArrayList<T> items, int left, int right) {
		int lBound = left;
		int rBound = right;
		T pivot = items.get(getPivotMO3(items, left, right));
		while (lBound <= rBound) {
			while (items.get(lBound).compareTo(pivot) < 0) {
				lBound++;
			}
			while (items.get(rBound).compareTo(pivot) > 0) {
				rBound--;
			}
			if (lBound <= rBound) {
				swapValues(items, lBound, rBound);
				lBound++;
				rBound--;
			}
		}
		if (left < rBound) {
			quicksort(items, left, rBound);
		}
		if (lBound < right) {
			quicksort(items, lBound, right);
		}
	}

	/**
	 * Swaps the values in a list at the 2 passed indices in the a list.
	 * 
	 * @param items - the list being swapped
	 * @param i1    - the first index
	 * @param i2    - the second index
	 */
	private static <T> void swapValues(ArrayList<T> items, int i1, int i2) {
		T val1 = items.get(i1);
		T val2 = items.get(i2);
		items.set(i1, val2);
		items.set(i2, val1);
	}

	/**
	 * Returns the median of the beginning, middle, and end values in a list based
	 * on the beginning and end indices
	 * 
	 * @param data  - the list being searched
	 * @param left  - the beginning index
	 * @param right - the end index
	 */
	private static <T extends Comparable<? super T>> int getPivotMO3(ArrayList<T> data, int left, int right) {
		T first = data.get(left);
		T last = data.get(right);
		T middle = data.get((right + left) / 2);
		boolean firstLMid = (first.compareTo(middle) < 0);
		boolean firstLLast = (first.compareTo(last) < 0);
		boolean midLLast = (middle.compareTo(last) < 0);
		if ((firstLMid && midLLast) || (!firstLMid && !midLLast)) {
			return (right + left) / 2;
		} else if ((!firstLMid && firstLLast) || (!firstLLast && firstLMid)) {
			return left;
		} else {
			return right;
		}
	}

	/**
	 * Generates and returns an ArrayList of integers 1 to size in ascending order.
	 * 
	 * @param size - the size
	 */
	public static ArrayList<Integer> generateAscending(int size) {
		ArrayList<Integer> temp = new ArrayList<>();
		for (int i = 1; i <= size; i++) {
			temp.add(i);
		}
		return temp;
	}

	/**
	 * Generates and returns an ArrayList of integers 1 to size in a random order.
	 * 
	 * @param size - the size
	 */
	public static ArrayList<Integer> generatePermuted(int size) {
		ArrayList<Integer> temp = new ArrayList<>();
		for (int i = 1; i <= size; i++) {
			temp.add(i);
		}
		Collections.shuffle(temp);
		return temp;
	}

	/**
	 * Generates and returns an ArrayList of integers 1 to size in descending order.
	 * 
	 * @param size - the size
	 */
	public static ArrayList<Integer> generateDescending(int size) {
		ArrayList<Integer> temp = new ArrayList<>();
		for (int i = size; i > 0; i--) {
			temp.add(i);
		}
		return temp;
	}
}
