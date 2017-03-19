import java.nio.file.Path;
import java.nio.file.Paths;

public class CharacterBenchmark {

	public static void main(String[] args) throws Exception {
		int warmup = 3;
		int runs = 10;

		Path file1 = Paths.get("text", "pg1661.txt");
		Path file2 = Paths.get("text", "pg2701.txt");

		long start = 0;
		long elapsed = 0;
		double single = 0;
		double multiple = 0;

		/*
		 * Make sure you DISABLE LOGGING before running this benchmark!
		 */

		for (int i = 0; i < warmup; i++) {
			CharacterCompare.compareSequentially(file1, file2, 'c');
		}

		for (int i = 0; i < runs; i++) {
			start = System.nanoTime();
			CharacterCompare.compareSequentially(file1, file2, 'c');
			elapsed = System.nanoTime() - start;
			single += elapsed;
		}

		single /= runs;
		single /= 1000000000;

		System.out.printf("Took %.05f seconds average for sequential comparison.%n", single);

		/* MULTITHREADING */

		for (int i = 0; i < warmup; i++) {
			CharacterCompare.compareConcurrently(file1, file2, 'c');
		}

		for (int i = 0; i < runs; i++) {
			start = System.nanoTime();
			CharacterCompare.compareConcurrently(file1, file2, 'c');
			elapsed = System.nanoTime() - start;
			multiple += elapsed;
		}

		multiple /= runs;
		multiple /= 1000000000;
		System.out.printf("Took %.05f seconds average for concurrent comparison.%n", multiple);

		double speedup = single / multiple;
		System.out.printf("Execution time speedup: %.02f times faster%n", speedup);
	}
}
