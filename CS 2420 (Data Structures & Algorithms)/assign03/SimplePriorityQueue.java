package assign03;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * A priority queue that supports access of the minimum element only.
 * 
 * @author Erin Parker, Isaac Gibson, Matthew Johnsen
 * @version January 28, 2019
 */
public class SimplePriorityQueue<Type> implements PriorityQueue<Type> {

	private int size;

	private Type[] queue;

	private boolean isComparable;

	private Comparator<? super Type> c;

	/**
	 * Creates an empty SimplePriorityQueue that uses the comparable interface for
	 * sorting
	 */
	@SuppressWarnings("unchecked")
	public SimplePriorityQueue() {
		size = 0;
		queue = (Type[]) new Object[16];
		isComparable = true;
		c = null;
	}

	/**
	 * Creates an empty SimplePriorityQueue that uses a comparator for sorting
	 */
	@SuppressWarnings("unchecked")
	public SimplePriorityQueue(Comparator<? super Type> c) {
		size = 0;
		queue = (Type[]) new Object[16];
		isComparable = false;
		this.c = c;
	}

	@Override
	public Type findMin() throws NoSuchElementException {
		if (size <= 0) {
			throw new NoSuchElementException();
		}
		return queue[size - 1];
	}

	@Override
	public Type deleteMin() throws NoSuchElementException {
		if (size <= 0) {
			throw new NoSuchElementException();
		}
		size--;
		return queue[size];
	}

	@Override
	public void insert(Type item) {
		size++;
		if (size > queue.length) {
			queue = Arrays.copyOf(queue, queue.length * 2);
		}
		int index = BinarySearch(item);
		Type temp = queue[index];
		Type temp2;
		queue[index] = item;
		while (index < size - 1) {
			temp2 = queue[index + 1];
			queue[index + 1] = temp;
			temp = temp2;
			index++;
		}
	}

	@Override
	public void insertAll(Collection<? extends Type> coll) {
		for (Type t : coll) {
			insert(t);
		}
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Returns the length (not size) of the priority queue
	 */
	public int getArraySize() {
		return queue.length;
	}

	@Override
	public boolean isEmpty() {
		if (size == 0) {
			return true;
		}
		return false;
	}

	@Override
	public void clear() {
		size = 0;
	}

	/**
	 * Finds the correct position for the passed item and returns the index
	 * 
	 * @param item - the item having its index searched for
	 * @return the index of where item would go in the Priority Queue.
	 */
	@SuppressWarnings("unchecked")
	private int BinarySearch(Type item) {
		int first = 0;
		int last = size - 1;

		if (isComparable) {
			while (first < last) {
				int mid = (first + last) / 2;
				if (((Comparable<? super Type>) item).compareTo(queue[mid]) > 0) {
					last = mid;
				} else if (((Comparable<? super Type>) item).compareTo(queue[mid]) < 0) {
					first = mid + 1;
				} else {
					return mid;
				}
			}
			return first;
		} else {
			while (first < last) {
				int mid = (first + last) / 2;
				if (c.compare(item, queue[mid]) > 0) {
					last = mid;
				} else if (c.compare(item, queue[mid]) < 0) {
					first = mid + 1;
				} else {
					return mid;
				}
			}
			return first;
		}
	}
}