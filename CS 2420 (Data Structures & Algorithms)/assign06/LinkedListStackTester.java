package assign06;

import static org.junit.jupiter.api.Assertions.*;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Provides testing methods for the LinkedListStack class
 * 
 * @author Isaac Gibson and Matthew Johnsen
 * @version February 25, 2019
 */
class LinkedListStackTester {

	private LinkedListStack<Integer> intStack;
	private LinkedListStack<String> stringStack;

	@BeforeEach
	void setup() throws Exception {
		intStack = new LinkedListStack<Integer>();
		intStack.push(6);
		intStack.push(5);
		intStack.push(4);
		intStack.push(3);
		intStack.push(2);
		intStack.push(1);

		stringStack = new LinkedListStack<String>();
		stringStack.push("f");
		stringStack.push("e");
		stringStack.push("d");
		stringStack.push("c");
		stringStack.push("b");
		stringStack.push("a");
	}

	@Test
	void testClear() {
		assertTrue(intStack.size() == 6);
		intStack.clear();
		assertTrue(intStack.size() == 0);
		assertTrue(stringStack.size() == 6);
		stringStack.clear();
		assertTrue(stringStack.size() == 0);
	}

	@Test
	void testIsEmpty() {
		assertTrue(!intStack.isEmpty());
		intStack.clear();
		assertTrue(intStack.isEmpty());
		assertTrue(!stringStack.isEmpty());
		stringStack.clear();
		assertTrue(stringStack.isEmpty());
	}

	@Test
	void testPeekInt() {
		assertTrue(intStack.peek() == 1);
		intStack.pop();
		intStack.pop();
		assertTrue(intStack.peek() == 3);
		intStack.clear();
		intStack.push(1);
		intStack.push(2);
		assertTrue(intStack.peek() == 2);
	}

	@Test
	void testPeekString() {
		assertEquals(stringStack.peek(), "a");
		stringStack.pop();
		stringStack.pop();
		assertEquals(stringStack.peek(), "c");
		stringStack.clear();
		stringStack.push("a");
		stringStack.push("b");
		assertEquals(stringStack.peek(), "b");
	}

	@Test
	void testPeekException() {
		intStack.clear();
		assertThrows(NoSuchElementException.class, () -> {
			intStack.peek();
		});
		intStack.push(-123);
		assertTrue(intStack.peek() == -123);
		intStack.pop();
		assertThrows(NoSuchElementException.class, () -> {
			intStack.peek();
		});
	}

	@Test
	void testPopInt() {
		assertTrue(intStack.size() == 6);
		assertTrue(intStack.pop() == 1);
		assertTrue(intStack.size() == 5);
		intStack.pop();
		intStack.pop();
		assertTrue(intStack.pop() == 4 && intStack.size() == 2);
		intStack.pop();
		assertTrue(intStack.pop() == 6 && intStack.size() == 0);
		assertThrows(NoSuchElementException.class, () -> {
			intStack.pop();
		});
	}

	@Test
	void testPopString() {
		assertTrue(stringStack.size() == 6);
		assertEquals(stringStack.pop(), "a");
		assertTrue(stringStack.size() == 5);
		stringStack.pop();
		stringStack.pop();
		assertTrue(stringStack.pop().equals("d") && stringStack.size() == 2);
		stringStack.pop();
		assertTrue(stringStack.pop().equals("f") && stringStack.size() == 0);
		assertThrows(NoSuchElementException.class, () -> {
			stringStack.pop();
		});
	}

	@Test
	void testPopException() {
		intStack.clear();
		assertThrows(NoSuchElementException.class, () -> {
			intStack.pop();
		});
		intStack.push(-123);
		assertTrue(intStack.pop() == -123 && intStack.size() == 0);
		assertThrows(NoSuchElementException.class, () -> {
			intStack.pop();
		});
	}

	@Test
	void testPushInt() {
		assertTrue(intStack.size() == 6);
		intStack.clear();
		intStack.push(-1231231);
		assertTrue(intStack.size() == 1);
		intStack.push(12331);
		intStack.push(2342345);
		assertTrue(intStack.size() == 3);
		assertTrue(intStack.pop() == 2342345);
	}

	@Test
	void testPushString() {
		assertTrue(stringStack.size() == 6);
		stringStack.clear();
		stringStack.push("asdf");
		assertTrue(stringStack.size() == 1);
		stringStack.push("asgsg");
		stringStack.push("qwertyuiop");
		assertTrue(stringStack.size() == 3);
		assertEquals(stringStack.pop(), "qwertyuiop");
	}
	
	@Test
	void testSize() {
		LinkedListStack<Double> test = new LinkedListStack<Double>();
		assertTrue(test.size() == 0);
		test.push(1.1);
		test.push(2.2);
		test.push(3.3);
		assertTrue(test.size() == 3);
		test.clear();
		assertTrue(test.size() == 0);
	}
}
