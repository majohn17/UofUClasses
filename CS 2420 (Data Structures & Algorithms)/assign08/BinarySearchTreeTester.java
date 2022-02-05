package assign08;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BinarySearchTreeTester {

	private BinarySearchTree<Integer> intTree;
	private ArrayList<Integer> intList;

	@BeforeEach
	void setup() throws Exception {
		intTree = new BinarySearchTree<Integer>();
		intTree.add(7);
		intTree.add(6);
		intTree.add(8);
		intTree.add(1);
		intTree.add(3);
		intTree.add(2);
		intTree.add(4);
		intList = new ArrayList<Integer>();
		intList.add(7);
		intList.add(6);
		intList.add(8);
		intList.add(1);
		intList.add(3);
		intList.add(2);
		intList.add(4);
	}

	@Test
	void testAddStuff() {
		intTree = new BinarySearchTree<Integer>();
		intTree.addAll(intList);
		assertTrue(intTree.size() == 7);
	}

	@Test
	void testContains() {
		assertFalse(intTree.contains(12314));
		assertTrue(intTree.contains(4));
	}

	@Test
	void testContainsAll() {
		assertTrue(intTree.containsAll(intList));
		intList.add(3214234);
		assertFalse(intTree.containsAll(intList));
	}

	@Test
	void testFirst() {
		assertTrue(intTree.first() == 1);
		intTree.add(-1234);
		assertTrue(intTree.first() == -1234);
		assertFalse(intTree.first() == 8);
		assertThrows(NoSuchElementException.class, () -> new BinarySearchTree<Integer>().first());
	}

	@Test
	void testLast() {
		assertTrue(intTree.last() == 8);
		intTree.add(1234);
		assertTrue(intTree.last() == 1234);
		assertFalse(intTree.last() == 8);
		assertThrows(NoSuchElementException.class, () -> new BinarySearchTree<Integer>().last());
	}

	@Test
	void testRemove() {
		System.out.print(intTree.generateDot());
		intTree.remove(6);
		System.out.print(intTree.generateDot());
	}
}
