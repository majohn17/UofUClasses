package assign06;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * The class represents a singly linked list where it can only be traversed in a
 * forward direction.
 * 
 * @author Isaac Gibson and Matthew Johnsen
 * @version February 25, 2019
 */
public class SinglyLinkedList<E> implements List<E> {

	private Node<E> start;
	private int size;

	private class Node<T> {
		public T data;
		public Node<T> next;

		public Node(T data, Node<T> next) {
			this.data = data;
			this.next = next;
		}

	}

	public SinglyLinkedList() {
		start = null;
		size = 0;
	}

	@Override
	public void addFirst(E element) {
		start = new Node<E>(element, start);
		size++;
	}

	@Override
	public void add(int index, E element) throws IndexOutOfBoundsException {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		if (index == 0) {
			addFirst(element);
		} else {
			Node<E> prev = findPrevNode(index);
			add(prev, element);
		}

	}

	/**
	 * Adds a node holding element between previous and its next
	 * 
	 * @param prev
	 * @param element
	 */
	private void add(Node<E> prev, E element) {
		prev.next = new Node<E>(element, prev.next);
		size++;
	}

	/**
	 * Returns the node before the node at the index or null otherwise
	 * 
	 * @param index
	 */
	private Node<E> findPrevNode(int index) {
		if (index == 0) {
			return null;
		}
		Node<E> temp = start;
		for (int i = 0; i < index - 1; i++) {
			temp = temp.next;
		}
		return temp;
	}

	@Override
	public E getFirst() throws NoSuchElementException {
		if (size == 0)
			throw new NoSuchElementException();
		return start.data;
	}

	@Override
	public E get(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		if (index == 0) {
			return start.data;
		}
		return findPrevNode(index).next.data;
	}

	@Override
	public E removeFirst() throws NoSuchElementException {
		if (size == 0) {
			throw new NoSuchElementException();
		}
		E temp = start.data;
		start = start.next;
		size--;
		return temp;
	}

	@Override
	public E remove(int index) throws IndexOutOfBoundsException {
		if (index < 0 || index > size)
			throw new IndexOutOfBoundsException();
		if (index == 0) {
			return removeFirst();
		} else {
			Node<E> prev = findPrevNode(index);
			return removeNext(prev);
		}

	}

	/**
	 * Removes the previous nodes next
	 * 
	 * @param prev
	 */
	private E removeNext(Node<E> prev) {
		E temp = prev.next.data;
		prev.next = prev.next.next;
		size--;
		return temp;
	}

	@Override
	public int indexOf(E element) {
		Node<E> temp = start;
		for (int i = 0; i < size; i++) {
			if (temp.data.equals(element))
				return i;
			temp = temp.next;
		}
		return -1;
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
		start = null;
		size = 0;
	}

	@Override
	public Object[] toArray() {
		Object[] temp = new Object[size];
		Node<E> value = start;
		for (int i = 0; i < size; i++) {
			temp[i] = value.data;
			value = value.next;
		}
		return temp;
	}

	@Override
	public Iterator<E> iterator() {
		return new LinkedListIterator();
	}

	private class LinkedListIterator implements Iterator<E> {
		private Node<E> current;
		private Node<E> previous;
		private boolean canRemove;
		private int index;

		public LinkedListIterator() {
			current = start;
			previous = null;
			index = 0;
			canRemove = false;
		}

		@Override
		public boolean hasNext() {
			return index < size;
		}

		@Override
		public E next() {
			if (!hasNext())
				throw new NoSuchElementException();
			canRemove = true;
			index++;
			E temp = current.data;
			previous = current;
			current = current.next;
			return temp;
		}

		@Override
		public void remove() {
			if (canRemove == true) {
				canRemove = false;
				index--;
				SinglyLinkedList.this.remove(index);
				current = previous.next;
			} else {
				throw new IllegalStateException();
			}

		}
	}

	/**
	 * Prints the values in the linked list
	 */
	public void printValues() {
		Object[] temp = this.toArray();
		for (int i = 0; i < size; i++) {
			System.out.print(temp[i] + " ");
		}
		System.out.println("");
	}
}
