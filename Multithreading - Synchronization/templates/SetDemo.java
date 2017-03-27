import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SetDemo {
	private static final Logger log = LogManager.getLogger();

	public static ArrayList<String> generateData(int size) {
		ArrayList<String> elements = new ArrayList<>(size);

		int length = Integer.toString(size).length();
		String format = "item-%0" + length + "d";

		for (int i = 0; i < size; i++) {
			elements.add(String.format(format, i));
		}

		return elements;
	}

	public static long timeSingle(List<String> source, IndexedSet<String> destination) {
		long time = System.nanoTime();

		// TODO

		time = System.nanoTime() - time;

		return time;
	}
	
	public static long timeMulti(List<String> source, IndexedSet<String> destination) {
		long time = System.nanoTime();

		// TODO

		try {
			// TODO
		}
		catch (InterruptedException e) {
			log.debug(e.getMessage(), e);
		}

		time = System.nanoTime() - time;

		return time;
	}

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
