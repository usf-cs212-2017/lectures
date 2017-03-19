import java.nio.file.Path;
import java.nio.file.Paths;

public class CharacterCounter {

	private static int SIZE = 1000;

	public static int countCharacters(Path file) {

		int count = 0;
		int total = 0;

		char[] buffer = new char[SIZE];

		// TODO

		return total;
	}

	public static void main(String[] args) {
		Path path1 = Paths.get("src");
		Path path2 = Paths.get("src", "CharacterCounter.java");

		System.out.println(countCharacters(path1));
		System.out.println(countCharacters(path2));
		System.out.println();
	}
}
