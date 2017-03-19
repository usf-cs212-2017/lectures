import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CharacterCompare {

	private static final Logger log = LogManager.getLogger();

	public static int compareSequentially(Path file1, Path file2, char character) {
		log.debug("Comparing {} and {} sequentially.", file1, file2);

		int count1 = 0;
		int count2 = 0;

		try {
			count1 = CharacterFinder.findCharacter(file1, character, true);
			count2 = CharacterFinder.findCharacter(file2, character, true);
		}
		catch (IOException e) {
			log.debug(e.getMessage(), e);
		}

		log.debug("Files \"{}\" and \"{}\" have a difference of {} \"{}\" characters.", file1, file2, count1 - count2,
				character);

		return count1 - count2;
	}

	public static int compareConcurrently(Path file1, Path file2, char character) {
		log.debug("Comparing {} and {} concurrently.", file1, file2);

		// TODO

//		log.debug("Files \"{}\" and \"{}\" have a difference of {} \"{}\" characters.", file1, file2,
//				counter1.count - counter2.count, character);

		return -1; // TODO
	}

	public static void main(String[] args) {
		Path sherlock = Paths.get("text", "pg1661.txt").normalize();
		Path mobydick = Paths.get("text", "pg2701.txt").normalize();

		if (!Files.isReadable(sherlock) || !Files.isReadable(mobydick)) {
			log.error("Unable to read \"{}\" and \"{}\".", sherlock, mobydick);
			return;
		}

		char character = 'c';

		int countSequentially = compareSequentially(sherlock, mobydick, character);
		int countConcurrently = compareConcurrently(sherlock, mobydick, character);

		assert countSequentially == countConcurrently;

		log.info("The file \"{}\" has {} {} \"{}\" characters than \"{}\" does.", sherlock.getFileName(),
				Math.abs(countSequentially), countSequentially > 0 ? "more" : "less", character,
				mobydick.getFileName());
	}

}
