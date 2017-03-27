import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DirectorySizeCalculator {

	private static final Logger logger = LogManager.getLogger();

	private long files;
	private long bytes;

	public DirectorySizeCalculator() {
		this.files = 0;
		this.bytes = 0;
	}

	public void reset() {
		this.bytes = 0;
		this.files = 0;

		logger.debug("Counters reset");
	}

	public long files() {
		logger.debug("Getting files");
		return this.files;
	}

	public long bytes() {
		logger.debug("Getting bytes");
		return this.bytes;
	}

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

	private void processDirectory(Path directory) {
		logger.debug("Processing {}", directory);
		
		// TODO

		try (DirectoryStream<Path> stream = Files.newDirectoryStream(directory)) {
			// TODO
		}
		catch (IOException e) {
			logger.warn("Unable to parse {}", directory);
			logger.catching(Level.DEBUG, e);
		}
	}

	private void updateCounters(long files, long bytes) {
		// TODO

		this.files += files;
		this.bytes += bytes;
		logger.debug("Counters are {} files and {} bytes", this.files, this.bytes);
	}

	public static void main(String[] args) {
		DirectorySizeCalculator demo = new DirectorySizeCalculator();
		demo.addPath(Paths.get("."));

		System.out.println(demo.files() + " files");
		System.out.println(demo.bytes() + " bytes");
	}
}
