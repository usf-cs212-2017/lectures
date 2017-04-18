import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * A simple example of using Jetty and servlets to track the number of visitors
 * to a web page, demonstrating that this code is run in a multi-threaded
 * setting.
 */
public class VisitServer {

	public static final int PORT = 8080;

	// Keep in mind our server is multithreaded, so we need to use
	// storage safe for access by multiple threads simultaneously
	private static AtomicInteger visits = new AtomicInteger();

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);

		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(VisitServlet.class, "/");

		server.setHandler(handler);
		server.start();
		server.join();
	}

	@SuppressWarnings("serial")
	public static class VisitServlet extends HttpServlet {

		private static final String TITLE = "Visits";

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			// Output request for debugging (better if use logger)
			System.out.println(Thread.currentThread().getName() + ": " + request.getRequestURI());

			// Check to make sure the browser is not requesting favicon.ico
			if (request.getRequestURI().endsWith("favicon.ico")) {
				response.sendError(HttpServletResponse.SC_NOT_FOUND);
				return;
			}

			response.setContentType("text/html");

			PrintWriter out = response.getWriter();
			out.printf("<html>%n");
			out.printf("<head><title>%s</title></head>%n", TITLE);
			out.printf("<body>%n");

			// Safely increment the number of visits to this website
			int current = visits.incrementAndGet();
			out.printf("<p>There have been %d visits to this page.%n", current);

			// Demonstrate that this servlet is called by different threads
			out.printf("<p>This request was handled by thread %s.</p>%n", Thread.currentThread().getName());

			out.printf("</body>%n");
			out.printf("</html>%n");

			response.setStatus(HttpServletResponse.SC_OK);
		}
	}
}
