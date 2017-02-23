import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Demonstrates how to create a custom data structure, and the dangers of
 * returning references of this data structure. As a result, this object is not
 * encapsulated properly, and is not hiding its internal data properly (even
 * though it is set to private).
 */
public class PrefixMap {

	/*
	 * Stores strings by the prefix they start with. The key is the prefix
	 * String and the value is a sorted set of strings that start with that
	 * prefix.
	 */
	private final TreeMap<String, TreeSet<String>> internal;

	/*
	 * Determines the size of the prefix used. Must be at least one letter. If a
	 * string is smaller than the prefix size, it is skipped. Since the value
	 * cannot be changed after initialization, it can be safely made public.
	 */
	public final int prefixSize;

	public PrefixMap(int prefixSize) {
		// Initialize the size of the prefix, defaulting to 1 if an invalid
		// value is provided.
		this.prefixSize = prefixSize < 1 ? 1 : prefixSize;

		/*
		 * Remember, this initializes the OUTER data structure only. You need a
		 * specific key to pair with the TreeSet before you can initialize the
		 * inner data structure.
		 */
		internal = new TreeMap<String, TreeSet<String>>();
	}

	public PrefixMap() {
		this(1);
	}

	public boolean addWord(String word) {
		// Make sure we have a non-null word with at least as many characters
		// as the prefix size.
		if (word == null || word.length() < prefixSize) {
			return false;
		}

		String prefix = word.substring(0, prefixSize);

		// VERSION 1: VERBOSE AND STRAIGHTFORWARD
		/*
		if (internal.containsKey(prefix)) {
			TreeSet<String> words = internal.get(prefix);
			words.add(word);
			internal.put(prefix, words); // not needed since set is mutable
		}
		else {
			TreeSet<String> words = new TreeSet<>();
			words.add(word);
			internal.put(prefix, words);
		}
		*/

		// VERSION 2: FEWER IDENTIFIERS
		/*
		if (internal.containsKey(prefix)) {
			internal.get(prefix).add(word);
		}
		else {
			internal.put(prefix, new TreeSet<>());
			internal.get(prefix).add(word);
		}
		*/

		// VERSION 3: NO REPEATED CODE

		if (!internal.containsKey(prefix)) {
			// Initialize inner data structure now that we have a key!
			// If you do this when the key already exists, you overwrite
			// the previous values with an empty set.
			internal.put(prefix, new TreeSet<String>());
		}

		// Now, we can add our word to our map. Return whether this add
		// was successful.
		return internal.get(prefix).add(word);
	}

	// Convenience method to add multiple words at once. Note our use of
	// addWord() to make sure we only add valid words. Yay for code reuse!
	public void addWords(String[] words) {
		for (String word : words) {
			addWord(word);
		}
	}

	// Returns whether a prefix exists in internal data structure.
	public boolean hasPrefix(String prefix) {
		return internal.containsKey(prefix);
	}

	// Converts entire internal data structure into a string representation
	@Override
	public String toString() {
		return internal.toString();
	}

	// Unsafe method returning reference to internal data structure.
	public TreeMap<String, TreeSet<String>> getMap() {
		return internal;
	}

	// Unsafe method returning reference to internal keyset
	public Set<String> getPrefixes() {
		return internal.keySet();
	}

	// Unsafe method returning reference to internal nested data structure
	public Set<String> getWords(String prefix) {
		return internal.get(prefix);
	}

	// Safe method but inefficient and not updated
	public Set<String> copyPrefixes() {
		return new TreeSet<String>(internal.keySet());
	}

	// Safe method but inefficient and not updated
	public Set<String> copyWords(String prefix) {
		TreeSet<String> words = internal.get(prefix);

		if (words != null) {
			return new TreeSet<String>(words);
		}

		return new TreeSet<String>();
	}

	// What would a copyMap() look like?
	// Make sure you copy both the map and the inner sets!

	// Safer method for returning a non-nested data structure
	public Set<String> getUnmodifiablePrefixes() {
		return Collections.unmodifiableSet(internal.keySet());
	}

	// Safer method for returning a non-nested data structure
	public Set<String> getUnmodifiableWords(String prefix) {
		if (internal.containsKey(prefix)) {
			return Collections.unmodifiableSet(internal.get(prefix));
		}

		return null;
	}

	// Still unsafe method returning reference to internal data structure.
	public Map<String, TreeSet<String>> getUnmodifiableMap() {
		return Collections.unmodifiableMap(internal);
	}
}
