package assign09;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a map of keys to values. It cannot contain duplicate
 * keys, and each key can map to at most one value.
 * 
 * @author Isaac Gibson and Matthew Johnsen
 * @version March 25, 2019
 */
public class HashTable<K, V> implements Map<K, V> {
	private ArrayList<LinkedList<MapEntry<K, V>>> table;
	private int size;
	private int capacity;
	private int collisions; // Used for testing

	public HashTable() {
		capacity = 100;
		table = new ArrayList<LinkedList<MapEntry<K, V>>>();
		for (int i = 0; i < capacity; i++)
			table.add(new LinkedList<MapEntry<K, V>>());
	}

	@Override
	public void clear() {
		for (int i = 0; i < table.size(); i++) {
			table.get(i).clear();
		}
		size = 0;
	}

	@Override
	public boolean containsKey(K key) {
		for (MapEntry<K, V> map : table.get(getHashIndex(key))) {
			if (map.getKey().equals(key)) {
				return true;
			} else {
				collisions++;
			}
		}
		return false;
	}

	@Override
	public boolean containsValue(V value) {
		for (LinkedList<MapEntry<K, V>> list : table) {
			for (MapEntry<K, V> map : list) {
				if (map.getValue().equals(value)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public List<MapEntry<K, V>> entries() {
		LinkedList<MapEntry<K, V>> maps = new LinkedList<>();
		for (LinkedList<MapEntry<K, V>> list : table) {
			maps.addAll(list);
		}
		return maps;
	}

	@Override
	public V get(K key) {
		for (MapEntry<K, V> map : table.get(getHashIndex(key))) {
			if (map.getKey().equals(key)) {
				return map.getValue();
			}
		}
		return null;
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public V put(K key, V value) {
		if (containsKey(key)) {
			V item;
			for (MapEntry<K, V> map : table.get(getHashIndex(key))) {
				if (map.getKey().equals(key)) {
					item = map.getValue();
					map.setValue(value);
					return item;
				} else {
					collisions++;
				}
			}
		} else {
			table.get(getHashIndex(key)).add(new MapEntry<K, V>(key, value));
			size++;
			if (loadFactor() > 10) {
				reHash();
			}
		}
		return null;
	}

	@Override
	public V remove(K key) {
		LinkedList<MapEntry<K, V>> list = table.get(getHashIndex(key));
		if (containsKey(key)) {
			V item;
			for (MapEntry<K, V> map : list) {
				if (map.getKey().equals(key)) {
					item = map.getValue();
					list.remove(map);
					size--;
					return item;
				}
			}
		}
		return null;
	}

	public void reHash() {

		capacity = capacity * 2;
		ArrayList<LinkedList<MapEntry<K, V>>> temp = new ArrayList<LinkedList<MapEntry<K, V>>>();
		for (int i = 0; i < capacity; i++)
			temp.add(new LinkedList<MapEntry<K, V>>());
		for (LinkedList<MapEntry<K, V>> list : table) {
			for (MapEntry<K, V> map : list) {
				temp.get(getHashIndex(map.getKey())).add(map);
			}
		}
		table = temp;
	}

	@Override
	public int size() {
		return size;
	}

	private double loadFactor() {
		return size / (1.0 * capacity);
	}

	public int getCapacity() {
		return capacity;
	}

	public int getCollisions() {
		return collisions;
	}

	private int getHashIndex(K key) {
		return Math.abs(key.hashCode() % capacity);
	}
}
