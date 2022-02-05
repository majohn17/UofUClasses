package assign08;

import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * A class that is a BinarySearchTree with some methods.
 * 
 * @author Isaac Gibson and Matthew Johnsen
 * @version March 18, 2019
 */
public class BinarySearchTree<Type extends Comparable<? super Type>> implements SortedSet<Type> {

	private BinaryNode<Type> root = null;

	private int size = 0;

	@Override
	public boolean add(Type item) {
		if (root == null) {
			root = new BinaryNode<Type>(item);
			size++;
			return true;
		}
		return add(item, root);
	}

	private boolean add(Type item, BinaryNode<Type> node) {
		if (item.compareTo(node.element) == 0) {
			return false;
		} else if (item.compareTo(node.element) < 0) {
			if (node.left == null) {
				node.left = new BinaryNode<Type>(item);
				size++;
				return true;
			} else {
				return add(item, node.left);
			}
		} else {
			if (node.right == null) {
				node.right = new BinaryNode<Type>(item);
				size++;
				return true;
			} else {
				return add(item, node.right);
			}
		}
	}

	@Override
	public boolean addAll(Collection<? extends Type> items) {
		boolean changed = false;
		for (Type item : items) {
			if (add(item)) {
				changed = true;
			}
		}
		return changed;
	}

	@Override
	public void clear() {
		root = null;
		size = 0;
	}

	@Override
	public boolean contains(Type item) {
		if (isEmpty()) {
			return false;
		}
		return contains(item, root);
	}

	private boolean contains(Type item, BinaryNode<Type> node) {
		if (item.compareTo(node.element) == 0) {
			return true;
		} else if (item.compareTo(node.element) < 0) {
			if (node.left == null) {
				return false;
			} else {
				return contains(item, node.left);
			}
		} else {
			if (node.right == null) {
				return false;
			} else {
				return contains(item, node.right);
			}
		}
	}

	@Override
	public boolean containsAll(Collection<? extends Type> items) {
		for (Type item : items) {
			if (!contains(item)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Type first() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException();
		} else {
			BinaryNode<Type> node = root;
			while (node.left != null) {
				node = node.left;
			}
			return node.element;
		}
	}

	@Override
	public boolean isEmpty() {
		return size == 0;
	}

	@Override
	public Type last() throws NoSuchElementException {
		if (isEmpty()) {
			throw new NoSuchElementException();
		} else {
			BinaryNode<Type> node = root;
			while (node.right != null) {
				node = node.right;
			}
			return node.element;
		}
	}

	@Override
	public boolean remove(Type item) {
		if (isEmpty()) {
			return false;
		}
		return remove(item, root, null);
	}

	private boolean remove(Type item, BinaryNode<Type> node, BinaryNode<Type> parent) {
		if (item.compareTo(node.element) == 0) {
			removeNode(node, parent);
			size--;
			return true;
		} else if (item.compareTo(node.element) < 0) {
			if (node.left == null) {
				return false;
			} else {
				return remove(item, node.left, node);
			}
		} else {
			if (node.right == null) {
				return false;
			} else {
				return remove(item, node.right, node);
			}
		}
	}

	private void removeNode(BinaryNode<Type> node, BinaryNode<Type> parent) {
		if (node.left == null && node.right == null) { // If the node is a leaf
			removeLeaf(node, parent);
		} else if (node.left == null || node.right == null) { // If the node has 1 child
			removeOneChild(node, parent);
		} else { // If the node has 2 children
			BinaryNode<Type> temp = node.right;
			BinaryNode<Type> tempParent = node;
			while (temp.left != null) {
				tempParent = temp;
				temp = temp.left;
			}
			node.element = temp.element;
			if (temp.right == null) {
				removeLeaf(temp, tempParent);
			} else {
				removeOneChild(temp, tempParent);
			}
		}
	}

	/**
	 * Shifts nodes if node is a leaf
	 */
	private void removeLeaf(BinaryNode<Type> node, BinaryNode<Type> parent) {
		if (parent == null) {
			clear();
		} else {
			if (parent.left == node) {
				parent.left = null;
			} else {
				parent.right = null;
			}
		}
	}

	/**
	 * Shifts nodes if one child is null
	 */
	private void removeOneChild(BinaryNode<Type> node, BinaryNode<Type> parent) {
		if (node.left == null) {
			if (parent == null) {
				root = node.right;
			} else {
				if (parent.left == node) {
					parent.left = node.right;
				} else {
					parent.right = node.right;
				}
			}
		} else if (node.right == null) {
			if (parent == null) {
				root = node.left;
			} else {
				if (parent.left == node) {
					parent.left = node.left;
				} else {
					parent.right = node.left;
				}
			}
		}
	}

	@Override
	public boolean removeAll(Collection<? extends Type> items) {
		boolean changed = false;
		for (Type item : items) {
			if (remove(item)) {
				changed = true;
			}
		}
		return changed;
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public ArrayList<Type> toArrayList() {
		ArrayList<Type> temp = new ArrayList<Type>();
		if (isEmpty()) {
			return temp;
		}
		addInorder(root, temp);
		return temp;
	}

	private void addInorder(BinaryNode<Type> node, ArrayList<Type> list) {
		if (node.left != null) {
			addInorder(node.left, list);
		}
		list.add(node.element);
		if (node.right != null) {
			addInorder(node.right, list);
		}
	}

	/**
	 * @return a string containing all of the edges in the tree rooted at "this"
	 *         node, in DOT format
	 */
	public String generateDot() {
		return root.generateDot();
	}

	private class BinaryNode<T> {

		public T element;

		public BinaryNode<T> left;

		public BinaryNode<T> right;

		/**
		 * Creates a binary node with left and right nodes as children.
		 */
		public BinaryNode(T element, BinaryNode<T> left, BinaryNode<T> right) {
			this.element = element;
			this.left = left;
			this.right = right;
		}

		/**
		 * Creates a binary node with no children.
		 */
		public BinaryNode(T element) {
			this(element, null, null);
		}

		/**
		 * @return a string containing all of the edges in the tree rooted at "this"
		 *         node, in DOT format
		 */
		public String generateDot() {
			String ret = "";

			if (left != null)
				ret += element + " -> " + left.element + "\n" + left.generateDot();

			if (right != null)
				ret += element + " -> " + right.element + "\n" + right.generateDot();

			return ret;
		}
	}
}
