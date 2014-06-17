import java.lang.Comparable;
import java.io.Serializable;

/**
* Generic node class
*/
public class Node<E extends Comparable<E>> implements Serializable {
		Node<E> left;
		Node<E> right;
		E value;

		public Node() {
			left = null;
			right = null;
			value = null;
		}

		public Node(E v) {
			this();
			value = v;
		}

		public Node(Node<E> l, Node<E> r, E v) {
			left = l;
			right = r;
			value = v;
		}
	}
