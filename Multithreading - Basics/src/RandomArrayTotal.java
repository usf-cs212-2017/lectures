import java.util.Arrays;
import java.util.Random;

/**
 * Demonstrates basic multithreading, and illustrates how to break up a problem
 * into subproblems. Also used to motivate the inefficiency of constantly
 * creating new threads instead of reusing them.
 */
public class RandomArrayTotal {

	/**
	 * Fills an integer array with random integers between 0 and {@code max}.
	 *
	 * @param numbers
	 *            array to fill with random integers
	 * @param max
	 *            maximum (positive) integer
	 */
	public static void fillRandom(int[] numbers, int max) {
		Random random = new Random(System.currentTimeMillis());

		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = random.nextInt(max);
		}
	}

	/**
	 * Calculates a subtotal in an array. Does no validation of parameters, so
	 * results will vary when invalid indices are provided.
	 *
	 * @param numbers
	 *            array of numbers to subtotal
	 * @param start
	 *            index of array to start subtotal
	 * @param chunk
	 *            number of values to subtotal
	 * @return subtotal of numbers from index {@code start} (inclusive) to
	 *         {@code start + chunk} (exclusive)
	 */
	public static long subtotal(int[] numbers, int start, int chunk) {
		long total = 0;

		for (int i = start; i < (start + chunk); i++) {
			total = total + numbers[i];
		}

		return total;
	}

	/**
	 * Calculates total of values in an array.
	 *
	 * @param numbers
	 *            array of numbers to total
	 * @return total of numbers in array
	 */
	public static long total(int[] numbers) {
		return subtotal(numbers, 0, numbers.length);
	}

	/**
	 * Calculates the total of an array using multithreading. Used to
	 * demonstrate the cost of creating/destorying thread objects versus using a
	 * work queue.
	 *
	 * @param numbers
	 *            array of numbers to total
	 * @param threads
	 *            number of threads to create
	 * @return total of numbers in array
	 * @throws InterruptedException
	 */
	public static long total(int[] numbers, int threads) throws InterruptedException {
		// make sure do not have more threads than numbers
		threads = threads > numbers.length ? numbers.length : threads;
		threads = threads < 1 ? 1 : threads;

		// create an array of workers
		ArrayWorker[] workers = new ArrayWorker[threads];

		// calculate how to split up the problem
		int chunk = numbers.length / workers.length;
		int remainder = numbers.length % workers.length;
		int last = workers.length - 1;

		long total = 0;

		assert chunk > 0;
		assert remainder >= 0;

		// create and start the worker threads
		for (int i = 0; i < last; i++) {
			workers[i] = new ArrayWorker(numbers, i * chunk, chunk);
			workers[i].start();
		}

		// account for any remainder
		workers[last] = new ArrayWorker(numbers, last * chunk, chunk + remainder);
		workers[last].start();

		// wait for workers to finish and add up subtotal
		for (ArrayWorker worker : workers) {
			worker.join();
			total = total + worker.subtotal;
		}

		return total;
	}

	/**
	 * Uses the {@link RandomArrayTotal#subtotal(int[], int, int) method to
	 * generate a subtotal of an array.
	 */
	private static class ArrayWorker extends Thread {
		private final int[] numbers;
		private final int start;
		private final int end;

		private long subtotal;

		public ArrayWorker(int[] numbers, int start, int end) {
			this.numbers = numbers;
			this.start = start;
			this.end = end;

			this.subtotal = 0;
		}

		@Override
		public void run() {
			subtotal = subtotal(numbers, start, end);
		}
	}

	/**
	 * Compares our single threaded and multi threaded approach. Notice that we
	 * do not see a speedup when we increase the number of threads. Do you
	 * understand why?
	 *
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		int[] numbers = new int[5];
		fillRandom(numbers, 10);

		System.out.println(Arrays.toString(numbers));
		System.out.println(total(numbers));
		System.out.println(total(numbers, 5));

		benchmark(100, 1);
		benchmark(100, 5);
	}

	/**
	 * Rough benchmarking estimate. Use a benchmark package for better results.
	 * @param size
	 * @param threads
	 * @throws InterruptedException
	 */
	private static void benchmark(int size, int threads) throws InterruptedException {
		int warmup = 3;
		int runs = 5;

		long start = 0;
		long elapsed = 0;
		double average = 0;

		int[] numbers = new int[size];
		fillRandom(numbers, 100);

		for (int i = 0; i < warmup; i++) {
			total(numbers, threads);
		}

		for (int i = 0; i < runs; i++) {
			start = System.nanoTime();
			total(numbers, threads);
			elapsed = System.nanoTime() - start;
			average += elapsed;
		}

		average /= runs;
		average /= 1000000000;

		System.out.printf("%.05f seconds average for %d numbers and %d threads%n", average, size, threads);
	}
}
