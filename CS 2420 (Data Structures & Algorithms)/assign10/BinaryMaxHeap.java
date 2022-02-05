package assign10;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * This class represents binary max heap where all of the "children" are smaller
 * than the root
 * 
 * @author Isaac Gibson and Matthew Johnsen
 * @version April 8, 2019
 *
 * @param <E>
 */
public class BinaryMaxHeap<E> implements PriorityQueue<E> {

	private ArrayList<E> heap;

	private Comparator<? super E> comp;

	private int size;

	public BinaryMaxHeap() {
		heap = new ArrayList<>();
		size = 0;
		heap.add(null);
	}

	public BinaryMaxHeap(Comparator<? super E> c) {
		this();
		comp = c;
	}

	public BinaryMaxHeap(List<? extends E> l) {
		this();
		for (E value : l) {
			add(value);
		}
	}

	public BinaryMaxHeap(List<? extends E> l, Comparator<? super E> c) {
		this(c);
		for (E value : l) {
			add(value);
		}
	}

	@Override
	public void add(E item) {
		if (size < 1) {
			heap.add(item);
			size++;
		} else {
			heap.add(item);
			size++;
			percolateUp(size);
		}
	}

	private void percolateUp(int index) {
		while (index / 2 != 0 && compareVals(heap.get(index), heap.get(index / 2)) > 0) {
			swap(index, index / 2);
			index = index / 2;
		}
	}

	private void swap(int i1, int i2) {
		E value;
		value = heap.get(i1);
		heap.set(i1, heap.get(i2));
		heap.set(i2, value);
	}

	@SuppressWarnings("unchecked")
	private int compareVals(E val1, E val2) {
		if (comp == null) {
			return ((Comparable<E>) val1).compareTo(val2);
		} else {
			return comp.compare(val1, val2);
		}
	}

	@Override
	public E peek() throws NoSuchElementException {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		return heap.get(1);
	}

	@Override
	public E extractMax() throws NoSuchElementException {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		E temp = heap.get(1);
		if (size == 1) {
			heap.remove(1);
			size--;
			return temp;
		} else {
			heap.set(1, heap.get(size));
			heap.remove(size);
			size--;
			percolateDown();
		}

		return temp;
	}

	private void percolateDown() {
		int index = 1;
		while (index * 2 <= size && compareVals(heap.get(index), heap.get(index * 2)) < 0
				|| index * 2 + 1 <= size && compareVals(heap.get(index), heap.get(index * 2 + 1)) < 0) {
			int temp = findGreater(index);
			swap(index, temp);
			index = temp;
		}
	}

	private int findGreater(int index) {
		E temp1, temp2;
		temp1 = heap.get(index * 2);
		if (index * 2 + 1 <= size) {
			temp2 = heap.get(index * 2 + 1);
		} else {
			return index * 2;
		}
		if (compareVals(temp1, temp2) > 0) {
			return index * 2;
		} else {
			return index * 2 + 1;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public void clear() {
		heap = new ArrayList<>();
		size = 0;
	}

	@Override
	public Object[] toArray() {
		Object[] items = new Object[size];
		for (int i = 1; i <= size; i++) {
			items[i - 1] = heap.get(i);
		}
		return items;
	}
}
