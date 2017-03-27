import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Demonstrates the time difference between using the synchronized keyword and a
 * read/write lock for an IndexedSet object.
 *
 * @see IndexedSet
 * @see SynchronizedSet
 * @see ConcurrentSet
 */
public class SetDemo {
	private static final Logger log = LogManager.getLogger();

	/**
	 * Generates a list of generic data elements.
	 *
	 * @param size
	 *            number of data elements to generate
	 * @return list of data elements
	 */
	public static ArrayList<String> generateData(int size) {
		ArrayList<String> elements = new ArrayList<>(size);

		// use size to determine how many 0s to pad each number
		int length = Integer.toString(size).length();
		String format = "item-%0" + length + "d";

		// add elements to list one at a time
		// (simple, but not necessarily most efficient)
		for (int i = 0; i < size; i++) {
			elements.add(String.format(format, i));
		}

		return elements;
	}

	/**
	 * Returns the number of nanoseconds that passed from one add and two copy
	 * operations.
	 *
	 * @param source
	 *            source data to add to destination set
	 * @param destination
	 *            destination set (should be thread-safe)
	 * @return nanoseconds passed
	 */
	public static long timeMulti(List<String> source, IndexedSet<String> destination) {
		long time = System.nanoTime();

		Thread adder = new Thread() {
			@Override
			public void run() {
				destination.addAll(source);
			}
		};

		Thread copy1 = new Thread() {
			@Override
			public void run() {
				destination.sortedCopy();
			}
		};

		Thread copy2 = new Thread() {
			@Override
			public void run() {
				destination.unsortedCopy();
			}
		};

		adder.setPriority(Thread.MAX_PRIORITY);
		copy1.setPriority(Thread.NORM_PRIORITY);
		copy2.setPriority(Thread.NORM_PRIORITY);

		adder.start();
		copy1.start();
		copy2.start();

		try {
			adder.join();
			copy1.join();
			copy2.join();
		}
		catch (InterruptedException e) {
			log.debug(e.getMessage(), e);
		}

		time = System.nanoTime() - time;

		return time;
	}

	/**
	 * Returns the number of nanoseconds that passed from one add and two copy
	 * operations.
	 *
	 * @param source
	 *            source data to add to destination set
	 * @param destination
	 *            destination set (should be thread-safe)
	 * @return nanoseconds passed
	 */
	public static long timeSingle(List<String> source, IndexedSet<String> destination) {
		long time = System.nanoTime();

		destination.addAll(source);
		destination.sortedCopy();
		destination.unsortedCopy();

		time = System.nanoTime() - time;

		return time;
	}

	/**
	 * Roughly demonstrates runtime different between using synchronized and a
	 * read/write lock when there are more than one large read operations. Note:
	 * not an accurate benchmark, but you get the idea.
	 *
	 * @param args
	 *            unused
	 */
	public static void main(String[] args) {
		List<String> data = generateData(10000);

		IndexedSet<String> set1 = new IndexedSet<>();
		SynchronizedSet<String> set2 = new SynchronizedSet<>();
		ConcurrentSet<String> set3 = new ConcurrentSet<>();

		double time1 = timeSingle(data, set1) / (double) 1000000000;
		double time2 = timeMulti(data, set2) / (double) 1000000000;
		double time3 = timeMulti(data, set3) / (double) 1000000000;

		System.out.printf("Indexed:\t\t %.5f seconds%n", time1);
		System.out.printf("Synchronized:\t %.5f seconds%n", time2);
		System.out.printf("Concurrent:\t %.5f seconds%n", time3);
		System.out.println();

		double speed1 = time1 / time2;
		double speed2 = time2 / time3;
		double speed3 = time1 / time3;

		System.out.printf("Speedup I-S:\t %.4fx %s%n", speed1, speed1 > 0 ? "faster" : "slower");
		System.out.printf("Speedup S-C:\t %.4fx %s%n", speed2, speed2 > 0 ? "faster" : "slower");
		System.out.printf("Speedup I-C:\t %.4fx %s%n", speed3, speed3 > 0 ? "faster" : "slower");
	}
}
