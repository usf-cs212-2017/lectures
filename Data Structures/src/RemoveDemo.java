import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.TreeSet;

/**
 * Demonstrates that modifying a collection while iterating through that
 * collection is a bad idea.
 */
public class RemoveDemo {

	private static final TreeSet<String> words = new TreeSet<>(Arrays.asList("ant", "bee", "cow", "dog", "elk"));

	/**
	 * Uses a traditional for loop with {@link TreeSet#pollFirst()} within the
	 * loop. Notice that we are missing dog and elk in the loop output, and that
	 * they are not removed from the set!
	 */
	public static void traditionalFor() {
		// create a fresh copy
		TreeSet<String> copy = new TreeSet<>(words);

		for (int i = 0; i < copy.size(); i++) {
			// retrieves and **removes** first element
			String first = copy.pollFirst();

			System.out.printf("size = %d, i = %d, element = %s, set = %s%n", copy.size(), i, first, copy);
		}
	}

	/**
	 * Uses an enhanced for loop with {@link TreeSet#pollFirst()} within the
	 * loop. Notice how we get a {@link ConcurrentModificationException} thrown
	 * on the first iteration!
	 */
	@SuppressWarnings("unused")
	public static void enhancedFor() {
		// create a fresh copy
		TreeSet<String> copy = new TreeSet<>(words);

		try {
			for (String word : copy) {
				// retrieves and **removes** first element
				String first = copy.pollFirst();

				System.out.printf("size = %d, element = %s, set = %s%n", copy.size(), first, copy);
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Uses a while loop with {@link TreeSet#pollFirst()} within the loop. This
	 * example works properly for removing elements. Other operations,
	 * especially those that add elements, may require other strategies.
	 */
	public static void whileLoop() {
		// create a fresh copy
		TreeSet<String> copy = new TreeSet<>(words);

		while (copy.size() > 0) {
			// retrieves and **removes** first element
			String first = copy.pollFirst();

			System.out.printf("size = %d, element = %s, set = %s%n", copy.size(), first, copy);
		}
	}

	/**
	 * Of course, if we really wanted to remove ALL the elements, there are much
	 * simpler ways for us to proceed.
	 */
	public static void usingClear() {
		// create a fresh copy
		TreeSet<String> copy = new TreeSet<>(words);

		copy.clear();
		System.out.println(copy);
	}

	public static void main(String[] args) {
		System.out.println("Original: " + words);

		System.out.println();
		System.out.println("Traditional For Loop:");
		traditionalFor();

		System.out.println();
		System.out.println("Enhanced For Loop:");
		enhancedFor();

		System.out.println();
		System.out.println("While Loop:");
		whileLoop();

		System.out.println();
		System.out.println("Using Clear:");
		usingClear();
	}

}
