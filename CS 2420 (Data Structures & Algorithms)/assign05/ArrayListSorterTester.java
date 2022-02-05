package assign05;

import static assign05.ArrayListSorter.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Provides testing methods for the ArrayListSorter class
 * 
 * @author Jacob Montes and Matthew Johnsen
 * @version February 11, 2019
 */
class ArrayListSorterTester {
	private ArrayList<Integer> ascendingSmallList, ascendingLargeList, descendingSmallList, descendingLargeList,
			randomSmallList, randomLargeList, setList;
	private ArrayList<String> randomWordList, sortedWordList;

	@BeforeEach
	void setup() throws Exception {
		ascendingSmallList = new ArrayList<Integer>();
		for (int i = 1; i < 9; i++) {
			ascendingSmallList.add(i);
		}

		ascendingLargeList = new ArrayList<Integer>();
		for (int i = 1; i < 1001; i++) {
			ascendingLargeList.add(i);
		}

		descendingSmallList = new ArrayList<Integer>();
		for (int i = 8; i > 0; i--) {
			descendingSmallList.add(i);
		}

		descendingLargeList = new ArrayList<Integer>();
		for (int i = 1000; i > 0; i--) {
			descendingLargeList.add(i);
		}

		randomSmallList = new ArrayList<Integer>();
		for (int i = 1; i < 9; i++) {
			randomSmallList.add(i);
		}
		Collections.shuffle(randomSmallList);

		randomLargeList = new ArrayList<Integer>();
		for (int i = 1; i < 1001; i++) {
			randomLargeList.add(i);
		}
		Collections.shuffle(randomLargeList);

		setList = new ArrayList<Integer>();
		setList.add(8);
		setList.add(1);
		setList.add(5);
		setList.add(6);
		setList.add(7);
		setList.add(2);
		setList.add(3);
		setList.add(4);

		randomWordList = new ArrayList<String>();
		randomWordList.add("Alligator");
		randomWordList.add("Bear");
		randomWordList.add("Cat");
		randomWordList.add("Dog");
		randomWordList.add("Elephant");
		randomWordList.add("Fox");
		randomWordList.add("Gorilla");
		randomWordList.add("Horse");
		randomWordList.add("Iguana");
		randomWordList.add("Jaguar");
		randomWordList.add("Kangaroo");
		randomWordList.add("Lion");
		randomWordList.add("Moose");
		randomWordList.add("Narwhal");
		randomWordList.add("Octopus");
		randomWordList.add("Panda");
		randomWordList.add("Quail");
		randomWordList.add("Rhinocerus");
		randomWordList.add("Snake");
		randomWordList.add("Tiger");
		randomWordList.add("Uuuuuu");
		randomWordList.add("VampireBat");
		randomWordList.add("Walrus");
		randomWordList.add("Xxxxxx");
		randomWordList.add("Yak");
		randomWordList.add("Zebra");
		Collections.shuffle(randomWordList);

		sortedWordList = new ArrayList<String>();
		sortedWordList.add("Alligator");
		sortedWordList.add("Bear");
		sortedWordList.add("Cat");
		sortedWordList.add("Dog");
		sortedWordList.add("Elephant");
		sortedWordList.add("Fox");
		sortedWordList.add("Gorilla");
		sortedWordList.add("Horse");
		sortedWordList.add("Iguana");
		sortedWordList.add("Jaguar");
		sortedWordList.add("Kangaroo");
		sortedWordList.add("Lion");
		sortedWordList.add("Moose");
		sortedWordList.add("Narwhal");
		sortedWordList.add("Octopus");
		sortedWordList.add("Panda");
		sortedWordList.add("Quail");
		sortedWordList.add("Rhinocerus");
		sortedWordList.add("Snake");
		sortedWordList.add("Tiger");
		sortedWordList.add("Uuuuuu");
		sortedWordList.add("VampireBat");
		sortedWordList.add("Walrus");
		sortedWordList.add("Xxxxxx");
		sortedWordList.add("Yak");
		sortedWordList.add("Zebra");
	}

	@Test
	void testMergeSortSmallDescendingList() {
		mergesort(descendingSmallList);
		assertEquals(descendingSmallList, ascendingSmallList);
	}

	@Test
	void testMergeSortLargeDescendingList() {
		mergesort(descendingLargeList);
		assertEquals(descendingLargeList, ascendingLargeList);
	}

	@Test
	void testMergeSortSmallRandomList() {
		mergesort(randomSmallList);
		assertEquals(randomSmallList, ascendingSmallList);
	}

	@Test
	void testMergeSortLargeRandomList() {
		mergesort(randomLargeList);
		assertEquals(randomLargeList, ascendingLargeList);
	}

	@Test
	void testMergeSortWordList() {
		mergesort(randomWordList);
		assertEquals(randomWordList, sortedWordList);
	}

	@Test
	void testInsertionSortRandomSmallList() {
		insertionSort(randomSmallList, 0, (randomSmallList.size() - 1));
		assertEquals(randomSmallList, ascendingSmallList);
	}

	@Test
	void testInsertionSortRandomLargeList() {
		insertionSort(randomLargeList, 0, (randomLargeList.size() - 1));
		assertEquals(randomLargeList, ascendingLargeList);
	}

	@Test
	void testInsertionSortRandomWordList() {
		insertionSort(randomWordList, 0, (randomWordList.size() - 1));
		assertEquals(randomWordList, sortedWordList);
	}

	@Test
	void testQuickSortEdgeCase() {
		quicksort(setList);
		assertEquals(setList, ascendingSmallList);
	}

	@Test
	void testQuickSortSmallDescendingList() {
		quicksort(descendingSmallList);
		assertEquals(descendingSmallList, ascendingSmallList);
	}

	@Test
	void testQuickSortLargeDescendingList() {
		quicksort(descendingLargeList);
		assertEquals(descendingLargeList, ascendingLargeList);
	}

	@Test
	void testQuickSortSmallRandomList() {
		quicksort(randomSmallList);
		assertEquals(randomSmallList, ascendingSmallList);
	}

	@Test
	void testQuickSortLargeRandomList() {
		quicksort(randomWordList);
		assertEquals(randomWordList, sortedWordList);
	}

	@Test
	void testQuickSortWordList() {
		quicksort(randomWordList);
		assertEquals(randomWordList, sortedWordList);
	}

	@Test
	void testSwapValues() {
		swapValues(ascendingLargeList, 276, 513);
		assertTrue(ascendingLargeList.get(513) == 277);
	}

	@Test
	void testSwapValues2() {
		swapValues(descendingLargeList, 11, 989);
		assertTrue(descendingLargeList.get(11) == 11);
		assertTrue(descendingLargeList.get(989) == 989);
	}

	@Test
	void testGetPivotMO3Numbers() {
		descendingSmallList.add(72);
		assertTrue(8 == descendingSmallList.get(getPivotMO3(descendingSmallList, 0, descendingSmallList.size() - 1)));
	}

	@Test
	void testGetPivotMO3Words() {
		sortedWordList.add("testing");
		assertEquals("Narwhal", sortedWordList.get(getPivotMO3(sortedWordList, 0, sortedWordList.size() - 1)));
	}

	@Test
	void testGenerateAscending() {
		assertEquals(ascendingSmallList, generateAscending(8));
	}

	@Test
	void testGenerateAscending2() {
		assertEquals(ascendingLargeList, generateAscending(1000));
	}

	@Test
	void testGenerateDescending() {
		assertEquals(descendingSmallList, generateDescending(8));
	}

	@Test
	void testGenerateDescending2() {
		assertEquals(descendingLargeList, generateDescending(1000));
	}
}
