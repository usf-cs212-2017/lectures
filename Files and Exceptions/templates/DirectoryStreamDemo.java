import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class DirectoryStreamDemo {

	private static void traverse(String prefix, Path path) throws IOException {
		// TODO
	}

	public static void traverse(Path directory) throws IOException {
		if (Files.isDirectory(directory)) {
			// TODO
		}
		else {
			System.out.println(directory.getFileName());
		}
	}

	public static void main(String[] args) throws IOException {
		Path path = Paths.get(".").toAbsolutePath().normalize();
		System.out.println(path.getFileName() + ":");
		traverse(path);
	}

}
