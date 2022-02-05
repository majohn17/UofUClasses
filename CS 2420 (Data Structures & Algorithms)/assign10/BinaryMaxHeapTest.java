package assign10;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class tests the BinaryMaxHeap class for its methods.
 * 
 * @author Isaac Gibson and Matthew Johnsen
 * @version April 8, 2019
 */
class BinaryMaxHeapTest {

	BinaryMaxHeap<Integer> heap;
	BinaryMaxHeap<String> heap2;

	@BeforeEach
	void setup() throws Exception {
		heap = new BinaryMaxHeap<Integer>();
		for (int i = 1; i <= 10; i++) {
			heap.add(i);
		}

		ArrayList<String> strings = new ArrayList<>();
		strings.add("ABC");
		strings.add("ABCD");
		strings.add("ABC");
		strings.add("A");
		strings.add("ABCDEF");
		strings.add("ABCDE");
		heap2 = new BinaryMaxHeap<>(strings, new OrderByLength());
	}

	@Test
	void constructFromListTest() {
		List<Integer> list = new ArrayList<>();
		int size = 10;
		for (int i = 1; i <= size; i++) {
			list.add(i);
		}

		heap = new BinaryMaxHeap<Integer>(list);
		assertTrue(size == heap.peek());
		assertTrue(size == heap.size());
	}

	@Test
	void constructFromListTest2() {
		assertEquals(heap2.peek(), "ABCDEF");
		assertTrue(heap2.size() == 6);
		assertEquals(heap2.extractMax(), "ABCDEF");
		assertEquals(heap2.peek(), "ABCDE");
		heap2.clear();
		assertTrue(heap2.size() == 0);
	}

	@Test
	void addTest() {
		heap = new BinaryMaxHeap<Integer>();

		for (int i = 1; i <= 10; i++) {
			heap.add(i);
			assertTrue(i == heap.peek());
			assertTrue(i == heap.size());
		}

	}

	@Test
	void peekTest() {
		assertEquals(heap2.peek(), "ABCDEF");
		heap2.extractMax();
		assertEquals(heap2.peek(), "ABCDE");
		heap2.extractMax();
		assertEquals(heap2.peek(), "ABCD");
		heap2.extractMax();
		assertEquals(heap2.peek(), "ABC");
		heap2.extractMax();
		assertEquals(heap2.peek(), "ABC");
		heap2.extractMax();
		assertEquals(heap2.peek(), "A");
		heap2.extractMax();
		assertThrows(NoSuchElementException.class, () -> heap2.peek());
	}

	@Test
	void extractMaxTest() {
		for (int i = 10; i >= 1; i--) {
			assertEquals(i, heap.extractMax().intValue());
		}
		assertThrows(NoSuchElementException.class, () -> heap.extractMax());
	}

	@Test
	void clearTest() {
		heap.clear();
		assertTrue(0 == heap.size());
	}

	@Test
	void toArrayTest() {
		Object[] o = heap.toArray();
		assertTrue(10 == o.length);
		assertTrue(o[0].equals(10));
	}

	protected class OrderByLength implements Comparator<String> {
		public int compare(String lhs, String rhs) {
			return lhs.length() - rhs.length();
		}
	}
}
