import java.lang.Comparable;
import java.util.Comparator;

/**
* NaturalComparator is a wrapper class for T's compareTo --
* @author EWenderholm
*/
public class NaturalComparator<T extends Comparable<T>> implements Comparator<T> {

	/**
	* uses T's compareTo method to determine the ordering
	*/
	public int compare(T a, T b) {
		return a.compareTo(b);
	}

	public boolean equals(Object obj) {
		return (obj != null) && (obj instanceof NaturalComparator);
	}
}
