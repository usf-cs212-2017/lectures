import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class EliteFileConverter {

	public static char toLeetSpeak(char letter) {
		boolean random = Math.random() < 0.5;

		switch (letter) {
			// TODO
			default:
				return random ? Character.toLowerCase(letter) : Character.toUpperCase(letter);
		}
	}

	public static String toLeetSpeak(String text, double threshold) {
		// TODO
		return null;
	}

	public static String toLeetSpeak(String text) {
		return toLeetSpeak(text, 0.5);
	}

	public static void toLeetSpeakMemoryIntensive(Path input, Path output) throws IOException {
		// TODO
	}

	public static void toLeetSpeak(Path input, Path output) throws IOException {
		// TODO
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

		Path nowhere = Paths.get("nowhere");
		toLeetSpeak(nowhere, nowhere);
	}
}
