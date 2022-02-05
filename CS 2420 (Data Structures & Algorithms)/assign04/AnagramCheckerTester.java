package assign04;

import static org.junit.jupiter.api.Assertions.*;
import static assign04.AnagramChecker.*;

import java.util.Comparator;

import org.junit.jupiter.api.Test;

class AnagramCheckerTester {

	@Test
	void testSort() {
		assertEquals("abc", sort("cba"));
	}

	@Test
	void testSort2() {
		assertEquals("abcdefghijklmnopqrstuvwxyz", sort("qwertyuioplkjhgfdsazxcvbnm"));
	}

	@Test
	void testInsertionSort() {
		Integer[] arr = new Integer[] { 2, 3, 4, 5, 1 };
		insertionSort(arr, (Integer l, Integer r) -> (l - r));
		for (int i = 0; i < arr.length; i++)
			assertEquals(i + 1, arr[i].intValue());

	}

	@Test
	void testInsertionSort2() {
		String[] s = new String[] { "a", "ab", "abcde", "abc", "Abc" };
		insertionSort(s, new CompareByLength());
		assertEquals("a", s[0]);
		assertEquals("ab", s[1]);
		assertEquals("Abc", s[2]);
		assertEquals("abc", s[3]);
		assertEquals("abcde", s[4]);
	}

	@Test
	void testAreAnagrams() {
		assertTrue(areAnagrams("asdf", "fdsa"));
		assertFalse(areAnagrams("asqf", "fdsa"));
	}

	@Test
	void testAreAnagrams2() {
		assertTrue(areAnagrams("Alert", "later"));
	}

	@Test
	void testGetLargestAnagramGroup() {
		String[] s = new String[] { "ba", "ab", "abcde", "adebc", "edcba" };
		s = getLargestAnagramGroup(s);
		assertEquals("abcde", s[0]);
		assertEquals("adebc", s[1]);
		assertEquals("edcba", s[2]);
	}

	@Test
	void testGetLargestAnagramGroupEmpty() {
		assertTrue(getLargestAnagramGroup(new String[0]).length == 0);
	}

	@Test
	void testGetLargestAnagramGroupFile() {
		String[] s = getLargestAnagramGroup(
				"C:\\Users\\Isaac Gibson\\eclipse-workspace\\Assignments\\src\\assign04\\sample_word_list.txt");
		assertEquals("carets", s[0]);
		assertEquals("Caters", s[1]);
		assertEquals("caster", s[2]);
		assertEquals("crates", s[3]);
		assertEquals("Reacts", s[4]);
		assertEquals("recast", s[5]);
		assertEquals("traces", s[6]);
	}

	@Test
	void testGetLargestAnagramGroupFileEmpty() {
		assertTrue(getLargestAnagramGroup(
				"C:\\Users\\Isaac Gibson\\eclipse-workspace\\Assignments\\src\\assign04\\EmptyList.txt").length == 0);
		assertTrue(getLargestAnagramGroup("\\NotReal").length == 0);
	}

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