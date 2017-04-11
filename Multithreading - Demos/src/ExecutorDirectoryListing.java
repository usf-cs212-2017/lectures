import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Generates a directory listing using multi-threading via executors and
 * built-in thread-safe objects (like synchronized sets and atomic integers)
 */
public class ExecutorDirectoryListing {

	/**
	 * Returns a directory listing for the given path.
	 *
	 * @param path
	 *            directory to create listing
	 * @return paths found within directory and its subdirectories
	 */
	public static Set<Path> list(Path path) {
		ExecutorDirectoryListing workers = new ExecutorDirectoryListing();
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
	private AtomicInteger pending;

	private ExecutorDirectoryListing() {
		// make this synchronized so we can just add without worry
		// note this is NOT safe to iterate through without manual
		// synchronization
		this.paths = Collections.synchronizedSet(new HashSet<>());

		// creates a built-in thread pool/work queue with the same size
		this.executor = Executors.newFixedThreadPool(WorkQueue.DEFAULT);

		// we can use an atomic integer without synchronization
		this.pending = new AtomicInteger();
	}

	private void parse(Path path) {
		executor.execute(new DirectoryTask(path));

		while (pending.get() != 0) {
			try {
				// we still need to synchronize our wait() and notifyAll()
				synchronized (pending) {
					pending.wait();
				}
			}
			catch (InterruptedException ex) {
				log.debug(ex.getMessage(), ex);
				break;
			}
		}
	}

	private class DirectoryTask implements Runnable {
		private final Path path;

		public DirectoryTask(Path path) {
			this.path = path;
			pending.incrementAndGet();
		}

		@Override
		public void run() {
			HashSet<Path> local = new HashSet<>();

			try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
				for (Path current : stream) {
					local.add(current);

					if (Files.isDirectory(current)) {
						executor.execute(new DirectoryTask(current));
					}
				}
			}
			catch (IOException ex) {
				log.debug(ex.getMessage(), ex);
			}

			// remember methods on paths is thread-safe
			paths.addAll(local);

			if (pending.decrementAndGet() == 0) {
				// we still need to synchronize our wait() and notifyAll()
				synchronized (pending) {
					pending.notifyAll();
				}
			}
		}
	}
}
