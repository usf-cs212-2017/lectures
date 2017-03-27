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
public class DirectorySizeCalculator {

	private static final Logger logger = LogManager.getLogger();

	private long files;
	private long bytes;

	/**
	 * Initializes the number of files found and total bytes found to 0.
	 */
	public DirectorySizeCalculator() {
		this.files = 0;
		this.bytes = 0;
	}

	/**
	 * Resets the counters, allowing this object to be easily reused if desired.
	 */
	public void reset() {
		this.bytes = 0;
		this.files = 0;

		logger.debug("Counters reset");
	}

	/**
	 * Returns the number of files found since the last reset.
	 *
	 * @return number of files
	 */
	public long files() {
		logger.debug("Getting files");
		return this.files;
	}

	/**
	 * Returns the number of bytes found since the last reset.
	 *
	 * @return number of bytes
	 */
	public long bytes() {
		logger.debug("Getting bytes");
		return this.bytes;
	}

	/**
	 * Adds the number of files and bytes in the specified path to the current
	 * total.
	 *
	 * @param path
	 */
	public void addPath(Path path) {
		try {
			if (Files.isDirectory(path)) {
				processDirectory(path);
			}
			else if (Files.exists(path)) {
				updateCounters(1, Files.size(path));
			}
		}
		catch (IOException e) {
			logger.warn("Unable to calculate size for {}", path);
			logger.catching(Level.DEBUG, e);
		}
	}

	/**
	 * Handles per-directory parsing. If a subdirectory is encountered, a
	 * recursive call will handle that subdirectory.
	 */
	private void processDirectory(Path directory) {
		assert Files.isDirectory(directory);

		logger.debug("Processing {}", directory);

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
			for (Path path : stream) {
				// looks familiar, right? we could call addDirectory(path)
				// but lets keep this as-is because it will be helpful for
				// multithreading
				if (Files.isDirectory(path)) {
					processDirectory(path);
				}
				else {
					updateCounters(1, Files.size(path));
				}
			}
		}
		catch (IOException e) {
			logger.warn("Unable to parse {}", directory);
			logger.catching(Level.DEBUG, e);
		}
	}

	/**
	 * Updates the number of files and bytes found. Not entirely necessary for
	 * this example, but becomes useful when we move to multithreading.
	 *
	 * @param files
	 * @param bytes
	 */
	private void updateCounters(long files, long bytes) {
		assert files >= 0;
		assert bytes >= 0;

		this.files += files;
		this.bytes += bytes;
		logger.debug("Counters are {} files and {} bytes", this.files, this.bytes);
	}

	/**
	 * Runs a simple example to demonstrate this class. Try changing the path to
	 * your root directory, and see how long it takes!
	 *
	 * @param args
	 *            unused
	 */
	public static void main(String[] args) {
		DirectorySizeCalculator demo = new DirectorySizeCalculator();
		demo.addPath(Paths.get("."));

		System.out.println(demo.files() + " files");
		System.out.println(demo.bytes() + " bytes");
	}
}
