import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class PoorHTTPFetcher {

	public static List<String> get(String link) throws UnknownHostException, IOException {
		ArrayList<String> lines = new ArrayList<>();

		// TODO

		return lines;
	}

	public static void main(String[] args) throws Exception {
		String valid = "http://www.cs.usfca.edu/~sjengle/archived.html";
		String invalid = "http://www.cs.usfca.edu/~sjengle/nowhere.html";

		System.out.println(valid);
		System.out.println(get(valid));

		System.out.println();

		System.out.println(invalid);
		System.out.println(get(invalid));
	}
}
