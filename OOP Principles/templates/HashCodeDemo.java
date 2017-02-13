import java.util.ArrayList;
import java.util.Arrays;

public class HashCodeDemo {

	public static void printHash(String label, Object object) {
		System.out.printf("%-9s : x%08X : %s %n", label, System.identityHashCode(object), object);
	}

	public static void printHash(String label, Object[] object) {
		System.out.printf("%-9s : x%08X : %s %n", label, System.identityHashCode(object), Arrays.toString(object));
	}

	public static void testInteger(Integer value) {
		printHash("INNER BEG", value);
		// TODO
		printHash("INNER END", value);
	}

	public static void testString(String value) {
		printHash("INNER BEG", value);
		// TODO
		printHash("INNER END", value);
	}

	public static void testBuilder(StringBuilder value) {
		printHash("INNER BEG", value);
		// TODO
		printHash("INNER END", value);
	}

	public static void testArray(String[] value) {
		printHash("INNER BEG", value);
		// TODO
		printHash("INNER END", value);
	}

	public static void testList(ArrayList<String> value) {
		printHash("INNER BEG", value);
		// TODO
		printHash("INNER BEG", value);
	}

	public static void main(String[] args) {

		Integer a = null; // TODO
		printHash("OUTER BEG", a);
		testInteger(a);
		printHash("OUTER END", a);
		System.out.println();

//		Integer b = null; // TODO
//		printHash("VALUE OF", b);
//		printHash("CALL NEW", new Integer(1));
//		System.out.println();

//		String c = null; // TODO
//		printHash("OUTER BEG", c);
//		testString(c);
//		printHash("OUTER END", c);
//		System.out.println();

//		StringBuilder d = null; // TODO
//		printHash("OUTER BEG", d);
//		testBuilder(d);
//		printHash("OUTER END", d);
//		System.out.println();

//		String[] e = null; // TODO
//		printHash("OUTER BEG", e);
//		testArray(e);
//		printHash("OUTER END", e);
//		System.out.println();

//		ArrayList<String> f = null; // TODO
//		f.add("apple");
//		printHash("OUTER BEG", f);
//		testList(f);
//		printHash("OUTER END", f);
	}

}
