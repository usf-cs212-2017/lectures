import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.Instant;
import java.util.Set;

public class ListingBenchmark {

	public static final int WARMUP_ROUNDS = 10;
	public static final int TIMED_ROUNDS = 20;

	public static void main(String[] args) {
		Path test = Paths.get("..", "..").toAbsolutePath().normalize();

		Set<Path> expected = SerialDirectoryListing.list(test);

		System.out.println("Benchmarking serial directory listing...");
		double serial = 0; // TODO

		System.out.println("Benchmarking work queue directory listing...");
		double queue = 0; // TODO

		System.out.println("Benchmarking executor directory listing...");
		double executor = 0; // TODO

		System.out.println("Benchmarking futures directory listing...");
		double futures = 0; // TODO

		System.out.println();
		System.out.println(String.format("%20s: %8.2fms", "Serial", serial));
		System.out.println(String.format("%20s: %8.2fms\t\tSpeedup: %.2fx", "Work Queue", queue, serial / queue));
		System.out.println(String.format("%20s: %8.2fms\t\tSpeedup: %.2fx", "Executor", executor, serial / executor));
		System.out.println(String.format("%20s: %8.2fms\t\tSpeedup: %.2fx", "Futures", futures, serial / futures));
	}

	private static abstract class Benchmarker {
		public abstract Set<Path> run(Path path);

		public double benchmark(Path path, Set<Path> expected) {
			// TODO
		}
	}

}
