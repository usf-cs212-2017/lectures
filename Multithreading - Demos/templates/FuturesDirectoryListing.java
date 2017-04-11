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

public class FuturesDirectoryListing {

	public static Set<Path> list(Path path) {
		// TODO
		return null;
	}

	public static void main(String[] args) {
		System.out.println(list(Paths.get(".").normalize()));
	}

	/* --------------------------------------- */

	private static final Logger log = LogManager.getLogger();

	// TODO

}
