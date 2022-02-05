package assign06;

import java.util.NoSuchElementException;

/**
 * The class represents a stack that is backed by a LinkedList
 * 
 * @author Isaac Gibson and Matthew Johnsen
 * @version February 25, 2019
 */
public class LinkedListStack<E> implements Stack<E> {

	private SinglyLinkedList<E> stack;

	public LinkedListStack() {
		stack = new SinglyLinkedList<E>();
	}

	@Override
	public void clear() {
		stack.clear();
	}

	@Override
	public boolean isEmpty() {
		return stack.isEmpty();
	}

	@Override
	public E peek() throws NoSuchElementException {
		return stack.getFirst();
	}

	@Override
	public E pop() throws NoSuchElementException {
		return stack.removeFirst();
	}

	@Override
	public void push(E element) {
		stack.addFirst(element);
	}

	@Override
	public int size() {
		return stack.size();
	}

}
