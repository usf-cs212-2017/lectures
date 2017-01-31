import java.util.Arrays;
import java.util.ConcurrentModificationException;
import java.util.TreeSet;

public class RemoveDemo {

	private static final TreeSet<String> words = new TreeSet<>(Arrays.asList("ant", "bee", "cow", "dog", "elk"));

	public static void traditionalFor() {
		TreeSet<String> copy = new TreeSet<>(words);

		// TODO
		
		// System.out.printf("size = %d, i = %d, element = %s, set = %s%n", copy.size(), i, first, copy);
	}

	public static void enhancedFor() {
		TreeSet<String> copy = new TreeSet<>(words);

		try {
			// TODO
			
			// System.out.printf("size = %d, element = %s, set = %s%n", copy.size(), first, copy);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void whileLoop() {
		TreeSet<String> copy = new TreeSet<>(words);

		// TODO
		
		// System.out.printf("size = %d, element = %s, set = %s%n", copy.size(), first, copy);
	}

	public static void usingClear() {
		TreeSet<String> copy = new TreeSet<>(words);

		// TODO
		
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
