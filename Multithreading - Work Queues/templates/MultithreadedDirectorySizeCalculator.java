import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MultithreadedDirectorySizeCalculator {

	private static final Logger logger = LogManager.getLogger();

	private final WorkQueue minions;
	private int pending;

	private long files;
	private long bytes;

	public MultithreadedDirectorySizeCalculator() {
		minions = new WorkQueue();
		pending = 0;

		files = 0;
		bytes = 0;
	}

	public void reset() { // TODO
		// TODO
		bytes = 0;
		files = 0;
		logger.debug("Counters reset");
	}

	public long files() { // TODO
		logger.debug("Getting files");
		// TODO
		return files;
	}

	public long bytes() { // TODO
		logger.debug("Getting bytes");
		// TODO
		return bytes;
	}

	public void addDirectory(Path directory) {
		try {
			// TODO
		}
		catch (IOException e) {
			logger.warn("Unable to calculate size for {}", directory);
			logger.catching(Level.DEBUG, e);
		}
	}

	private class DirectoryMinion implements Runnable {

		private Path directory;

		public DirectoryMinion(Path directory) {
			logger.debug("Minion created for {}", directory);
			this.directory = directory;

			// TODO
		}

		@Override
		public void run() {
			try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
				// TODO
			}
			catch (IOException e) {
				logger.warn("Unable to parse {}", directory);
				logger.catching(Level.DEBUG, e);
			}

			logger.debug("Minion finished {}", directory);
		}
	}

	private void updateCounters(long files, long bytes) { // TODO
		this.files += files;
		this.bytes += bytes;
		logger.debug("Counters are {} files and {} bytes", this.files, this.bytes);
	}

	public static void main(String[] args) {
		MultithreadedDirectorySizeCalculator demo = new MultithreadedDirectorySizeCalculator();
		demo.addDirectory(Paths.get("."));

		System.out.println(demo.files() + " files");
		System.out.println(demo.bytes() + " bytes");

		// demo.shutdown();
	}
}
