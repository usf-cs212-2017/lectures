import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Demonstrates basic map operations using a {@link HashMap} and a
 * {@link TreeMap}.
 */
public class MapDemo {

	/**
	 * Test case for this demo. This is another form of the sentence "Buffalo
	 * buffalo buffalo buffalo buffalo." See Wikipedia for more:
	 * http://en.wikipedia.org/wiki/Buffalo_buffalo_Buffalo_buffalo_buffalo_buffalo_Buffalo_buffalo
	 */
	public static final String buffalo = "bison that other bison bully also bully bison";

	/**
	 * Stores and prints a word count using the provided map and text.
	 *
	 * @param map
	 *            - where to store word count
	 * @param text
	 *            - test case to use
	 */
	private static void demoMap(Map<String, Integer> map, String text) {
		/*
		 * Split text into words by space, and iterate through each word.
		 */
		for (String word : text.split(" ")) {

			/*
			 * Need to determine if this is the first time we saw the word and
			 * need to initialize its value, or if we need to add 1 to the old
			 * value already stored.
			 */
			if (map.containsKey(word) == false) {
				/*
				 * This is a new word, so its word count is 1. You can take
				 * advantage of auto boxing/unboxing in Java instead of
				 * explicitly converting to an Integer.
				 */

				map.put(word, 1); // map.put(word, Integer.valueOf(1));
			}
			else {
				/*
				 * This word is already stored, so get the old count from the
				 * map and add 1 to the value. Update the results. You can break
				 * this up into multiple lines if it is easier to understand!
				 */
				map.put(word, map.get(word) + 1);
			}
		}

		/*
		 * Print out the map to the console. You can take the easy approach, or
		 * iterate through the map via the key set.
		 */

		// System.out.println(map);

		for (String word : map.keySet()) {
			System.out.printf("Word: %s \tCount: %d%n", word, map.get(word));
		}

		System.out.println();
	}

	public static void main(String[] args) {
		/*
		 * Create both a HashMap and TreeMap to compare.
		 */
		HashMap<String, Integer> wordHashMap = new HashMap<>();
		TreeMap<String, Integer> wordTreeMap = new TreeMap<>();

		/*
		 * Notice that the same code works whether you use a HashMap or a
		 * TreeMap as the implementation. The only visible difference is the
		 * output order.
		 */
		System.out.println("HashMap:");
		demoMap(wordHashMap, buffalo);

		System.out.println("TreeMap:");
		demoMap(wordTreeMap, buffalo);
	}

}
