import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathDemo {

	private static final String format = "%22s: %s%n";

	public static void main(String[] args) throws IOException {

		// TODO
		
		// try ".", "..", and "~"
//		Path relativeDirectory = Paths.get("."); 
//		pathDetails(relativeDirectory);

//		Path absoluteDirectory = relativeDirectory.toAbsolutePath();
//		pathDetails(absoluteDirectory);

//		Path normalizedDirectory = absoluteDirectory.normalize();
//		pathDetails(normalizedDirectory);

//		Path srcDirectory = Paths.get(".", "src");
//		pathDetails(srcDirectory);

//		Path fileRelativePath = srcDirectory.resolve(PathDemo.class.getSimpleName() + ".java");
//		pathDetails(fileRelativePath);

//		Path fileCanonicalPath = fileRelativePath.toAbsolutePath().normalize();
//		pathDetails(fileCanonicalPath);

//		System.out.printf(format, "getRoot()", fileCanonicalPath.getRoot());
//		System.out.printf(format, "getNameCount()", fileCanonicalPath.getNameCount());
//		System.out.printf(format, "getName(0)", fileCanonicalPath.getName(0));
//		System.out.printf(format, "subpath(1, 3)", fileCanonicalPath.subpath(1, 3));
//		System.out.println();

//		fileDetails(normalizedDirectory);
//		fileDetails(fileCanonicalPath);
	}
	
	public static void pathDetails(Path path) {
		// TODO
		System.out.printf(format, "toString()", path.toString());
		System.out.printf(format, "isAbsolute()", path.isAbsolute());
		System.out.printf(format, "getParent()", path.getParent());
		System.out.printf(format, "getRoot()", path.getRoot());
		System.out.printf(format, "getFileName()", path.getFileName());
		System.out.printf(format, "normalize()", path.normalize());
		System.out.printf(format, "toAbsolutePath()", path.toAbsolutePath());
		System.out.println();
	}

	public static void fileDetails(Path path) throws IOException {
		System.out.printf(format, "toString()", path.toString());

		// Get basic information about the file.
		// TODO

		// Check whether the file is a directory or hidden.
		// TODO

		// Check the read/write/execute permissions of the file.
		// TODO
		
		System.out.println();
	}	
}
