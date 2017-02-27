import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class demonstrates very basic upcasting and downcasting.
 */
public class CastDemo {

	/**
	 * This method demonstrates simple upcasting and downcasting. Note that even
	 * though {@link Number} is an abstract class, we can still use it as a
	 * reference.
	 */
	public static void numberDemo() {
		// This is upcasting from Double to its superclass Number.
		Number n = new Double(3.14);

		// From the Number reference, we can only access methods in Number or
		// a superclass of Number.
		System.out.println(n.intValue());

		// To access the Double methods again, we must use an explicit cast.
		Double d = (Double) n;
		System.out.println(d.isInfinite());
	}

	/**
	 * This method demonstrates one possible use for upcasting---getting around
	 * the restriction that arrays must have the same type.
	 */
	public static void arrayDemo() {
		// Array elements must have the same type... so we can use upcasting
		// to mix Integer, Double, and BigInteger objects in a single array.
		Number[] numbers = new Number[] { new Double(3.14), new Integer(42), new BigInteger("123456789") };

		// We can then operate use any of the Number of Object methods on
		// these array elements, like Object.toString().
		for (Number n : numbers) {
			System.out.println(n.doubleValue());
		}
	}

	/**
	 * This method demonstrates that since every class implicitly inherits from
	 * {@link Object}, we can always upcast to the {@link Object} class.
	 */
	public static void objectDemo() {
		// Everything is an object, so we can always upcast to Object.
		Object[] objects = new Object[] { new CastDemo(), new Integer(42), new ArrayList<Double>(),
				new String[] { "a" } };

		// However, we can only use Object methods from those references.
		for (Object o : objects) {
			System.out.println(o.getClass().getSimpleName());
		}
	}

	/**
	 * This method demonstrates that we can also do this if the classes share
	 * the same interface.
	 */
	public static void interfaceDemo() {
		// All of the following implement the CharSequence interface
		CharSequence[] chars = new CharSequence[] { new String("apple"), new StringBuffer("banana"),
				new StringBuilder("carrot") };

		// We can use the generalized printFirstLetter() method on
		// multiple types of objects now!
		for (CharSequence c : chars) {
			printFirstLetter(c);
		}
	}

	/**
	 * This method is part of the {@link #interfaceDemo()} example, and shows
	 * how generalized methods can be created that will work on multiple
	 * subclasses.
	 *
	 * @param seq
	 *            to print first letter for
	 */
	public static void printFirstLetter(CharSequence seq) {
		System.out.println(seq.charAt(0));
	}

	/**
	 * This method demonstrates how it may be useful to use upcasting for
	 * declaring collections---allowing the implementation class to change
	 * without affecting the rest of the code.
	 */
	public static void setDemo() {
		// Use upcasting to create a general reference
		Set<String> set = null;

		// Choose one to comment out
		set = new TreeSet<String>();
		// set = new HashSet<String>();

		// Everything below here remains the same, even if you
		// decide to change the underlying set type
		set.add("apple");
		set.add("banana");
		set.add("carrot");

		for (String element : set) {
			System.out.println(element);
		}

		/*
		 * Using upcasting prevents you from using any methods specific to, for
		 * example, TreeSet, making your code more generalized.
		 */
	}

	/**
	 * This method runs all of the examples at once. You can comment out each
	 * demo to run one at a time if you want.
	 *
	 * @param args
	 *            unused
	 */
	public static void main(String[] args) {
		numberDemo();
		arrayDemo();
		objectDemo();
		interfaceDemo();

		// Object a = new String("3.14");
		// Double b = (Double) a;
	}

}
