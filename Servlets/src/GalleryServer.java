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

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Demonstrates one way to serve both dynamic and static resources. Requires a
 * local web/foto directory with several jpg images to function properly.
 */
public class GalleryServer {

	public static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);

		// add static resource holders to web server
		// this indicates where web files are accessible on the file system
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setResourceBase("web/foto");
		resourceHandler.setDirectoriesListed(true);

		// only serve static resources in the "/images" context directory
		// this indicates where web files are accessible via the web server
		ContextHandler resourceContext = new ContextHandler("/images");
		resourceContext.setHandler(resourceHandler);

		// all other requests should be handled by the gallery servlet
		ServletContextHandler servletContext = new ServletContextHandler();
		servletContext.setContextPath("/");
		servletContext.addServlet(GalleryServlet.class, "/");

		// setup handlers (and handler order)
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { resourceContext, servletContext });

		// order matters---if you swap the order, we no longer see the
		// individual files listed
//		handlers.setHandlers(new Handler[] {servletContext, resourceContext});

		server.setHandler(handlers);
		server.start();
		server.join();

		// http://localhost:8080/
		// http://localhost:8080/nowhere
		// http://localhost:8080/images
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
				// for each returned file, add to gallery page
				// assumes photos will be served out of the images/ directory
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
