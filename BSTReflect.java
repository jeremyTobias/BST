import java.lang.Comparable;
import java.lang.Iterable;
import java.util.Comparator;
import java.util.Iterator;
import java.io.Serializable;

/**
* BSTReflect takes the values from the given binary search tree and switches the right nodes with the
* left nodes and vice versa
* @author JTobias
*/
public class BSTReflect<E extends Comparable<E>> implements Iterable<E>, Serializable {
	Node<E> root;
	Comparator<? super E> ordering;
	int size = 0;

	/**
	* Default BST constructor
	*/
	public BSTReflect() {
		root = null;
		ordering = new NaturalComparator<E>();
	}

	/**
	* Create BST using generics
	*/
	public BSTReflect(Comparator<? super E> o) {
		ordering = o;
	}

	/**
	* Attempts to add an item to the BSTReflector.
	* @param item - value to be added
	*/
	public void add(E item) {
			root = add(item, root);
			size++;
	}

	/*connectLeft
	* Actual node addition method. Checks to see if a given value is equal to, less than, or
	* greater than exising nodes in order to place the value in the correct node.
	*/
	protected Node<E> add(E item, Node<E> node) {
		if (node == null) {
			return new Node<E>(item);
		}

		if (ordering.compare(item, node.value) == 0) {
			node.value = item; // replace current node value with item
		} else {
			// where the actual reflection takes place
			if (ordering.compare(item, node.value) < 0) {
				node.right = add(item, node.right); // add to right subtree
			} else {
				node.left = add(item, node.left); // add to left subtree
			}
		}
		return node;
	}

	/**
	* Gives a new preorder traversal iterator
	*/
	public Iterator<E> iterator() {
		return (Iterator<E>) new BSTIterator<E>(root);
	}

	/**
	* Gets the root of the BST.
	*/
	public Node<E> getRoot() {
		return root;
	}

	/**
	* Returns true if BST is empty.
	* False otherwise.
	*/
	public boolean isEmpty() {
		return root == null;
	}

	/**
	* Removes all values from the current BST and storage arraylist and resets the size to 0.
	*/
	public void clear() {
		root = null;
		size = 0;
	}
}
