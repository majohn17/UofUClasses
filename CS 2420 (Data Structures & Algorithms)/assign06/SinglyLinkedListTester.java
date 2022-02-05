package assign06;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Provides testing methods for the SinglyLinkedList class
 * 
 * @author Isaac Gibson and Matthew Johnsen
 * @version February 25, 2019
 */
class SinglyLinkedListTester {

	private SinglyLinkedList<Integer> intList;
	private SinglyLinkedList<String> stringList;

	@BeforeEach
	void setup() throws Exception {
		intList = new SinglyLinkedList<Integer>();
		intList.addFirst(6);
		intList.addFirst(5);
		intList.addFirst(4);
		intList.addFirst(3);
		intList.addFirst(2);
		intList.addFirst(1);

		stringList = new SinglyLinkedList<String>();
		stringList.addFirst("f");
		stringList.addFirst("e");
		stringList.addFirst("d");
		stringList.addFirst("c");
		stringList.addFirst("b");
		stringList.addFirst("a");
	}

	@Test
	void testAddFirstInt() {
		assertTrue(intList.getFirst() == 1);
		intList.addFirst(9);
		assertTrue(intList.getFirst() == 9);
		intList.addFirst(-35);
		assertTrue(intList.getFirst() == -35);
	}

	@Test
	void testAddFirstString() {
		assertEquals(stringList.getFirst(), "a");
		stringList.addFirst("x");
		assertEquals(stringList.getFirst(), "x");
		stringList.addFirst("z");
		assertEquals(stringList.getFirst(), "z");
	}

	@Test
	void testAddInt() {
		assertTrue(intList.get(4) == 5);
		intList.add(4, 9);
		assertTrue(intList.get(4) == 9);
		intList.add(4, -35);
		assertTrue(intList.get(4) == -35);
	}

	@Test
	void testAddString() {
		assertEquals(stringList.get(4), "e");
		stringList.add(4, "x");
		assertEquals(stringList.get(4), "x");
		stringList.add(4, "z");
		assertEquals(stringList.get(4), "z");
	}

	@Test
	void testAddZero() {
		assertTrue(intList.get(0) == 1);
		intList.add(0, 7);
		assertTrue(intList.get(0) == 7);
		assertEquals(stringList.get(0), "a");
		stringList.add(0, "x");
		assertEquals(stringList.get(0), "x");
	}

	@Test
	void testAddAtSize() {
		assertTrue(intList.size() == 6);
		intList.add(intList.size(), 7);
		assertTrue(intList.size() == 7);
		assertTrue(intList.get(6) == 7);
	}

	@Test
	void testAddException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			intList.add(-1, 12);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			intList.add(7, 12);
		});
	}

	@Test
	void testGetFirstInt() {
		assertTrue(intList.getFirst() == 1);
		intList.addFirst(-123);
		assertTrue(intList.getFirst() == -123);
		intList.addFirst(24445);
		assertTrue(intList.getFirst() == 24445);
	}

	@Test
	void testGetFirstString() {
		assertEquals(stringList.getFirst(), "a");
		stringList.addFirst("asdf");
		assertEquals(stringList.getFirst(), "asdf");
		stringList.addFirst("qwertyuiop");
		assertEquals(stringList.getFirst(), "qwertyuiop");
	}

	@Test
	void testGetInt() {
		assertTrue(intList.get(4) == 5);
		intList.add(4, -123);
		assertTrue(intList.get(4) == -123);
		intList.add(4, 24445);
		assertTrue(intList.get(4) == 24445);
	}

	@Test
	void testGetString() {
		assertEquals(stringList.get(4), "e");
		stringList.add(4, "asdf");
		assertEquals(stringList.get(4), "asdf");
		assertEquals(stringList.get(5), "e");
		assertEquals(stringList.getFirst(), "a");
		stringList.add(4, "qwertyuiop");
		assertEquals(stringList.get(4), "qwertyuiop");
	}

	@Test
	void testGetZero() {
		assertEquals(stringList.get(0), "a");
		assertTrue(intList.get(0) == 1);
	}

	@Test
	void testGetException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			intList.get(-1);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			intList.get(7);
		});
	}

	@Test
	void testRemoveFirst() {
		assertTrue(intList.size() == 6);
		assertTrue(intList.getFirst() == 1);
		intList.removeFirst();
		assertTrue(intList.size() == 5);
		assertTrue(intList.getFirst() == 2);
	}

	@Test
	void testRemoveFirstException() {
		SinglyLinkedList<Double> test = new SinglyLinkedList<Double>();
		assertThrows(NoSuchElementException.class, () -> {
			test.removeFirst();
		});
	}

	@Test
	void testRemove() {
		assertTrue(intList.size() == 6);
		assertTrue(intList.get(4) == 5);
		intList.remove(4);
		assertTrue(intList.size() == 5);
		assertTrue(intList.get(4) == 6);
	}

	@Test
	void testRemoveZero() {
		assertTrue(intList.size() == 6);
		assertTrue(intList.getFirst() == 1);
		intList.remove(0);
		assertTrue(intList.size() == 5);
		assertTrue(intList.getFirst() == 2);
	}

	@Test
	void testRemoveException() {
		assertThrows(IndexOutOfBoundsException.class, () -> {
			intList.remove(-1);
		});
		assertThrows(IndexOutOfBoundsException.class, () -> {
			intList.remove(7);
		});
	}

	@Test
	void testIndexOf() {
		assertTrue(stringList.indexOf("a") == 0);
		assertTrue(stringList.indexOf("b") == 1);
		assertTrue(stringList.indexOf("c") == 2);
		assertTrue(stringList.indexOf("d") == 3);
		assertTrue(stringList.indexOf("e") == 4);
		assertTrue(stringList.indexOf("f") == 5);
	}

	@Test
	void testSize() {
		SinglyLinkedList<Double> test = new SinglyLinkedList<Double>();
		assertTrue(test.size() == 0);
		test.addFirst(2.2);
		assertTrue(test.size() == 1);
		test.removeFirst();
		assertTrue(test.size() == 0);
	}

	@Test
	void testIsEmpty() {
		SinglyLinkedList<Double> test = new SinglyLinkedList<Double>();
		assertTrue(test.isEmpty());
		test.addFirst(2.2);
		assertTrue(!test.isEmpty());
		test.removeFirst();
		assertTrue(test.isEmpty());
	}

	@Test
	void testClear() {
		assertTrue(intList.size() == 6);
		intList.clear();
		assertTrue(intList.size() == 0);
		assertThrows(NoSuchElementException.class, () -> {
			intList.getFirst();
		});
	}

	@Test
	void testToArray() {
		Object[] temp = intList.toArray();
		assertTrue((int) temp[0] == 1);
		assertTrue((int) temp[1] == 2);
		assertTrue((int) temp[2] == 3);
		assertTrue((int) temp[3] == 4);
		assertTrue((int) temp[4] == 5);
		assertTrue((int) temp[5] == 6);
		assertThrows(IndexOutOfBoundsException.class, () -> {
			System.out.print(temp[6]);
		});
	}

	@Test
	void testIterator() {
		Iterator<Integer> iter = intList.iterator();
		intList.printValues();
		assertTrue(iter.next() == 1);
		assertTrue(iter.next() == 2);
		iter.remove();
		intList.printValues();
		assertTrue(intList.size() == 5);
		assertThrows(IllegalStateException.class, () -> {
			iter.remove();
		});
		assertTrue(iter.next() == 3);
		iter.remove();
		intList.printValues();
		assertTrue(intList.size() == 4);
		assertTrue(iter.next() == 4);
		iter.remove();
		intList.printValues();
		assertTrue(intList.size() == 3);
		assertTrue(iter.next() == 5);
		assertTrue(iter.next() == 6);
		assertThrows(NoSuchElementException.class, () -> {
			iter.next();
		});
		iter.remove();
		intList.printValues();
		assertTrue(intList.size() == 2);
	}
	
	@Test
	void testIterator2() {
		SinglyLinkedList<Integer> test = new SinglyLinkedList<Integer>();
		test.addFirst(1);
		Iterator<Integer> iter = test.iterator();
		assertTrue(iter.next() == 1);
		iter.remove();
		assertTrue(test.size() == 0);
	}
}
