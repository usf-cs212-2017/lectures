import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;

public class ListingBenchmark {

	public static final int WARMUP_ROUNDS = 10;
	public static final int TIMED_ROUNDS = 20;

	public static void main(String[] args) {
		// TODO Change this to a large directory on your system!
		// Path test = Paths.get("..").normalize();
		Path test = Paths.get("..", "..").toAbsolutePath().normalize();

		Set<Path> expected = SerialDirectoryListing.list(test);

		System.out.println("Benchmarking serial directory listing...");
		double serial = new Benchmarker() {
			@Override
			public Set<Path> run(Path path) {
				return SerialDirectoryListing.list(test);
			}
		}.benchmark(test, expected);

		System.out.println("Benchmarking work queue directory listing...");
		double queue = new Benchmarker() {
			@Override
			public Set<Path> run(Path path) {
				return WorkQueueDirectoryListing.list(test);
			}
		}.benchmark(test, expected);

		System.out.println("Benchmarking executor directory listing...");
		double executor = new Benchmarker() {
			@Override
			public Set<Path> run(Path path) {
				return ExecutorDirectoryListing.list(test);
			}
		}.benchmark(test, expected);

		System.out.println("Benchmarking futures directory listing...");
		double futures = new Benchmarker() {
			@Override
			public Set<Path> run(Path path) {
				return FuturesDirectoryListing.list(test);
			}
		}.benchmark(test, expected);

		System.out.println();
		System.out.println(String.format("%20s: %8.2fms", "Serial", serial));
		System.out.println(String.format("%20s: %8.2fms\t\tSpeedup: %.2fx", "Work Queue", queue, serial / queue));
		System.out.println(String.format("%20s: %8.2fms\t\tSpeedup: %.2fx", "Executor", executor, serial / executor));
		System.out.println(String.format("%20s: %8.2fms\t\tSpeedup: %.2fx", "Futures", futures, serial / futures));
	}

	private static abstract class Benchmarker {
		public abstract Set<Path> run(Path path);

		public double benchmark(Path path, Set<Path> expected) {
			// verify results first
			if (!run(path).equals(expected)) {
				System.err.println("Unexpected results!");
			}

			// warmup
			for (int i = 0; i < WARMUP_ROUNDS; i++) {
				run(path);
			}

			// timed
			Instant start = Instant.now();
			for (int i = 0; i < TIMED_ROUNDS; i++) {
				run(path);
			}
			Instant end = Instant.now();

			// averaged result
			Duration elapsed = Duration.between(start, end);
			return (double) elapsed.toMillis() / TIMED_ROUNDS;
		}
	}

}
