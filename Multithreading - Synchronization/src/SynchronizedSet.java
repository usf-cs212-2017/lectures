import java.util.Collection;
import java.util.Set;
import java.util.SortedSet;

/**
 * A thread-safe version of {@link IndexedSet} using the synchronized keyword.
 *
 * @param <E>
 *            element type
 * @see IndexedSet
 */
public class SynchronizedSet<E> extends IndexedSet<E> {

	public SynchronizedSet() {
		this(false);
	}

	public SynchronizedSet(boolean sorted) {
		super(sorted);
	}

	@Override
	public synchronized boolean add(E element) {
		return super.add(element);
	}

	@Override
	public synchronized boolean addAll(Collection<E> elements) {
		return super.addAll(elements);
	}

	@Override
	public synchronized int size() {
		return super.size();
	}

	@Override
	public synchronized boolean contains(E element) {
		return super.contains(element);
	}

	@Override
	public synchronized E get(int index) {
		return super.get(index);
	}

	@Override
	public synchronized String toString() {
		return super.toString();
	}

	@Override
	public synchronized Set<E> unsortedCopy() {
		return super.unsortedCopy();
	}

	@Override
	public synchronized SortedSet<E> sortedCopy() {
		return super.sortedCopy();
	}
}
