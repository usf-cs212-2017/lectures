import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HTTPFetcher {
	public static final int DEFAULT_PORT = 80;
	public static final String version = "HTTP/1.1";

	public static enum HTTP {
		OPTIONS, GET, HEAD, POST, PUT, DELETE, TRACE, CONNECT
	};

	public static List<String> fetchLines(URL url, String request) throws UnknownHostException, IOException {
		ArrayList<String> lines = new ArrayList<>();

		// TODO

		return lines;
	}

	public static String craftHTTPRequest(URL url, HTTP type) {
		String host = url.getHost();
		String resource = url.getFile().isEmpty() ? "/" : url.getFile();

		return String.format("%s %s %s\n" + "Host: %s\n" + "Connection: close\n" + "\r\n", type.name(), resource,
				version, host);
	}

	public static String fetchHeaders(String url) throws UnknownHostException, MalformedURLException, IOException {
		URL target = new URL(url);
		String request = null; // TODO
		List<String> lines = fetchLines(target, request);

		return String.join(System.lineSeparator(), lines);
	}

	public static String fetchAll(String url) throws UnknownHostException, MalformedURLException, IOException {
		URL target = new URL(url);
		String request = null; // TODO
		List<String> lines = fetchLines(target, request);

		return String.join(System.lineSeparator(), lines);
	}

	public static String fetchHTML(String url) throws UnknownHostException, MalformedURLException, IOException {
		URL target = new URL(url);
		String request = craftHTTPRequest(target, HTTP.GET);
		List<String> lines = fetchLines(target, request);

		// TODO

		return null;
	}

	public static Map<String, String> parseHeaders(List<String> headers) {
		Map<String, String> fields = new HashMap<>();

		// TODO

		return fields;
	}

	public static Map<String, String> getHeaderFields(String url)
			throws UnknownHostException, MalformedURLException, IOException {
		URL target = new URL(url);
		String request = craftHTTPRequest(target, HTTP.HEAD);
		List<String> lines = fetchLines(target, request);

		return parseHeaders(lines);
	}

	public static void main(String[] args) throws Exception {
		String url = "http://www.cs.usfca.edu/~sjengle/archived.html";
		System.out.println("***** HEADERS *****");
		System.out.println(fetchHeaders(url));
		System.out.println();

//		System.out.println("***** FIELDS *****");
//		System.out.println(getHeaderFields(url));
//		System.out.println();
//
//		System.out.println("***** HEADERS and HTML *****");
//		System.out.println(fetchAll(url));
//		System.out.println();
//
//		System.out.println("***** HTML *****");
//		System.out.println(fetchHTML(url));
//		System.out.println();
//
//		String image = "http://www.cs.usfca.edu/~sjengle/images/olivetrees.jpg";
//
//		System.out.println("**** IMAGE HEADERS *****");
//		System.out.println(fetchHeaders(image));
//		System.out.println();
//
//		System.out.println("**** IMAGE HTML *****");
//		System.out.println(fetchHTML(image));
	}
}
