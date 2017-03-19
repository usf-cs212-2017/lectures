import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class demonstrates very basic debugging approaches, including using a
 * debugger, assertions, println statements, a {@link Debug} class, and logging
 * using the log4j2 package.
 */
public class CharacterCounter {

	/**
	 * Size used for the character buffer. Set to a small size to force our code
	 * to loop to demonstrate debugging techniques.
	 */
	private static int SIZE = 1000;

	/**
	 * This method does not return the correct value. We can use a debugger to
	 * try and figure out what is going on, but we will not be able to use a
	 * debugger when we get to multithreading. We also have a silent exception,
	 * so there is no indication something went wrong!
	 *
	 * @param file
	 * @return number of characters
	 */
	public static int countNoDebug(Path file) {

		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (BufferedReader reader = Files.newBufferedReader(file, Charset.forName("UTF-8"));) {
			while (count >= 0) {
				count = reader.read(buffer);
				total += count;
			}
		}
		catch (IOException e) {

		}

		return total;
	}

	/**
	 * This method adds assertions to the previous approach. This requires the
	 * -ea flag (enable assertions flag) to be turned on to work. While this
	 * helps some, we need more information.
	 *
	 * Note: The bug is still present in this code.
	 *
	 * @param file
	 * @return number of characters
	 */
	public static int countAssertions(Path file) {

		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (BufferedReader reader = Files.newBufferedReader(file, Charset.forName("UTF-8"));) {
			while (count >= 0) {
				count = reader.read(buffer);
				total += count;
			}

			// post-condition assert statement
			assert count < 0;
		}
		catch (IOException e) {

		}

		// post-condition assert statement
		assert total >= 0;
		return total;
	}

	/**
	 * We can add print statements to help with debugging, but once we figure
	 * out the bug, we have to comment most of these statements out!
	 *
	 * Note: The bug is still present in this code.
	 *
	 * @param file
	 * @return number of characters
	 */
	public static int countWithPrintln(Path file) {

		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (BufferedReader reader = Files.newBufferedReader(file, Charset.forName("UTF-8"));) {
			while (count >= 0) {
				count = reader.read(buffer);
				System.out.println("Read " + count + " characters into buffer.");

				total += count;
				System.out.println("Total number of characters is now " + total + ".");
			}

			System.out.println("Finished reading file.");
			assert count < 0;
		}
		catch (IOException e) {
			System.out.println(e.toString());
		}

		assert total >= 0;
		return total;
	}

	/**
	 * This method replaces the println statements with those from a Debug
	 * class. We can now turn on or off all of these messages, but that is the
	 * only level of configuration we have.
	 *
	 * Note: The bug is still present in this code.
	 *
	 * @param file
	 * @return number of characters
	 */
	public static int countWithDebug(Path file) {

		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (BufferedReader reader = Files.newBufferedReader(file, Charset.forName("UTF-8"));) {
			while (count >= 0) {
				count = reader.read(buffer);
				Debug.println("Read " + count + " characters into buffer.");

				total += count;
				Debug.println("Total number of characters is now " + total + ".");
			}

			Debug.println("Finished reading file.");
			assert count < 0;
		}
		catch (IOException e) {
			Debug.println(e.toString());
		}

		assert total >= 0;
		return total;
	}

	/**
	 * Used by {@link #countWithLogging(Path)} to demonstrate the flexibility of
	 * logging with log4j.
	 */
	private static Logger logger = LogManager.getLogger(CharacterCounter.class);

	/**
	 * Demonstrates how we can use Log4j instead of a Debug class to help with
	 * debugging our method. Assumes the log4j2.xml is in the classpath!
	 *
	 * Note: The bug is still present in this code.
	 *
	 * @param file
	 * @return number of characters
	 */
	public static int countWithLogging(Path file) {
		logger.debug("Counting characters in file \"{}\".", file.getFileName());

		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (BufferedReader reader = Files.newBufferedReader(file, Charset.forName("UTF-8"));) {
			logger.trace("Opened file successfully.");

			while (count >= 0) {
				count = reader.read(buffer);
				logger.debug("Read {} characters into buffer.", count);

				total += count;
				logger.debug("Total number of characters is now {}.", total);
			}

			logger.trace("Finished reading file.");
			assert count < 0;
		}
		catch (IOException e) {
			logger.warn("Unable to count characters in \"{}\".", file.getFileName().toString());
			logger.debug("Unable to count characters.", e);
		}

		logger.debug("Found {} characters total.", total);
		assert total >= 0;
		return total;
	}

	/**
	 * Final version of this class with the bug fixed but the logging messages
	 * remain in place. We added some assert statements and modified the code a
	 * bit as well. We can disable these messages entirely in log4j2.xml.
	 *
	 * @param file
	 * @return number of characters
	 */
	public static int countCharacters(Path file) {
		logger.debug("Counting characters in file \"{}\".", file.getFileName());

		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		try (BufferedReader reader = Files.newBufferedReader(file, Charset.forName("UTF-8"));) {
			logger.trace("Opened file successfully.");

			while (count >= 0) {
				total += count;
				logger.debug("Total number of characters is now {}.", total);

				count = reader.read(buffer);
				logger.debug("Read {} characters into buffer.", count);
			}

			logger.trace("Finished reading file.");
			assert count < 0;
		}
		catch (IOException e) {
			logger.warn("Unable to count characters in \"{}\".", file.getFileName().toString());
			logger.debug("Unable to count characters.", e);

			return -1;
		}

		logger.debug("Found {} characters total.", total);
		assert total >= 0;
		return total;
	}

	/**
	 * Tests out two paths, one that is not a file, and one that is a file. To
	 * get how many characters are expected to be returned for this file, look
	 * at its properties in Eclipse.
	 *
	 * @param args
	 *            unused
	 */
	public static void main(String[] args) {
		Path path1 = Paths.get("src");
		Path path2 = Paths.get("src", "CharacterCounter.java");

		// System.out.println("No Output Messages:");
		// System.out.println(countNoDebug(path1));
		// System.out.println(countNoDebug(path2));
		// System.out.println();

		// System.out.println("With Assertions:");
		// System.out.println(countAssertions(path1));
		// System.out.println(countAssertions(path2));
		// System.out.println();

		// System.out.println("With Println Statements:");
		// System.out.println(countWithPrintln(path1));
		// System.out.println(countWithPrintln(path2));
		// System.out.println();

		// Debug.on = true;
		// System.out.println("With Debug Statements:");
		// System.out.println(countWithDebug(path1));
		// System.out.println(countWithDebug(path2));
		// System.out.println();

		// System.out.println("With Logging:");
		// System.out.println(countWithLogging(path1));
		// System.out.println(countWithLogging(path2));
		// System.out.println();

		System.out.println("Final Version:");
		System.out.println(countCharacters(path1));
		System.out.println(countCharacters(path2));
		System.out.println();
	}
}
