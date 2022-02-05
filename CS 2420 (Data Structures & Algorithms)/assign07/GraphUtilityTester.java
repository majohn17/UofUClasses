package assign07;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import static assign07.GraphUtility.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Provides testing methods for the Graph class
 * 
 * @author Isaac Gibson and Matthew Johnsen
 * @version March 4, 2019
 */
class GraphUtilityTester {

	ArrayList<Integer> sourceCycle = new ArrayList<Integer>();
	ArrayList<Integer> goToCycle = new ArrayList<Integer>();
	ArrayList<Integer> source = new ArrayList<Integer>();
	ArrayList<Integer> goTo = new ArrayList<Integer>();

	@BeforeEach
	void setup() throws Exception {
		// Cyclic graph creation
		addEdgeCycle(1, 2);
		addEdgeCycle(2, 3);
		addEdgeCycle(2, 7);
		addEdgeCycle(1, 6);
		addEdgeCycle(4, 5);
		addEdgeCycle(3, 4);
		addEdgeCycle(5, 3);
		addEdgeCycle(7, 5);
		addEdgeCycle(6, 7);
		// acyclic graph creation
		addEdge(1, 2);
		addEdge(2, 3);
		addEdge(2, 7);
		addEdge(1, 6);
		addEdge(4, 5);
		addEdge(3, 4);
		addEdge(7, 5);
		addEdge(6, 7);
	}

	private void addEdgeCycle(int from, int to)

	{
		sourceCycle.add(from);
		goToCycle.add(to);
	}

	private void addEdge(int from, int to)

	{
		source.add(from);
		goTo.add(to);
	}

	@Test
	void testIsCyclic() {
		assertTrue(isCyclic(sourceCycle, goToCycle));
		assertFalse(isCyclic(source, goTo));
		assertThrows(IllegalArgumentException.class, () -> isCyclic(source, goToCycle));
	}

	@Test
	void testIsCyclicException() {
		ArrayList<Integer> test1 = new ArrayList<Integer>();
		ArrayList<Integer> test2 = new ArrayList<Integer>();
		test1.add(1);
		test2.add(2);
		test2.add(3);
		assertThrows(IllegalArgumentException.class, () -> isCyclic(test1, test2));
		test1.add(4);
		test1.add(5);
		assertThrows(IllegalArgumentException.class, () -> isCyclic(test1, test2));
		test2.add(6);
		assertFalse(isCyclic(test1, test2));
	}

	@Test
	void testAreConnected() {
		assertTrue(areConnected(sourceCycle, goToCycle, 1, 5));
		assertTrue(areConnected(sourceCycle, goToCycle, 1, 2));
		assertTrue(areConnected(sourceCycle, goToCycle, 1, 3));
		assertTrue(areConnected(sourceCycle, goToCycle, 2, 4));
		assertTrue(areConnected(sourceCycle, goToCycle, 6, 3));
		assertFalse(areConnected(sourceCycle, goToCycle, 2, 6));
		assertFalse(areConnected(sourceCycle, goToCycle, 3, 1));
		assertFalse(areConnected(sourceCycle, goToCycle, 5, 1));
		assertFalse(areConnected(sourceCycle, goToCycle, 2, 1));
		assertThrows(IllegalArgumentException.class, () -> areConnected(source, goToCycle, 17, 2));
		assertThrows(IllegalArgumentException.class, () -> areConnected(source, goTo, 1, 17));
	}

	@Test
	void testAreConnectedException() {
		ArrayList<Integer> test1 = new ArrayList<Integer>();
		ArrayList<Integer> test2 = new ArrayList<Integer>();
		test1.add(1);
		test2.add(2);
		test2.add(3);
		assertThrows(IllegalArgumentException.class, () -> areConnected(test1, test2, 1, 2));
		test1.add(4);
		test1.add(5);
		assertThrows(IllegalArgumentException.class, () -> areConnected(test1, test2, 1, 2));
		test2.add(6);
		assertTrue(areConnected(test1, test2, 1, 2));
	}

	@Test
	void testSort() {
		String result = "";
		List<Integer> l = sort(source, goTo);
		for (int i = 0; i < l.size(); i++) {
			result += l.get(i) + " ";
		}
		assertEquals("1 2 6 3 7 4 5 ", result);
		Graph<String> test = new Graph<String>();
		test.addEdge("test", "test");
		assertThrows(IllegalArgumentException.class, () -> test.sort());
	}

	@Test
	void testSortException() {
		ArrayList<Integer> test1 = new ArrayList<Integer>();
		ArrayList<Integer> test2 = new ArrayList<Integer>();
		test1.add(1);
		test2.add(2);
		test2.add(3);
		assertThrows(IllegalArgumentException.class, () -> sort(test1, test2));
		test1.add(4);
		test1.add(5);
		assertThrows(IllegalArgumentException.class, () -> sort(test1, test2));
		test2.add(6);
		List<Integer> l = sort(test1, test2);
		String result = "";
		for (int i = 0; i < l.size(); i++) {
			result += l.get(i) + " ";
		}
		assertEquals("1 4 5 2 3 6 ", result);
	}
}
