package assign07;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Provides testing methods for the Graph class
 * 
 * @author Isaac Gibson and Matthew Johnsen
 * @version March 4, 2019
 */
class GraphTester {

	private Graph<Integer> intGraph;
	private Graph<String> stringGraph, testGraph;

	@BeforeEach
	void setup() throws Exception {
		intGraph = new Graph<Integer>();
		intGraph.addEdge(1, 2);
		intGraph.addEdge(1, 3);
		intGraph.addEdge(2, 1);
		intGraph.addEdge(3, 4);

		stringGraph = new Graph<String>();
		stringGraph.addEdge("test1", "test2");
		stringGraph.addEdge("test1", "test3");
		stringGraph.addEdge("test3", "test4");
		stringGraph.addEdge("test3", "test5");

		testGraph = new Graph<String>();
		testGraph.addEdge("v0", "v1");
		testGraph.addEdge("v0", "v2");
		testGraph.addEdge("v0", "v3");
		testGraph.addEdge("v1", "v3");
		testGraph.addEdge("v1", "v4");
		testGraph.addEdge("v2", "v5");
		testGraph.addEdge("v3", "v4");
		testGraph.addEdge("v3", "v2");
		testGraph.addEdge("v3", "v5");
		testGraph.addEdge("v3", "v6");
		testGraph.addEdge("v4", "v6");
		testGraph.addEdge("v6", "v5");
	}

	@Test
	void testIsCyclic() {
		assertTrue(intGraph.isCyclic());
		assertFalse(stringGraph.isCyclic());
	}

	@Test
	void testIsCyclicEmptyGraph() {
		Graph<Double> test = new Graph<Double>();
		assertFalse(test.isCyclic());
		test.addEdge(1.1, 2.0);
		assertFalse(test.isCyclic());
		test.addEdge(2.0, 1.1);
		assertTrue(test.isCyclic());
		test = new Graph<Double>();
		assertFalse(test.isCyclic());
	}

	@Test
	void testAreConnected() {
		resetIntGraph();
		assertTrue(intGraph.areConnected(1, 1));
		resetIntGraph();
		assertTrue(intGraph.areConnected(1, 2));
		resetIntGraph();
		assertTrue(intGraph.areConnected(1, 3));
		resetIntGraph();
		assertTrue(intGraph.areConnected(1, 4));
		resetIntGraph();
		assertTrue(intGraph.areConnected(2, 1));
		resetIntGraph();
		assertTrue(intGraph.areConnected(2, 2));
		resetIntGraph();
		assertTrue(intGraph.areConnected(2, 3));
		resetIntGraph();
		assertTrue(intGraph.areConnected(2, 4));
		resetIntGraph();
		assertFalse(intGraph.areConnected(3, 1));
		resetIntGraph();
		assertFalse(intGraph.areConnected(3, 2));
		resetIntGraph();
		assertFalse(intGraph.areConnected(3, 3));
		resetIntGraph();
		assertTrue(intGraph.areConnected(3, 4));
		resetIntGraph();
		assertFalse(intGraph.areConnected(4, 1));
		resetIntGraph();
		assertFalse(intGraph.areConnected(4, 2));
		resetIntGraph();
		assertFalse(intGraph.areConnected(4, 3));
		resetIntGraph();
		assertFalse(intGraph.areConnected(4, 4));
		resetIntGraph();
		assertThrows(IllegalArgumentException.class, () -> intGraph.areConnected(1, 19283));
		assertThrows(IllegalArgumentException.class, () -> intGraph.areConnected(19283, 1));
	}

	@Test
	void testAreConnectedEmptyGraph() {
		Graph<String> test = new Graph<String>();
		assertThrows(IllegalArgumentException.class, () -> test.areConnected("1", "2"));
		test.addEdge("1", "2");
		assertTrue(test.areConnected("1", "2"));
	}

	@Test
	void testSort() {
		String result = "";
		List<String> l = testGraph.sort();
		for (int i = 0; i < l.size(); i++) {
			result += l.get(i) + " ";
		}
		assertEquals("v0 v1 v3 v4 v2 v6 v5 ", result);
		Graph<Double> test = new Graph<Double>();
		test.addEdge(2.1, 2.1);
		assertThrows(IllegalArgumentException.class, () -> test.sort());
	}

	private void resetIntGraph() {
		intGraph = new Graph<Integer>();
		intGraph.addEdge(1, 2);
		intGraph.addEdge(1, 3);
		intGraph.addEdge(2, 1);
		intGraph.addEdge(3, 4);
	}
}
