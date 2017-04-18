import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;

public class GalleryServer {

	public static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);

		// TODO

		server.start();
		server.join();
	}

	@SuppressWarnings("serial")
	public static class GalleryServlet extends HttpServlet {

		private static final String TITLE = "Gallery";
		private static final Path ROOT = Paths.get("web", "foto");

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			response.setContentType("text/html");

			PrintWriter out = response.getWriter();
			out.printf("<html>%n");
			out.printf("<head><title>%s</title></head>%n", TITLE);
			out.printf("<body>%n");

			// loop through the local directory and get all the jpg files
			try (
					DirectoryStream<Path> dir = Files.newDirectoryStream(ROOT.toAbsolutePath(),
							new DirectoryStream.Filter<Path>() {
								@Override
								public boolean accept(Path entry) throws IOException {
									return entry.toString().endsWith(".jpg");
								}
							})
			) {
				for (Path file : dir) {
					out.printf("<img src=\"images/%s\" width=\"150\" height=\"150\">%n", file.getFileName().toString());
				}
			}

			out.printf("<p>This request was handled by thread %s.</p>%n", Thread.currentThread().getName());

			out.printf("</body>%n");
			out.printf("</html>%n");

			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}
