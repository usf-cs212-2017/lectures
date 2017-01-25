import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Converts a file to a simple version of 1337-speak. The history behind this
 * "alternative" alphabet is as follows:
 *
 * <blockquote> One theory is that it was developed to defeat text filters
 * created by BBS or Internet Relay Chat system operators for message boards to
 * discourage the discussion of forbidden topics, like cracking and hacking.
 * Creative misspellings and ASCII-art-derived words were also a way to attempt
 * to indicate one was knowledgeable about the culture of computer users.
 * </blockquote>
 *
 * Demonstrates simple for loops, switch statements, method overloading, the
 * ternary operator, and line-by-line file reading and writing. Does not cover
 * exception handling in depth!
 *
 * @see <a href="https://en.wikipedia.org/wiki/Leet">Leet - Wikipedia</a>
 */
public class EliteFileConverter {

	/**
	 * Converts a letter to its 1337 representation, or randomizes the letter
	 * capitalization. Uses a {@code switch} statement to demonstrate how the
	 * {@code case} keyword works.
	 *
	 * @param letter
	 *            letter to convert
	 * @return letter converted to 1337-speak
	 */
	public static char toLeetSpeak(char letter) {
		boolean random = Math.random() < 0.5;

		switch (letter) {
			case 'a':
			case 'A':
				// ternary (3-way) operator
				// condition ? trueValue : falseValue
				// https://docs.oracle.com/javase/tutorial/java/nutsandbolts/op2.html
				return random ? '4' : '@';
			case 'e':
			case 'E':
				return '3';
			case 'i':
			case 'I':
				return '!';
			case 'l':
			case 'L':
				return '1';
			case 'o':
			case 'O':
				return '0';
			case 's':
			case 'S':
				return random ? '5' : '$';
			case 't':
			case 'T':
				return '7';
			default:
				return random ? Character.toLowerCase(letter) : Character.toUpperCase(letter);
		}
	}

	/**
	 * Randomly converts certain characters to a simple version of 1337-speak.
	 * The provided threshold will determine the percentage of letters that will
	 * attempt to be converted.
	 *
	 * @see #toLeetSpeak(char)
	 * @see #toLeetSpeak(String)
	 * @see #toLeetSpeak(String, double)
	 */
	public static String toLeetSpeak(String text, double threshold) {
		char[] chars = text.toCharArray();

		// traditional for loop
		// https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html
		for (int i = 0; i < chars.length; i++) {
			boolean random = Math.random() < threshold;
			chars[i] = random ? toLeetSpeak(chars[i]) : chars[i];
		}

		return String.valueOf(chars);
	}

	/**
	 * Randomly converts certain characters to a simple version of 1337-speak.
	 * Same as {@link #toLeetSpeak(String, double)} with a threshold of 0.5.
	 *
	 * @see #toLeetSpeak(char)
	 * @see #toLeetSpeak(String)
	 * @see #toLeetSpeak(String, double)
	 */
	public static String toLeetSpeak(String text) {
		// this is an example convenience method
		// https://en.wikipedia.org/wiki/Convenience_function
		return toLeetSpeak(text, 0.5);
	}

	/**
	 * Demonstrates a simple, but memory-intensive way to convert a text file to
	 * 1337-speak.
	 *
	 * @param input
	 *            - path to the input file
	 * @param output
	 *            - path to the output file
	 * @throws IOException
	 */
	public static void toLeetSpeakMemoryIntensive(Path input, Path output) throws IOException {
		// quote from api: "not intended for reading in large files"
		List<String> inputLines = Files.readAllLines(input, StandardCharsets.UTF_8);

		// creates another arraylist with same size
		List<String> outputLines = new ArrayList<String>(inputLines.size());

		// enhanced for loop (use these when possible)
		// https://docs.oracle.com/javase/tutorial/java/nutsandbolts/for.html
		for (String line : inputLines) {
			outputLines.add(toLeetSpeak(line));
		}

		Files.write(output, outputLines, StandardCharsets.UTF_8);
	}

	/**
	 * Demonstrates a better way to convert a text file to 1337-speak, making
	 * sure resources are closed and as little memory as possible is used. Does
	 * NOT perform its own exception handling!
	 *
	 * @param inqput
	 *            - path to the input file
	 * @param oautput
	 *            - path to the output file
	 * @throws IOException
	 */
	public static void toLeetSpeak(Path input, Path output) throws IOException {
		// try-with-resources
		// https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
		try (
				BufferedReader reader = Files.newBufferedReader(input, StandardCharsets.UTF_8);
				BufferedWriter writer = Files.newBufferedWriter(output, StandardCharsets.UTF_8);
		) {
			String line = null;

			// only 1 line needs to be in memory at a time
			while ((line = reader.readLine()) != null) {
				writer.write(toLeetSpeak(line));
				writer.newLine();
			}
		}

		// note: we can still throw exceptions (do not need to catch)
	}

	public static void main(String[] args) throws IOException {
		String text = "Sally sells seashells at the sea shore.";
		System.out.println(text);
		System.out.println(toLeetSpeak(text));
		System.out.println(toLeetSpeak(text, 0.25));
		System.out.println(toLeetSpeak(text, 1.00));

		Path input = Paths.get(".", "src", EliteFileConverter.class.getSimpleName() + ".java");
		Path output = Paths.get(".", "output", EliteFileConverter.class.getSimpleName() + ".txt");

		Files.createDirectories(output.getParent());
		toLeetSpeak(input, output);

		// throwing exceptions in main result in stack trace console output
		Path nowhere = Paths.get("nowhere");
		toLeetSpeak(nowhere, nowhere);
	}
}
