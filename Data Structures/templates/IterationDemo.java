import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

public class IterationDemo {

	private final TreeMap<Integer, LinkedList<String>> results;
	private final String[] words = { "alligator", "ape", "bear", "bision", "cat", "camel", "deer", "dog", "dragonfly",
			"hippopotamus" };

	public IterationDemo() {
		results = null; // TODO

		// TODO
	}

	public void forExample() {
		System.out.println("Traditional For Example:");
		int iterations = 0;

		// TODO
		
		System.out.println("(" + iterations + " iterations)\n");
	}

	public void whileExample() {
		System.out.println("While Example:");

		int i = 0; // Iteration number
		int count = 0; // Number of elements output

		// TODO

		System.out.println("(" + i + " iterations)\n");
	}

	public void iteratorExample() {
		System.out.println("Iterator Example:");
		int iterations = 0;

		// TODO

		System.out.println("(" + iterations + " iterations)\n");
	}

	public void foreachExample() {
		System.out.println("For Each Example:");
		int iterations = 0;

		// TODO

		System.out.println("(" + iterations + " iterations)\n");
	}

	public void mapentryExample() {
		System.out.println("Map Entry Example");
		int iterations = 0;

		// TODO

		System.out.println("(" + iterations + " iterations)\n");
	}

	public void treemapExample() {
		System.out.println("TreeMap Example");
		int iterations = 0;

		// TODO

		System.out.println("(" + iterations + " iterations)\n");
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
