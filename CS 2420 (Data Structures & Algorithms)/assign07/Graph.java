package assign07;

import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a generic, sparse, unweighted, directed graph (a set of vertices
 * and a set of edges).
 * 
 * @author Erin Parker, Isaac Gibson, and Matthew Johnsen
 * @version March 4, 2019
 */
public class Graph<T> {

	// the graph -- a set of vertices (String name mapped to Vertex instance)
	HashMap<String, Vertex<T>> vertices;

	/**
	 * Constructs an empty graph.
	 */
	public Graph() {
		vertices = new HashMap<String, Vertex<T>>();
	}

	/**
	 * Adds to the graph a directed edge from the vertex with name "name1" to the
	 * vertex with name "name2". (If either vertex does not already exist in the
	 * graph, it is added.)
	 * 
	 * @param name1 - string name for source vertex
	 * @param name2 - string name for destination vertex
	 */
	public void addEdge(T name1, T name2) {
		Vertex<T> vertex1;
		// if vertex already exists in graph, get its object
		if (vertices.containsKey(name1.toString()))
			vertex1 = vertices.get(name1.toString());
		// else, create a new object and add to graph
		else {
			vertex1 = new Vertex<T>(name1);
			vertices.put(name1.toString(), vertex1);
		}

		Vertex<T> vertex2;
		if (vertices.containsKey(name2.toString()))
			vertex2 = vertices.get(name2.toString());
		else {
			vertex2 = new Vertex<T>(name2);
			vertices.put(name2.toString(), vertex2);
		}

		// add new directed edge from vertex1 to vertex2
		vertex1.addEdge(vertex2);
	}

	/**
	 * Generates the DOT encoding of this graph as string, which can be pasted into
	 * http://www.webgraphviz.com to produce a visualization.
	 */
	public String generateDot() {
		StringBuilder dot = new StringBuilder("digraph d {\n");

		// for every vertex
		for (Vertex<T> v : vertices.values()) {
			// for every edge
			Iterator<Edge<T>> edges = v.edges();
			while (edges.hasNext())
				dot.append("\t" + v.getName() + " -> " + edges.next() + "\n");

		}

		return dot.toString() + "}";
	}

	/**
	 * Generates a simple textual representation of this graph.
	 */
	public String toString() {
		StringBuilder result = new StringBuilder();

		for (Vertex<T> v : vertices.values())
			result.append(v + "\n");

		return result.toString();
	}

	/**
	 * Determines if the graph has any cycles
	 */
	public boolean isCyclic() {
		resetEdges();
		for (Vertex<T> v : vertices.values()) {
			if (depthFirstSearch(v) == true) {
				return true;
			}
		}
		return false;
	}

	private boolean depthFirstSearch(Vertex<T> v) {
		Iterator<Edge<T>> t = v.edges();
		while (t.hasNext()) {
			Edge<T> e = t.next();
			if (e.isVisited == false) {
				e.setIsVisited(true);
				if (depthFirstSearch(e.getOtherVertex()) == true) {
					return true;
				}
			} else {
				return true;
			}
		}
		Iterator<Edge<T>> t2 = v.edges();
		while (t2.hasNext()) {
			t2.next().setIsVisited(false);
		}
		return false;
	}

	/**
	 * Determines if there is a valid path from the source vertex to the destination
	 * vertex
	 * 
	 * @param src - the data of the source vertex
	 * @param dst - the data of the destination vertex
	 */
	public boolean areConnected(T src, T dst) {
		if (vertices.get(src.toString()) == null || vertices.get(dst.toString()) == null) {
			throw new IllegalArgumentException();
		}
		resetEdges();
		Vertex<T> start = vertices.get(src.toString());
		Deque<Vertex<T>> queue = new LinkedList<Vertex<T>>();
		queue.addLast(start);
		while (!queue.isEmpty()) {
			Vertex<T> x = queue.removeFirst();
			Iterator<Edge<T>> t = x.edges();
			while (t.hasNext()) {
				Edge<T> e = t.next();
				Vertex<T> next = e.getOtherVertex();
				if (e.isVisited == false) {
					if (next.data.toString().equals(dst.toString())) {
						return true;
					}
					e.setIsVisited(true);
					queue.addLast(next);
				}
			}
		}
		return false;
	}

	// Used for timing code only, allows for graph to not be created multiple times
	private void resetEdges() {
		for (Vertex<T> v : vertices.values()) {
			Iterator<Edge<T>> t = v.edges();
			{
				while (t.hasNext()) {
					t.next().setIsVisited(false);
				}
			}
		}
	}

	/**
	 * Sorts the graph topologically and returns a list of the sorted data
	 */
	public List<T> sort() {
		LinkedList<T> sorted = new LinkedList<T>();
		setDegrees();
		Deque<Vertex<T>> queue = new LinkedList<Vertex<T>>();
		int count = 0;
		for (Vertex<T> v : vertices.values()) {
			if (v.degree == 0) {
				queue.addLast(v);
			}
		}
		while (!queue.isEmpty()) {
			Vertex<T> x = queue.removeFirst();
			sorted.addLast(x.data);
			Iterator<Edge<T>> t = x.edges();
			while (t.hasNext()) {
				Edge<T> e = t.next();
				Vertex<T> next = e.getOtherVertex();
				next.setDegree(next.degree - 1);
				if (next.degree == 0) {
					queue.addLast(next);
				}
			}
			count++;
		}
		if (count != vertices.size()) {
			throw new IllegalArgumentException();
		} else {
			return sorted;
		}
	}

	private void setDegrees() {
		for (Vertex<T> v : vertices.values()) {
			Iterator<Edge<T>> t = v.edges();
			while (t.hasNext()) {
				Vertex<T> next = t.next().getOtherVertex();
				next.setDegree(next.degree + 1);
			}
		}
	}

	/**
	 * This class represents a generic vertex (AKA node) in a directed graph.
	 */
	private class Vertex<E> {
		// used to id the Vertex
		private E data;

		// used for a topological sort
		private int degree = 0;

		// adjacency list
		private LinkedList<Edge<E>> adj = new LinkedList<Edge<E>>();

		public Vertex(E data) {
			this.data = data;
		}

		public String getName() {
			return data.toString();
		}

		public void addEdge(Vertex<E> otherVertex) {
			adj.add(new Edge<E>(otherVertex));
		}

		public Iterator<Edge<E>> edges() {
			return adj.iterator();
		}

		public String toString() {
			String s = "Vertex " + data + " adjacent to vertices ";
			Iterator<Edge<E>> itr = adj.iterator();
			while (itr.hasNext())
				s += itr.next() + "  ";
			return s;
		}

		public void setDegree(int degree) {
			this.degree = degree;
		}
	}

	/**
	 * This class represents an edge between a source vertex and a destination
	 * vertex in a directed graph.
	 * 
	 * The source of this edge is the Vertex whose object has an adjacency list
	 * containing this edge.
	 */
	private class Edge<E> {
		// destination of this directed edge
		private Vertex<E> dst;

		// determines if this edge has been visited
		private boolean isVisited = false;

		public Edge(Vertex<E> dst) {
			this.dst = dst;
		}

		public Vertex<E> getOtherVertex() {
			return this.dst;
		}

		public String toString() {
			return this.dst.getName();
		}

		public void setIsVisited(boolean isVisited) {
			this.isVisited = isVisited;
		}
	}
}