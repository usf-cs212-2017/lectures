import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.TreeSet;

/**
 * This class demonstrates different types of collections. In particular, it
 * illustrates the difference between a different types of lists and sets.
 */
public class CollectionDemo {

	public static void parseLine(String line) {

		/*
		 * Note how we must specify the element type using the Generics < >
		 * notation, and with maps we must specify the type of both the key and
		 * the value elements.
		 *
		 * Also notice we cannot use primitive types. Instead of an int, we must
		 * store its object-equivalent Integer.
		 */

		ArrayList<String> wordList = new ArrayList<>();
		HashSet<String> wordHashSet = new HashSet<>();
		TreeSet<String> wordTreeSet = new TreeSet<>();

		/*
		 * We will use the String.split() method to split a line into individual
		 * words.
		 */

		String[] words = line.split(" "); // Split by spaces

		/*
		 * To print the contents of an array, use the helper method in the
		 * Arrays class. Otherwise, you will not get the expected output.
		 */
		System.out.println(Arrays.toString(words));

		/*
		 * This shows a for-each or enhanced-for loop. Whenever possible, use
		 * these types of loops instead of a traditional for loop. It uses
		 * iterators, which can often be more efficient than accessing an
		 * element by index or key.
		 */
		for (String word : words) {
			// Add word to various data structures
			wordList.add(word);
			wordHashSet.add(word);
			wordTreeSet.add(word);
		}

		// Print out results in a consistent format
		String format = "%-10s : %02d items : %s\n";
		System.out.printf("\n");
		System.out.printf(format, "ArrayList", wordList.size(), wordList);
		System.out.printf(format, "HashSet", wordHashSet.size(), wordHashSet);
		System.out.printf(format, "TreeSet", wordTreeSet.size(), wordTreeSet);

		System.out.println();
	}

	public static void main(String[] args) {

		// https://en.wikipedia.org/wiki/Garden_path_sentence
		String test1 = "the old man the boat";

		// https://en.wikipedia.org/wiki/List_of_linguistic_example_sentences
		String test2 = "rose rose to put rose roes on her rows of roses";

		// https://en.wikipedia.org/wiki/Time_flies_like_an_arrow;_fruit_flies_like_a_banana
		String test3 = "time flies like an arrow fruit flies like an apple";

		parseLine(test1);
		parseLine(test2);
		parseLine(test3);
	}

}
