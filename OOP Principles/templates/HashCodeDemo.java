import java.util.ArrayList;
import java.util.Arrays;

/**
 * Demonstrates how parameter passing works in Java, including the difference in
 * behavior for immutable versus mutable objects.
 *
 * In particular, mutability is tied to how memory is handled. If new memory is
 * created when you change a value, then the original object is immutable. If
 * the values stored at the original memory location is modified, then it is
 * mutable.
 *
 * This is also related to whether it is "safe" to pass objects, because if it
 * is mutable, other classes can modify its value using that reference!
 */
public class HashCodeDemo {

	/**
	 * Shows the actual hash codes and the String representation of the
	 * specified object.
	 *
	 * Note the usage of System.identityHashCode(object) instead of
	 * Object.hashCode(). This returns the value from the default hashcode
	 * implementation, which gives the memory address of where the object is
	 * currently stored. Most classes override this implementation.
	 *
	 * It is not the focus of this example!
	 *
	 * @param label
	 *            - label to use in output
	 * @param object
	 *            - object to display
	 */
	public static void printHash(String label, Object object) {
		System.out.printf("%-9s : x%08X : %s %n", label, System.identityHashCode(object), object);
	}

	/**
	 * A special version of {@link #printHash(String, Object)} that works for
	 * Object arrays.
	 *
	 * @see #printHash(String, Object)
	 * @param label
	 *            - label to use in output
	 * @param object
	 *            - object array to display
	 */
	public static void printHash(String label, Object[] object) {
		System.out.printf("%-9s : x%08X : %s %n", label, System.identityHashCode(object), Arrays.toString(object));
	}

	/**
	 * Used to demonstrate how the memory location is or is not modified within
	 * a method call for this type of object.
	 *
	 * @param value
	 *            - value passed in from main method
	 */
	public static void testInteger(Integer value) {
		printHash("INNER BEG", value);
		value = 1;
		printHash("INNER END", value);
	}

	/**
	 * Used to demonstrate how the memory location is or is not modified within
	 * a method call for this type of object.
	 *
	 * @param value
	 *            - value passed in from main method
	 */
	public static void testString(String value) {
		printHash("INNER BEG", value);
		value = value.toUpperCase(); // notice the assignment!
		printHash("INNER END", value);
	}

	/**
	 * Used to demonstrate how the memory location is or is not modified within
	 * a method call for this type of object.
	 *
	 * @param value
	 *            - value passed in from main method
	 */
	public static void testBuilder(StringBuilder value) {
		printHash("INNER BEG", value);
		value.append(" banana");
		printHash("INNER END", value);
	}

	/**
	 * Used to demonstrate how the memory location is or is not modified within
	 * a method call for this type of object.
	 *
	 * @param value
	 *            - value passed in from main method
	 */
	public static void testArray(String[] value) {
		printHash("INNER BEG", value);
		value[0] = "carrot";
		printHash("INNER END", value);
	}

	/**
	 * Used to demonstrate how the memory location is or is not modified within
	 * a method call for this type of object.
	 *
	 * @param value
	 *            - value passed in from main method
	 */
	public static void testList(ArrayList<String> value) {
		printHash("INNER BEG", value);
		value.add("banana");
		printHash("INNER BEG", value);
	}

	public static void main(String[] args) {

		/*
		 * Objects in Java may be mutable -OR- immutable. Often, you can find
		 * this information in the API.
		 *
		 * When changing the value of an immutable object, you are actually
		 * creating a new object. The reference to the old object is broken and
		 * updated to the new object. The garbage collector will clean up the
		 * now unused space of the old object.
		 *
		 * Notice how the memory address changes once the Integer value is
		 * modified and reassigned! The original Integer object is not being
		 * modified.
		 */

		Integer a = new Integer(0);
		printHash("OUTER BEG", a);
		testInteger(a);
		printHash("OUTER END", a);
		System.out.println();

		/*
		 * Immutable objects with the same value might as well share the same
		 * memory location. This allows Java to optimize its memory usage. That
		 * is also why you should avoid calling "new" when it comes to immutable
		 * objects.
		 */

		Integer b = Integer.valueOf(1);
		printHash("VALUE OF", b);
		printHash("CALL NEW", new Integer(1));
		System.out.println();

		/*
		 * Strings are immutable objects! As such, their references are safe to
		 * pass since any modification to that String will break the reference
		 * to the original object.
		 */

		String c = "apple";
		printHash("OUTER BEG", c);
		testString(c);
		printHash("OUTER END", c);
		System.out.println();

		/*
		 * Because of this, if you need to make a lot of modification to a
		 * String, use a StringBuilder instead (or a StringBuffer if you are
		 * using multiple threads).
		 *
		 * StringBuilder is mutable, so the function may modify the value of the
		 * original object directly through the passed reference.
		 */

		StringBuilder d = new StringBuilder("apple");
		printHash("OUTER BEG", d);
		testBuilder(d);
		printHash("OUTER END", d);
		System.out.println();

		/*
		 * Arrays are mutable objects in Java. (You see the "new" keyword after
		 * all, don't you?)
		 */

		String[] e = new String[] { "apple", "banana" };
		printHash("OUTER BEG", e);
		testArray(e);
		printHash("OUTER END", e);
		System.out.println();

		/*
		 * The same holds for an ArrayList and other Collection objects. These
		 * objects are mutable, so passing a reference can be dangerous. A
		 * function could clear the collection completely through the reference.
		 */

		ArrayList<String> f = new ArrayList<String>();
		f.add("apple");
		printHash("OUTER BEG", f);
		testList(f);
		printHash("OUTER END", f);
	}

}
