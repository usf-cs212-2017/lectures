import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Generates a directory listing using our simple work queue implementation.
 */
public class WorkQueueDirectoryListing {

	/**
	 * Returns a directory listing for the given path.
	 *
	 * @param path
	 *            directory to create listing
	 * @return paths found within directory and its subdirectories
	 */
	public static Set<Path> list(Path path) {
		WorkQueueDirectoryListing workers = new WorkQueueDirectoryListing();
		workers.parse(path);

		// since we create the work queue, we should shutdown the queue too
		workers.queue.shutdown();

		// return the value (we can access the private members!)
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
	private final WorkQueue queue;
	private int pending;

	private WorkQueueDirectoryListing() {
		this.paths = new HashSet<>();
		this.queue = new WorkQueue();
		this.pending = 0;
	}

	private void parse(Path path) {
		queue.execute(new DirectoryTask(path));

		// putting this here instead of a finally method since this is
		// a private class and we don't need the extra functionality
		synchronized (this) {
			while (pending != 0) {
				try {
					this.wait();
				}
				catch (InterruptedException ex) {
					log.debug(ex.getMessage(), ex);
				}
			}
		}
	}

	private synchronized void incrementPending() {
		pending++;
	}

	private synchronized void decrementPending() {
		assert pending > 0;
		pending--;

		if (pending == 0) {
			this.notifyAll();
		}
	}

	private synchronized void addAll(Set<Path> local) {
		this.paths.addAll(local);
	}

	private class DirectoryTask implements Runnable {

		private final Path path;

		public DirectoryTask(Path path) {
			this.path = path;
			incrementPending();
		}

		@Override
		public void run() {
			// make sure to use local "subresults"
			HashSet<Path> local = new HashSet<>();

			try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
				for (Path current : stream) {
					local.add(current);

					if (Files.isDirectory(current)) {
						queue.execute(new DirectoryTask(current));
					}
				}
			}
			catch (IOException ex) {
				log.debug(ex.getMessage(), ex);
			}

			// don't call blocking methods until absolutely have to
			addAll(local);
			decrementPending();
		}
	}
}
