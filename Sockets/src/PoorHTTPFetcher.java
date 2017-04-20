import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * An example class designed to make fetching the results of different HTTP
 * operations easier.
 */
public class PoorHTTPFetcher {

	public static List<String> get(String link) throws UnknownHostException, IOException {
		ArrayList<String> lines = new ArrayList<>();

		// convert String into URL object
		URL url = new URL(link);

		// grab URL components needed for HTTP GET request
		String host = url.getHost();
		String resource = url.getFile().isEmpty() ? "/" : url.getFile();

		// craft HTTP GET request
		String request = "GET " + resource + " HTTP/1.1\r\n";
		request += "Host: " + host + "\r\n";
		request += "Connection: close\r\n";
		request += "\r\n";

		// attempt to send/receive request
		try (
				// create socket
				Socket socket = new Socket(url.getHost(), 80);

				// get socket writer (to send request to server)
				PrintWriter writer = new PrintWriter(socket.getOutputStream());

				// get socket reader (to receive response from server)
				BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		) {
			// send request to server
			writer.println(request);
			writer.flush();

			String line = null;

			// fetch response from server
			while ((line = reader.readLine()) != null) {
				lines.add(line);
			}
		}

		return lines;
	}

	/*
	 * ISSUES: Works only with GET requests. Headers included in list. What if
	 * want to test if valid HTML file? What about images?
	 */

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
