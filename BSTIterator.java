import java.lang.Comparable;
import java.util.Iterator;
import java.util.Stack;
import java.io.Serializable;

/**
* BSTIterator is a generic preorder iterator class used to store and move nodes within a given 
* binary search tree.
* @author JTobias
*/
public class BSTIterator<E extends Comparable<E>> implements Iterator<E>, Serializable {

	Node<E> root = null;
	Stack<Node<E>> visit = new Stack<Node<E>>();

	/**
	* Iterator constructor
	*/
	public BSTIterator(Node<E> root) {
		this.root = root;
		visit = new Stack<Node<E>>(); // create a new node stack
	}

	public boolean hasNext() {
		return (root != null);
	}

	/**
	* Return node at top of stack and push remaining nodes, if any.
	*/
	public E next() {
		if (visit.empty()) {
			visit.push(root);
		}

		Node<E> node = visit.pop();
		E result = node.value;
		
		if (node.right != null) {
			visit.push(node.right);
		}

		if (node.left != null) {
			visit.push(node.left);
		}

		if (visit.empty()) {
			root = null;
		}

		return result;
	}

	public void remove() { return; }
}
