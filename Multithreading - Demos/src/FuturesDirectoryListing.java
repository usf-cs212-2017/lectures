import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Generates a directory listing using multi-threading via executors, callable
 * tasks (instead of runnable tasks), futures, and a completion service.
 */
public class FuturesDirectoryListing {

	/**
	 * Returns a directory listing for the given path.
	 *
	 * @param path
	 *            directory to create listing
	 * @return paths found within directory and its subdirectories
	 */
	public static Set<Path> list(Path path) {
		FuturesDirectoryListing workers = new FuturesDirectoryListing();
		workers.parse(path);
		workers.executor.shutdown();
		return workers.paths;
	}

	/**
	 * Tests the directory listing for the current directory.
	 *
	 * @param args
	 *            unused
	 */
	public static void main(String[] args) {
		System.out.println(list(Paths.get(".").normalize()));
	}

	/* --------------------------------------- */

	private static final Logger log = LogManager.getLogger();

	private final Set<Path> paths;
	private final ExecutorService executor;
	private final ExecutorCompletionService<Set<Path>> completor;
	private final AtomicInteger count;

	private FuturesDirectoryListing() {
		// only the main thread will add to this set, so no more synchronization
		this.paths = new HashSet<>();

		// this will create a thread pool of fixed size
		this.executor = Executors.newFixedThreadPool(WorkQueue.DEFAULT);

		// this will give us tasks as they complete (whatever order they
		// complete)
		this.completor = new ExecutorCompletionService<Set<Path>>(this.executor);

		// this tracks how many tasks we create total, we do not need to
		// synchronize access if we use an atomic integer
		this.count = new AtomicInteger();
	}

	private void parse(Path path) {
		// add to our completion service instead of the executor
		completor.submit(new DirectoryTask(path));

		// note the count can increase each for loop!
		for (int i = 0; i < count.get(); i++) {
			try {
				// the take() will grab the next completed task, or
				// force us to wait until the next task is completed
				Set<Path> local = completor.take().get();

				// this does not need to be synchronized, as only the main
				// thread is executing this code (not the workers)
				paths.addAll(local);
			}
			catch (InterruptedException | ExecutionException ex) {
				log.debug(ex.toString(), ex);
				break;
			}
		}
	}

	// notice this is now a callable object
	// the callable interface requires the type of object returned by call()
	private class DirectoryTask implements Callable<Set<Path>> {
		private final Path path;

		public DirectoryTask(Path path) {
			this.path = path;

			// keep track of the number of tasks created
			count.incrementAndGet();
		}

		@Override
		public Set<Path> call() {
			HashSet<Path> local = new HashSet<>();

			try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
				for (Path current : stream) {
					local.add(current);

					if (Files.isDirectory(current)) {
						// add to our completion service instead of the executor
						completor.submit(new DirectoryTask(current));
					}
				}
			}
			catch (IOException ex) {
				log.debug(ex.getMessage(), ex);
			}

			// notice call() returns a value, whereas run() does not!
			return local;
		}
	}

}
