import java.lang.Comparable;
import java.lang.Iterable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.io.Serializable;

/**
* BST is a collection class that takes values and arranges them into a binary search tree.
* @author JTobias
*/
public class BST<E extends Comparable<E>> implements Iterable<E>, Serializable {
	Node<E> root;
	Comparator<? super E> ordering;
	int size = 0;

	/**
	* Default BST constructor
	*/
	public BST() {
		root = null;
		ordering = new NaturalComparator<E>();
	}

	/**
	* Create BST using generics
	*/
	public BST(Comparator<? super E> o) {
		ordering = o;
	}

	/**
	* Attempts to add an item to the BST.
	* Checks first to see if item already exists in the BST.
	* If successful adds item to tree and increments size counter.
	* @param item - value to be added
	*/
	public void add(E item) {
		//System.out.println("comparison value for add: " + item.toString());
		if (exists(item)) { // if node already exists, leave
			//System.out.println(item.toString() + " already exists...");
			return;
		} else {
			root = add(item, root);
			size++;
		}
	}

	/*
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
			if (ordering.compare(item, node.value) < 0) {
				node.left = add(item, node.left); // add to left subtree
			} else {
				node.right = add(item, node.right); // add to right subtree
			}
		}
		return node;
	}

	/**
	* Attempts to remove an item to the BST.
	* Checks first to see if item already exists in the BST.
	* If successful, removes item to tree and decrements size counter.
	* @param item - value to be removed
	*/
	public void remove(E item) {
		//System.out.println("value to be removed: " + item.toString());		
		//if (!exists(item)) {
			//System.out.println(item.toString() + " does not exist...");
			//return;
		//} else {
			root = remove(item, root);
			size--;
		//}
	}

	/*
	* Actual node removal method. Removes a given node and it's value, and adjusts the BST
	* accordingly.
	*/
	protected Node<E> remove(E item, Node<E> node) {
		if (ordering.compare(item, node.value) == 0) { // node to replace

			/* 
			* if left child is empty, replace with the rightchild
			* else if the right child is empty replace with left child
			*/
			if (node.left == null) {
				return node.right;
			} else if (node.right == null) {
				return node.left;
			} else {
				// replace value with value of rightmost node of left subtree
				node.value = getRightmost(node.left);
				// clear rightmost node of left subtree
				node.left = remove(node.value, node.left);
			}
		} else {
			if (ordering.compare(item, node.value) < 0) {
				node.left = remove(item, node.left); // remove from left subtree
			} else {
				node.right = remove(item, node.right); // remove from right subtree
			}
		}
		return node;
	}

	protected E getRightmost(Node<E> node) {
		Node<E> right = node.right;
		if (right == null) {
			return node.value;
		} else {
			return getRightmost(right);
		}
	}

	/**
	* Checks to see if a given value already exists in the tree. Returns true if exists
	* otherwise returns false.
	* @param item - value to be checked
	* @return true is item exists, false otherwise
	*/
	public boolean exists(E item) {
		if (get(item) != null) {
			return true;
		}
		return false;
	}

	/**
	* Attempts to get a specific value within the BST.
	* @param item - value to be found
	* @return node value if node exists or null otherwise
	*/
	public E get(E item) {
		Node<E> node = root;
		while (node != null) {
			if (ordering.compare(item, node.value) == 0) {
				return node.value;
			}

			if (ordering.compare(item, node.value) < 0) {
				node = node.left;
			} else {
				node = node.right;
			}
		}
		return null;
	}

	/**
	* Gives a new preorder traversal iterator
	*/
	public Iterator<E> iterator() {
		return (Iterator<E>) new BSTIterator<E>(root);
	}

	/**
	* Gets the height of the tree
	*/
	public int treeHeight(Node<E> node) {
		if (isEmpty()) {
			return -1;
		}  else {
			return 1 + max(treeHeight(node.left), treeHeight(node.right));
		}
	}

	protected int max(int a, int b) {
		if (a > b) {
			return a;
		} else {
			return b;
		}
	}

	/**
	* Gets the root of the BST.
	*/
	public Node<E> getRoot() {
		return root;
	}

	/**
	* Get the size of the BST.
	*/
	public int getSize() {
		return size;
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

	/*
	* Used for debugging.
	* Returns string value of a node.

    public String toString() {
		return toString(root);
    }

    public String toString(Node<E> node) {
		if (node == null) {
		    return "";
		}
		return node.value.toString() + "(" + toString(node.left) + ", " +
	    	toString(node.right) + ")";
    }
    */
}
