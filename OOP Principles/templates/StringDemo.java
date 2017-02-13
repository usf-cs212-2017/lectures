/**
 * This class demonstrates the {@link String} class, and how it is optimized by
 * Java for efficiency. It also illustrates the dangers of using the == operator
 * on objects in Java.
 *
 * This class is a companion to {@link ObjectDemo}.
 */
public class StringDemo {

	public static void main(String[] args) {
		/*
		 * When the new keyword is used, new memory will always be created.
		 * However, we do not need to use this keyword with String objects.
		 * Since they are immutable, Java will reuse String objects when
		 * possible if the new keyword is not used.
		 */

		String demo1 = "apple";
		String demo2 = "apple";
		String demo3 = new String("apple");
		String demo4 = new String(demo3);

		/*
		 * Unsurprisingly, all of the String objects have value "apple".
		 */

		System.out.printf("demo1 = %s%n", demo1);
		System.out.printf("demo2 = %s%n", demo2);
		System.out.printf("demo3 = %s%n", demo3);
		System.out.printf("demo4 = %s%n", demo4);
		System.out.printf("%n");

		/*
		 * Even though we asked Java to create two of the String objects in new
		 * memory, all of the hash codes appear to be the same!
		 */

		System.out.printf("demo1.hashCode() = %x%n", demo1.hashCode());
		System.out.printf("demo2.hashCode() = %x%n", demo2.hashCode());
		System.out.printf("demo3.hashCode() = %x%n", demo3.hashCode());
		System.out.printf("demo4.hashCode() = %x%n", demo4.hashCode());
		System.out.printf("%n");

		/*
		 * Since the equals() method uses the hashCode(), all of these Strings
		 * are considered equivalent to each other. We actually want this
		 * behavior---if the String objects have the same value, they should be
		 * considered equivalent.
		 */

		System.out.printf("demo1.equals(demo2) = %s%n", demo1.equals(demo2));
		System.out.printf("demo2.equals(demo3) = %s%n", demo2.equals(demo3));
		System.out.printf("demo3.equals(demo4) = %s%n", demo3.equals(demo4));
		System.out.printf("%n");

		/*
		 * But, the hash code here is misleading. Any class can override this
		 * method to return something OTHER than the memory location. To view
		 * the actual memory location, you must use the identityHashCode()
		 * method whenever dealing with a class other than Object.
		 */

		System.out.printf("demo1.identityHashCode() = %x%n", System.identityHashCode(demo1));
		System.out.printf("demo2.identityHashCode() = %x%n", System.identityHashCode(demo2));
		System.out.printf("demo3.identityHashCode() = %x%n", System.identityHashCode(demo3));
		System.out.printf("demo4.identityHashCode() = %x%n", System.identityHashCode(demo4));
		System.out.printf("%n");

		/*
		 * Note that equals() and == no longer do the same operation. The String
		 * class overrode equals() to compare by content. But, the == operator
		 * still compares by memory location!
		 */

		System.out.printf("demo1 == demo2 = %s%n", demo1 == demo2);
		System.out.printf("demo2 == demo3 = %s%n", demo2 == demo3);
		System.out.printf("demo3 == demo4 = %s%n", demo3 == demo4);
		System.out.printf("%n");

		/*
		 * Be very aware of the difference between equals() and == when you are
		 * doing any kind of comparison. Most of the time, classes will override
		 * equals() to compare by value, not memory location. But the ==
		 * operator will *always* compare by memory locations.
		 */
	}
}
