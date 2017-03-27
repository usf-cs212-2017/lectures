import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Calculates the total number of files and bytes found in one or more
 * directories, and their subdirectories. Accumulates results.
 *
 * Used to demonstrate multithreading and how to use a work queue.
 *
 * @see DirectorySizeCalculator
 * @see MultithreadedDirectorySizeCalculator
 */
public class MultithreadedDirectorySizeCalculator {

	private static final Logger logger = LogManager.getLogger();

	private final WorkQueue minions;
	private int pending;

	private long files;
	private long bytes;

	/**
	 * Initializes the number of files found and total bytes found to 0. Also
	 * initializes a work queue with {@link WorkQueue#DEFAULT} threads.
	 */
	public MultithreadedDirectorySizeCalculator() {
		minions = new WorkQueue();
		pending = 0;

		files = 0;
		bytes = 0;
	}

	/**
	 * Resets the counters, allowing this object to be easily reused if desired.
	 * Note that we had to make this method synchronized in the multithreaded
	 * version.
	 */
	public synchronized void reset() {
		finish();
		bytes = 0;
		files = 0;
		logger.debug("Counters reset");
	}

	/**
	 * Returns the number of files found since the last reset. Note that we had
	 * to make this method synchronized in the multithreaded version.
	 *
	 * @return number of files
	 */
	public synchronized long files() {
		logger.debug("Getting files");
		finish();
		return files;
	}

	/**
	 * Returns the number of bytes found since the last reset. Note that we had
	 * to make this method synchronized in the multithreaded version.
	 *
	 * @return number of bytes
	 */
	public synchronized long bytes() {
		logger.debug("Getting bytes");
		finish();
		return bytes;
	}

	/**
	 * Helper method, that helps a thread wait until all of the current work is
	 * done. This is useful for resetting the counters or shutting down the work
	 * queue.
	 */
	public synchronized void finish() {
		try {
			while (pending > 0) {
				logger.debug("Waiting until finished");
				this.wait();
			}
		}
		catch (InterruptedException e) {
			logger.debug("Finish interrupted", e);
		}
	}

	/**
	 * Will shutdown the work queue after all the current pending work is
	 * finished. Necessary to prevent our code from running forever in the
	 * background.
	 */
	public synchronized void shutdown() {
		logger.debug("Shutting down");
		finish();
		minions.shutdown();
	}

	/**
	 * Adds the number of files and bytes in the specified directory to the
	 * current total.
	 *
	 * @param directory
	 */
	public void addDirectory(Path directory) {
		try {
			if (Files.isDirectory(directory)) {
				// Note that we now create a new runnable object and add it
				// to the work queue.
				minions.execute(new DirectoryMinion(directory));
			}
			else if (Files.exists(directory)) {
				updateCounters(1, Files.size(directory));
			}
		}
		catch (IOException e) {
			logger.warn("Unable to calculate size for {}", directory);
			logger.catching(Level.DEBUG, e);
		}
	}

	/**
	 * Handles per-directory parsing. If a subdirectory is encountered, a new
	 * {@link DirectoryMinion} is created to handle that subdirectory.
	 */
	private class DirectoryMinion implements Runnable {

		private Path directory;

		public DirectoryMinion(Path directory) {
			logger.debug("Minion created for {}", directory);
			this.directory = directory;

			// Indicate we now have "pending" work to do. This is necessary
			// so we know when our threads are "done", since we can no longer
			// call the join() method on them.
			incrementPending();
		}

		@Override
		public void run() {
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
				long files = 0;
				long bytes = 0;

				for (Path path : stream) {
					if (Files.isDirectory(path)) {
						// Note that we now create a new runnable object and add
						// it
						// to the work queue.
						minions.execute(new DirectoryMinion(path));
					}
					else {
						// This is inefficient, and causes a lot of blocking.
						// updateCounters(1, Files.size(path));

						// Note that we are adding to LOCAL variables, so we
						// only lock ONCE when we are done.
						files += 1;
						bytes += Files.size(path);
					}
				}

				// Now that we are done, go ahead and lock to update the
				// counter values.
				updateCounters(files, bytes);

				// Indicate that we no longer have "pending" work to do.
				decrementPending();
			}
			catch (IOException e) {
				logger.warn("Unable to parse {}", directory);
				logger.catching(Level.DEBUG, e);
			}

			logger.debug("Minion finished {}", directory);
		}
	}

	/**
	 * Updates the number of files and bytes found. Note that we had to make
	 * this method synchronized to work in the multithreaded version.
	 *
	 * @param files
	 * @param bytes
	 */
	private synchronized void updateCounters(long files, long bytes) {
		this.files += files;
		this.bytes += bytes;
		logger.debug("Counters are {} files and {} bytes", this.files, this.bytes);
	}

	/**
	 * Indicates that we now have additional "pending" work to wait for. We need
	 * this since we can no longer call join() on the threads. (The threads keep
	 * running forever in the background.)
	 *
	 * We made this a synchronized method in the outer class, since locking on
	 * the "this" object within an inner class does not work.
	 */
	private synchronized void incrementPending() {
		pending++;
		logger.debug("Pending is now {}", pending);
	}

	/**
	 * Indicates that we now have one less "pending" work, and will notify any
	 * waiting threads if we no longer have any more pending work left.
	 */
	private synchronized void decrementPending() {
		pending--;
		logger.debug("Pending is now {}", pending);

		if (pending <= 0) {
			this.notifyAll();
		}
	}

	/**
	 * Runs a simple example to demonstrate this class. Try changing the path to
	 * your root directory, and see how long it takes!
	 *
	 * @param args
	 *            unused
	 */
	public static void main(String[] args) {
		MultithreadedDirectorySizeCalculator demo = new MultithreadedDirectorySizeCalculator();
		demo.addDirectory(Paths.get("."));

		System.out.println(demo.files() + " files");
		System.out.println(demo.bytes() + " bytes");

		demo.shutdown();
	}
}
