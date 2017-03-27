import java.util.Arrays;
import java.util.Random;

public class RandomArrayTotal {

	public static void fillRandom(int[] numbers, int max) {
		Random random = new Random(System.currentTimeMillis());

		for (int i = 0; i < numbers.length; i++) {
			numbers[i] = random.nextInt(max);
		}
	}

	public static long subtotal(int[] numbers, int start, int chunk) {
		long total = 0;

		for (int i = start; i < (start + chunk); i++) {
			total = total + numbers[i];
		}

		return total;
	}

	public static long total(int[] numbers) {
		return subtotal(numbers, 0, numbers.length);
	}

	public static long total(int[] numbers, int threads) throws InterruptedException {
		// TODO

		return -1;
	}

	public static void main(String[] args) throws InterruptedException {
		int[] numbers = new int[5];
		fillRandom(numbers, 10);

		System.out.println(Arrays.toString(numbers));
		System.out.println(total(numbers));
		System.out.println(total(numbers, 5));

//		benchmark(100, 1);
//		benchmark(100, 5);
	}

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
