package assign10;

import static org.junit.jupiter.api.Assertions.*;
import static assign10.FindKLargest.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class tests the FindKLargest class for its methods.
 * 
 * @author Isaac Gibson and Matthew Johnsen
 * @version April 8, 2019
 */
class FindKLargestTest {

	List<String> strings;
	List<Integer> list;

	@BeforeEach
	void setup() throws Exception {

		strings = new ArrayList<>();
		strings.add("ABC");
		strings.add("ABCD");
		strings.add("ABC");
		strings.add("A");
		strings.add("ABCDEF");
		strings.add("ABCDE");

		list = new ArrayList<>();
		int size = 10;
		for (int i = 1; i <= size; i++) {
			list.add(i);
		}

	}

	@Test
	void findKLargestHeapIntTest() {
		int k = 5;
		list = findKLargestHeap(list, k);
		assertTrue(k == list.size());
		for (int i = 0, j = 10; i < k; i++, j--) {
			assertTrue(list.get(i) == j);
		}

	}

	@Test
	void findKLargestHeapStringTest() {
		int k = 5;
		strings = findKLargestHeap(strings, k, new OrderByLength());
		assertTrue(k == strings.size());
		assertEquals("ABCDEF", strings.get(0));
		assertEquals("ABCDE", strings.get(1));
		assertEquals("ABCD", strings.get(2));
		assertEquals("ABC", strings.get(3));
		assertEquals("ABC", strings.get(4));

	}

	@Test
	void findKLargestSortIntTest() {
		int k = 5;
		list = findKLargestSort(list, k);
		assertTrue(k == list.size());
		for (int i = 0, j = 10; i < k; i++, j--) {
			assertTrue(list.get(i) == j);
		}

	}

	@Test
	void findKLargestSortStringTest() {
		int k = 5;
		strings = findKLargestSort(strings, k, new OrderByLength());
		assertTrue(k == strings.size());
		assertEquals("ABCDEF", strings.get(0));
		assertEquals("ABCDE", strings.get(1));
		assertEquals("ABCD", strings.get(2));
		assertEquals("ABC", strings.get(3));
		assertEquals("ABC", strings.get(4));

	}

	protected class OrderByLength implements Comparator<String> {
		public int compare(String lhs, String rhs) {
			return lhs.length() - rhs.length();
		}
	}

}
