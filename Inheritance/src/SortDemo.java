import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.TreeSet;

/**
 * This class demonstrates the {@link Comparable} and {@link Comparator}
 * interfaces, nested classes, Generic syntax, and how to use these elements to
 * change how a list of objects is sorted.
 */
public class SortDemo {

	/**
	 * A nested class that knows how to compare against other objects of this
	 * class by implementing the {@link Comparable} interface. Since this class
	 * does not depend on any {@link SortDemo} instance members, we can make
	 * this class {@code static}.
	 */
	private static class Widget implements Comparable<Widget> {
		public int id;
		public String name;

		public Widget(int id, String name) {
			this.id = id;
			this.name = name;
		}

		/**
		 * By default, {@link #Widget} objects will be sorted by the widget id.
		 */
		@Override
		public int compareTo(Widget other) {
			return Integer.compare(this.id, other.id);
		}
	}

	/**
	 * This class {@link Comparator} to change how {@link #Widget} objects are
	 * sorted.
	 */
	private static class WidgetComparator implements Comparator<Widget> {
		/**
		 * Will sort {@link Widget} objects by the length of the name instead of
		 * the id.
		 */
		@Override
		public int compare(Widget w1, Widget w2) {
			int length1 = w1.name.length();
			int length2 = w2.name.length();

			// Use Integer.compare(length2, length1) to swap order.
			return Integer.compare(length1, length2);
		}
	}

	/**
	 * Able to print to console any {@link Collection} of {@link #Widget}
	 * objects. Used to debug.
	 *
	 * @param c
	 *            the collection to be printed to the console
	 */
	public static void printCollection(Collection<Widget> c) {
		for (Widget w : c) {
			System.out.printf("id: %2d name: %s\n", w.id, w.name);
		}
	}

	/**
	 * Demonstrates how to sort the {@link ArrayList} of {@link #Widget} objects
	 * using the {@link Comparable} and {@link Comparator} interfaces and the
	 * {@link Collections#sort(java.util.List)} methods.
	 *
	 * @param args
	 *            unused
	 *
	 * @see {@link Comparable}
	 * @see {@link Comparator}
	 * @see {@link Collections#sort(java.util.List)}
	 * @see {@link Collections#sort(java.util.List, Comparator)}
	 */
	public static void main(String[] args) {
		ArrayList<Widget> list = new ArrayList<Widget>();
		list.add(new Widget(10, "ant"));
		list.add(new Widget(7, "hippopotamus"));
		list.add(new Widget(14, "dragonfly"));
		list.add(new Widget(3, "camel"));

		// unsorted
		System.out.println("ArrayList, Unsorted:");
		printCollection(list);

		// sorted by name length
		System.out.println("\nArrayList, Sorted by ID:");
		Collections.sort(list);
		printCollection(list);

		// sorted by name length
		System.out.println("\nArrayList, Sorted by Name Length:");
		Collections.sort(list, new WidgetComparator());
		printCollection(list);

		// sorted by name
		System.out.println("\nArrayList, Sorted by Name");
		Collections.sort(list, new Comparator<Widget>() {
			@Override
			public int compare(Widget w1, Widget w2) {
				return String.CASE_INSENSITIVE_ORDER.compare(w1.name, w2.name);
			}
		});
		printCollection(list);

		// sorted by id
		System.out.println("\nTreeSet, Sorted by ID:");
		TreeSet<Widget> set1 = new TreeSet<Widget>();
		set1.addAll(list);
		printCollection(set1);

		// sorted by name length
		System.out.println("\nTreeSet, Sorted by Name Length:");
		TreeSet<Widget> set2 = new TreeSet<Widget>(new WidgetComparator());
		set2.addAll(list);
		printCollection(set2);
	}
}
