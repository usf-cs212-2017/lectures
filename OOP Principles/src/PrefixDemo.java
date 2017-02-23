/**
 * Briefly demonstrates how to create a custom data structure, and the dangers
 * of returning references of this data structure. As a result, this object is
 * not encapsulated properly, and is not hiding its internal data properly (even
 * though it is set to private).
 *
 * @see PrefixMap
 */
public class PrefixDemo {

	public static void main(String[] args) {

		String[] words = { "ant", "antelope", "ape", "bat", "badger", "cat", "catfish", "dog", "dragonfly" };

		PrefixMap map = new PrefixMap(2);

		map.addWords(words);
		System.out.println(map);

		/*
		 * Even though we cannot access the "internal" data structure directly,
		 * we can access it through the returned reference and run amok with the
		 * data.
		 */
		map.getMap().remove("do");
		System.out.println(map);

		/*
		 * Note that this works with the inner data structure as well!
		 */
		map.getWords("ba").clear();
		System.out.println(map);

		/*
		 * At this point, we have completely broken the integrity of our data
		 * structure. We can no longer guarantee that the map stores prefixes
		 * and strings that start with that prefix.
		 */
		map.getWords("ba").add("zebra");
		System.out.println(map);

		/*
		 * Unmodifiable versions of these sets solves some problems.
		 */

		try {
			map.getUnmodifiablePrefixes().clear();
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}

		try {
			map.getUnmodifiableMap().clear();
		}
		catch (Exception e) {
			System.out.println(e.toString());
		}

		System.out.println(map);

		/*
		 * But with nested data structures, we still have issues with
		 * the nested data structures.
		 */

		map.getUnmodifiableMap().get("ap").add("potato");
		System.out.println(map);

		/*
		 * We can even break things if we return references to our key set!
		 */
		map.getPrefixes().clear();
		System.out.println(map);

	}
}
