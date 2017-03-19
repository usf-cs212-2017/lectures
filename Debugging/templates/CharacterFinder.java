import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CharacterFinder {

	private static final Logger log = LogManager.getLogger();

	public static int findCharacter(Path file, char character, boolean ignoreCase) throws IOException {
		int count = 0;

		log.debug("Searching for \"{}\" in \"{}\" (ignore case: {}).", character, file, ignoreCase);

		// TODO

		log.debug("Found {} instances of \"{}\" in \"{}\".", count, character, file);
		return count;
	}

	public static void main(String[] args) {
		Path sherlock = Paths.get("text", "pg1661.txt").normalize();

		char lowerC = 'c';
		char upperC = 'C';

		try {
			int countLowers = findCharacter(sherlock, lowerC, false);
			int countUppers = findCharacter(sherlock, upperC, false);
			int countIgnore = findCharacter(sherlock, lowerC, true);

			assert countLowers + countUppers == countIgnore;

			log.info("Found {} instances of \"{}\" characters.", countLowers, lowerC);
			log.info("Found {} instances of \"{}\" characters.", countUppers, upperC);
			log.info("Found {} instances of \"{}\" characters (ignore case).", countIgnore, lowerC);
		}
		catch (IOException e) {
			log.warn("Unable to count characters in \"{}\".", sherlock);
			// TODO
		}
	}

}
