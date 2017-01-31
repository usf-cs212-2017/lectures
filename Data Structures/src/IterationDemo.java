import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * This class demonstrates several approaches (some that work, some that do not)
 * to iterating through a nested data structure.
 *
 * The level of nesting is usually a clue to how many nested loops you will need
 * to iterate through all of the values.
 */
public class IterationDemo {

	// tree is chosen for consistent output, list is chosen just to
	// demonstrate a different list implementations
	private final TreeMap<Integer, LinkedList<String>> results;

	// array of words used for demonstration purposes
	private final String[] words = { "alligator", "ape", "bear", "bision", "cat", "camel", "deer", "dog", "dragonfly",
			"hippopotamus" };

	public IterationDemo() {
		// initialize outer data structure
		results = new TreeMap<Integer, LinkedList<String>>();

		// add words to map based on length of word
		for (String word : words) {
			Integer length = word.length();

			// make sure the inner data structure is initialized
			if (!results.containsKey(length)) {
				results.put(length, new LinkedList<>());
			}

			// add the word to the appropriate list directly
			results.get(length).add(word);
		}
	}

	/*
	 * Example 1: Uses a traditional for loop in an attempt to output the map
	 * (but does so incorrectly and inefficiently).
	 */
	public void forExample() {
		System.out.println("Traditional For Example:");
		int iterations = 0;

		for (int i = 0; i < results.size(); i++) {
			if (results.containsKey(i)) {
				System.out.print(i + " = ");
				LinkedList<String> list = results.get(i);

				for (int j = 0; j < list.size(); j++) {
					System.out.print(list.get(j) + " ");
				}

				System.out.println();
			}

			iterations++;
		}

		System.out.println("(" + iterations + " iterations)\n");

		/*
		 * Output Incorrect: Many of you may try to use a "traditional" looking
		 * for loop, but this approach does not produce the correct output. It
		 * incorrectly assumes that the last key value is less than the size of
		 * the map. In the demo, the last key value is 12, but the size of the
		 * map (determined by the number of keys) is 5.
		 *
		 * Output Inefficient: It took 5 outer iterations to output 2 results
		 * from our map. We can do much better than that!
		 */
	}

	/*
	 * This example tries instead to use a while loop. The output is correct,
	 * but very inefficient to generate.
	 */
	public void whileExample() {
		System.out.println("While Example:");

		int i = 0; // Iteration number
		int count = 0; // Number of elements output

		while (count < results.size()) {
			if (results.containsKey(i)) {
				System.out.print(i + " = ");
				LinkedList<String> list = results.get(i);

				for (int j = 0; j < list.size(); j++) {
					System.out.print(list.get(j) + " ");
				}

				System.out.println();
				count++;
			}

			i++;
		}

		System.out.println("(" + i + " iterations)\n");

		/*
		 * Output Correct: Unlike the previous example, this does produce the
		 * correct output. Instead of looping until a key larger than the size
		 * is reached, it loops until results.size() keys have been found.
		 *
		 * Output Inefficient: This is unnecessarily inefficient. What happens
		 * if there are only two keys... 2 and 100. We loop through 100 times to
		 * get two values out of our data structure!
		 */
	}

	/*
	 * This example is correct and efficient, but a bit clunky due to its use of
	 * iterator objects. The next example shows how to improve on this.
	 */
	public void iteratorExample() {
		System.out.println("Iterator Example:");
		int iterations = 0;

		// Gets the iterator for the map keys
		Iterator<Integer> setIterator = results.keySet().iterator();

		while (setIterator.hasNext()) {
			Integer key = setIterator.next();
			System.out.print(key + " = ");

			// Get the iterator for the inner list
			Iterator<String> listIterator = results.get(key).iterator();

			while (listIterator.hasNext()) {
				System.out.print(listIterator.next() + " ");
			}

			System.out.println();
			iterations++;
		}

		System.out.println("(" + iterations + " iterations)\n");

		/*
		 * Output Correct and Efficient: By using iterators, we never loop more
		 * times than necessary. With a sorted data structure, the iterators
		 * will go through the collection in sorted order.
		 *
		 * However, the use of iterators for such a simple case may be
		 * considered unnecessarily "clunky" since there are other approaches
		 * that involve less code and objects.
		 */
	}

	/*
	 * This example demonstrates a basic nested for each loop, which is like
	 * using an iterator but easier to read.
	 */
	public void foreachExample() {
		System.out.println("For Each Example:");
		int iterations = 0;

		for (Integer key : results.keySet()) {
			System.out.print(key + " = ");

			for (String word : results.get(key)) {
				System.out.print(word + " ");
			}

			System.out.println();
			iterations++;
		}

		System.out.println("(" + iterations + " iterations)\n");

		/*
		 * Output Correct and Efficient: This will produce the same output for
		 * as an iterator, but results in easier to read code. It also removes
		 * the threat of off-by-one errors that comes with traditional for
		 * loops.
		 *
		 * You can use the for each loop on just about any type of collection,
		 * including arrays.
		 */
	}

	/*
	 * This example demonstrates another approach (which may be slightly more
	 * efficient) to using a for each loop.
	 */
	public void mapentryExample() {
		System.out.println("Map Entry Example");
		int iterations = 0;

		for (Map.Entry<Integer, LinkedList<String>> entry : results.entrySet()) {
			System.out.print(entry.getKey() + " = ");

			for (String word : entry.getValue()) {
				System.out.print(word + " ");
			}

			System.out.println();
			iterations++;
		}

		System.out.println("(" + iterations + " iterations)\n");

		/*
		 * This approach grabs both the key and value at the same time, avoiding
		 * multiple accesses to the nested data structure.
		 */
	}

	/*
	 * This example demonstrates how to use the methods specific to a sorted
	 * map, like TreeMap.
	 */
	public void treemapExample() {
		System.out.println("TreeMap Example");
		int iterations = 0;

		for (Integer i = results.firstKey(); i != null; i = results.higherKey(i)) {
			System.out.print(i + " = ");
			LinkedList<String> words = results.get(i);

			for (int j = 0; j < words.size(); j++) {
				System.out.print(words.get(j) + " ");
			}

			System.out.println();
			iterations++;
		}

		System.out.println("(" + iterations + " iterations)\n");

		/*
		 * We can take advantage of the firstKey(), lastKey(), higherKey(), and
		 * lowerKey() methods to explicitly iterate through the map in sorted
		 * order. We can even use these to iterate through the keys in reverse.
		 *
		 * Note that we must check whether ( i != null ) and not whether ( i <=
		 * results.lastKey() ). Can you figure out why?
		 */
	}

	/*
	 * Shows the output of the examples above.
	 */
	public static void main(String[] args) {
		IterationDemo demo = new IterationDemo();

		demo.forExample();
		demo.whileExample();

		demo.iteratorExample();
		demo.foreachExample();
		demo.mapentryExample();

		demo.treemapExample();
	}
}
