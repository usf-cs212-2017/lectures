import java.util.HashMap;
import java.util.HashSet;

/**
 * Demonstrates how collections can be nested.
 */
public class NestedMapDemo {

	/**
	 * Test case for this demo. See Wikipedia for more:
	 * http://en.wikipedia.org/wiki/Time_flies_like_an_arrow;_fruit_flies_like_a_banana
	 */
	public static final String text = "time flies like an arrow fruit flies like a banana";

	/*
	 * This time, we will store a set of words by the length of that word. For
	 * example, the word "ant" has 3 characters. The word "bat" also has length
	 * 3. So both words should appear in the set for words of length 3.
	 */
	public static void main(String[] args) {

		/*
		 * For each length word found (i.e. 3 letter word, 4 letter word), will
		 * store a set of all words found with that length.
		 *
		 * Note that this only initializes the outer data structure! We cannot
		 * initialize the inner sets until we have keys to pair those sets with.
		 */
		HashMap<Integer, HashSet<String>> words = new HashMap<>();

		for (String word : text.toLowerCase().split(" ")) {
			// Determine length of word
			Integer length = word.length();

			// Check if we need to initialize the inner data structure
			// There are a couple ways to do this!
			if (words.get(length) == null) {
				words.put(length, new HashSet<>());
			}

			// Since we know the inner set is initialized by this point,
			// go ahead and add the word. Duplicates will be ignored!
			words.get(length).add(word);

			/*
			 * This can be confusing. We are getting the set stored WITHIN the
			 * map, and adding a word directly. We do not need to call put()
			 * again. This is related to mutability and references in Java which
			 * we will get into more detail later.
			 */
		}

		/*
		 * To iterate through a nested data structure, we need one loop per
		 * level of nesting. Here we have 2 levels, so we need 2 loops.
		 */
		for (Integer length : words.keySet()) {
			System.out.printf("%d", length);

			for (String word : words.get(length)) {
				System.out.printf(", %s", word);
			}

			System.out.printf("%n");
		}
	}
}
