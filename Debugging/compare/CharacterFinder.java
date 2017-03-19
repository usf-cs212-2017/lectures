import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CharacterFinder {

	/** Logger used for all output generated in this class. */
	private static final Logger log = LogManager.getLogger();

	/**
	 * Takes a line-by-line approach to counting the number of times a specific
	 * character appears in a file.
	 *
	 * @param file
	 *            file path to open and read
	 * @param character
	 *            character to search for in text
	 * @param ignoreCase
	 *            turns on or off case-insensitive search
	 * @return number of times the character was found
	 * @throws IOException
	 *             if unable to read file
	 */
	public static int findCharacter(Path file, char character, boolean ignoreCase) throws IOException {
		int count = 0;

		// note that we are outputting log messages
		log.debug("Searching for \"{}\" in \"{}\" (ignore case: {}).", character, file, ignoreCase);

		// note the try-with-resources block **without** a catch statement
		// note the use of Files.newBufferedReader() to create the reader
		try (BufferedReader reader = Files.newBufferedReader(file, Charset.forName("UTF-8"));) {
			String line;

			// read one line at a time until hit end of file (null)
			while ((line = reader.readLine()) != null) {

				// convert to lowercase if ignoring case
				if (ignoreCase) {
					line = line.toLowerCase();
				}

				// loop through every character in line
				for (char current : line.toCharArray()) {
					if (current == character) {
						count++;
					}
				}
			}
		}

		log.debug("Found {} instances of \"{}\" in \"{}\".", count, character, file);
		return count;
	}

	/**
	 * Performs some sanity-checks of the
	 * {@link #findCharacter(Path, char, boolean)} method.
	 *
	 * @param args
	 *            unused
	 */
	public static void main(String[] args) {
		Path sherlock = Paths.get("text", "pg1661.txt").normalize();

		char lowerC = 'c';
		char upperC = 'C';

		try {
			int countLowers = findCharacter(sherlock, lowerC, false);
			int countUppers = findCharacter(sherlock, upperC, false);
			int countIgnore = findCharacter(sherlock, lowerC, true);

			assert countLowers + countUppers == countIgnore;

			// console output only if info messages enabled
			log.info("Found {} instances of \"{}\" characters.", countLowers, lowerC);
			log.info("Found {} instances of \"{}\" characters.", countUppers, upperC);
			log.info("Found {} instances of \"{}\" characters (ignore case).", countIgnore, lowerC);
		}
		catch (IOException e) {
			// note the console-friendly warning
			log.warn("Unable to count characters in \"{}\".", sherlock);

			// note the debug-friendly stack trace
			log.debug(e.getMessage(), e);
		}
	}

}
