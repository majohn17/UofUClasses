package assign03;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.NoSuchElementException;

import assign03.SimplePriorityQueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SimplePriorityQueueTester {
	private SimplePriorityQueue<String> sQ;
	private SimplePriorityQueue<Integer> iQ;

	@BeforeEach
	void setUp() throws Exception {
		sQ = new SimplePriorityQueue<String>(new CompareByLength());

		sQ.insert("Wow");
		sQ.insert("Dog");
		sQ.insert("telephone");
		sQ.insert("Alligator");
		sQ.insert("Longstrings");
		sQ.insert("Temp");

		iQ = new SimplePriorityQueue<Integer>();

		iQ.insert(127);
		iQ.insert(42);
		iQ.insert(9);
		iQ.insert(22022);
		iQ.insert(21);
		iQ.insert(11);
		iQ.insert(47);
		iQ.insert(68);
		iQ.insert(12345);
		iQ.insert(1277);
		iQ.insert(123);
		iQ.insert(145);
	}

	@Test
	void testFindMinString() {
		assertEquals("Dog", sQ.findMin());
		assertFalse("Wow".equals(sQ.findMin()));
		assertFalse("Temp".equals(sQ.findMin()));

	}

	@Test
	void testFindMinException() {
		SimplePriorityQueue<String> queue = new SimplePriorityQueue<String>(new CompareByLength());
		assertThrows(NoSuchElementException.class, () -> {
			queue.findMin();
		});

	}

	@Test
	void testFindMinInt() {
		assertEquals(9, iQ.findMin().intValue());
		assertFalse(127 == iQ.findMin().intValue());
		assertFalse(145 == iQ.findMin().intValue());
	}

	@Test
	void testDeleteMin() {
		assertEquals("Dog", sQ.deleteMin());
		assertEquals(5, sQ.size());
		assertEquals("Wow", sQ.deleteMin());
		assertEquals(4, sQ.size());
		assertEquals("Temp", sQ.deleteMin());
		assertEquals(3, sQ.size());
		assertEquals("Alligator", sQ.deleteMin());
		assertEquals(2, sQ.size());
		assertEquals("telephone", sQ.deleteMin());
		assertEquals(1, sQ.size());
		assertEquals("Longstrings", sQ.deleteMin());
		assertEquals(0, sQ.size());
		assertThrows(NoSuchElementException.class, () -> {
			sQ.deleteMin();
		});
	}

	@Test
	void testDeleteMinException() {
		SimplePriorityQueue<String> queue = new SimplePriorityQueue<String>(new CompareByLength());
		assertThrows(NoSuchElementException.class, () -> {
			queue.deleteMin();
		});
	}

	@Test
	void testInsert() {
		assertEquals("Dog", sQ.findMin());
		sQ.deleteMin();
		sQ.deleteMin();
		sQ.deleteMin();
		sQ.insert("Tester");
		assertEquals("Tester", sQ.findMin());
		sQ.deleteMin();
		sQ.deleteMin();
		sQ.insert("Telephone");
		assertTrue(sQ.size() == 3);
		assertEquals("Telephone", sQ.findMin());
	}

	@Test
	void testInsert2() {
		assertTrue(iQ.findMin() == 9);
		iQ.insert(-24);
		assertTrue(iQ.findMin() == -24);
		iQ.deleteMin();
		iQ.deleteMin();
		iQ.deleteMin();
		iQ.deleteMin();
		iQ.deleteMin();
		iQ.deleteMin();
		assertTrue(iQ.size() == 7);
		assertTrue(iQ.findMin() == 68);
	}

	@Test
	void testInsertResize() {
		sQ.insert("7");
		sQ.insert("8");
		sQ.insert("9");
		sQ.insert("10");
		sQ.insert("11");
		sQ.insert("12");
		sQ.insert("13");
		sQ.insert("14");
		sQ.insert("15");
		sQ.insert("16");
		assertTrue(sQ.getArraySize() == 16);
		sQ.insert("17");
		assertTrue(sQ.getArraySize() == 32);
	}

	@Test
	void testInsertAll() {
		SimplePriorityQueue<Integer> test = new SimplePriorityQueue<>();
		ArrayList<Integer> temp = new ArrayList<>();
		temp.add(124);
		temp.add(12);
		temp.add(1244);
		temp.add(1);
		temp.add(71);
		test.insertAll(temp);
		ArrayList<Integer> temp2 = new ArrayList<>();
		temp2.add(35);
		temp2.add(-47);
		assertTrue(test.size() == 5);
		assertTrue(test.findMin() == 1);
		test.insertAll(temp2);
		assertTrue(test.size() == 7);
		assertTrue(test.findMin() == -47);
	}

	@Test
	void testSize() {
		assertEquals(6, sQ.size());
		sQ.insert("Pineapple");
		assertEquals(7, sQ.size());
		sQ.clear();
		assertEquals(0, sQ.size());
		sQ.insert("Pizza");
		assertEquals(1, sQ.size());
	}

	@Test
	void testGetArraySize() {
		assertTrue(sQ.getArraySize() == 16);
		sQ.insert("Testing");
		assertTrue(sQ.getArraySize() == 16);
		assertTrue(iQ.getArraySize() == 16);
		iQ.insert(124124);
		assertTrue(sQ.getArraySize() == 16);
	}

	@Test
	void testIsEmpty() {
		SimplePriorityQueue<String> queue = new SimplePriorityQueue<String>(new CompareByLength());

		assertFalse(sQ.isEmpty());
		assertTrue(queue.isEmpty());
	}

	@Test
	void testIsClear() {
		assertEquals(6, sQ.size());
		sQ.insert("Pineapple");
		assertEquals(7, sQ.size());
		sQ.clear();
		assertEquals(0, sQ.size());
		sQ.insert("Pizza");
		assertEquals(1, sQ.size());
		sQ.clear();
		assertEquals(0, sQ.size());
	}

	/**
	 * Compares strings by length or lexicographically if the strings are the same
	 * length
	 */
	protected class CompareByLength implements Comparator<String> {
		public int compare(String LHS, String RHS) {
			if (LHS.length() > RHS.length()) {
				return 1;
			} else if (LHS.length() < RHS.length()) {
				return -1;
			} else {
				return LHS.compareTo(RHS);
			}
		}
	}
}
