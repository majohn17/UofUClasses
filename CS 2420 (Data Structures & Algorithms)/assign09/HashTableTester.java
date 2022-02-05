package assign09;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This class tests the HashTable class for its methods.
 * 
 * @author Isaac Gibson and Matthew Johnsen
 * @version March 25, 2019
 */
class HashTableTester {
	private HashTable<String, Integer> intTable;

	@BeforeEach
	void setup() throws Exception {
		intTable = new HashTable<String, Integer>();
		intTable.put("Dog", 123);
		intTable.put("Cat", 123);
		intTable.put("Bird", 123);
		intTable.put("Wolf", 124);
	}

	@Test
	void testClear() {
		assertTrue(intTable.size() == 4);
		intTable.clear();
		assertTrue(intTable.isEmpty());
		assertTrue(intTable.get("Dog") == null);
		intTable.put("Dog", 35);
		assertTrue(intTable.size() == 1);
		assertTrue(intTable.get("Dog") == 35);
	}

	@Test
	void testContainsKey() {
		assertTrue(intTable.containsKey("Dog"));
		assertFalse(intTable.containsKey("dog"));
		assertTrue(intTable.containsKey("Cat"));
		assertFalse(intTable.containsKey("cat"));
		assertTrue(intTable.containsKey("Bird"));
		assertFalse(intTable.containsKey("bird"));
		assertTrue(intTable.containsKey("Wolf"));
		assertFalse(intTable.containsKey("wolf"));
		intTable.put("wolf", 1234);
		assertTrue(intTable.containsKey("wolf"));
	}

	@Test
	void testContainsValue() {
		assertTrue(intTable.containsValue(123));
		assertFalse(intTable.containsValue(125));
		assertTrue(intTable.containsValue(124));
		intTable.put("Panda", 125);
		assertTrue(intTable.containsValue(125));
	}

	@Test
	void testEntries() {
		LinkedList<MapEntry<String, Integer>> list = new LinkedList<>();
		list.add(new MapEntry<>("Dog", 123));
		list.add(new MapEntry<>("Wolf", 124));
		list.add(new MapEntry<>("Cat", 123));
		list.add(new MapEntry<>("Bird", 123));
		assertTrue(list.containsAll(intTable.entries()));
	}

	@Test
	void testGet() {
		assertTrue(intTable.get("Dog") == 123);
		assertNull(intTable.get("dog"));
		intTable.put("dog", 1234);
		assertTrue(intTable.get("dog") == 1234);
		assertEquals(123, intTable.get("Dog").intValue());
		assertEquals(123, intTable.get("Cat").intValue());
		assertEquals(123, intTable.get("Bird").intValue());
		assertEquals(124, intTable.get("Wolf").intValue());
		assertNull(intTable.get("Elephant"));
		assertNull(intTable.get("Puppy"));
	}

	@Test
	void testPut() {
		assertEquals(124, intTable.put("Wolf", 777).intValue());
		assertEquals(123, intTable.put("Cat", 876).intValue());
		assertNull(intTable.put("Lion", 876));
		assertEquals(876, intTable.put("Lion", 333).intValue());

		for (int i = 0; i < 4000; i++) {
			intTable.put("" + i, i + 321);

		}
		assertTrue(4005 == intTable.size());
		assertEquals(800, intTable.getCapacity());
	}

	@Test
	void testRemove() {
		assertNull(intTable.remove("Truck"));
		assertNull(intTable.remove("Car"));
		assertNull(intTable.remove("Limo"));
		assertNull(intTable.remove("Model-T"));
		assertEquals(123, intTable.remove("Dog").intValue());
		assertEquals(123, intTable.remove("Cat").intValue());
		assertEquals(123, intTable.remove("Bird").intValue());
	}

	@Test
	void testSize() {
		assertTrue(4 == intTable.size());
		assertNull(intTable.put("Lion", 876));
		assertTrue(5 == intTable.size());
	}

	@Test
	void testIsEmpty() {
		assertTrue(new HashTable<String, Integer>().isEmpty());
		assertFalse(intTable.isEmpty());
	}

}
