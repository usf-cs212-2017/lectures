import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * A simple example of using Jetty and servlets to create a dynamic web page.
 * The web page will display the current date/time when loaded.
 */
public class TodayServer {

	public static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		// Create the Jetty server
		Server server = new Server();

		// Setup the connector component
		ServerConnector connector = new ServerConnector(server);
		connector.setHost("localhost");
		connector.setPort(PORT);

		// Setup the handler component
		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(TodayServlet.class, "/today");

		// Configure server to use connector and handler
		server.addConnector(connector);
		server.setHandler(handler);

		// Start the server (it is a thread) and wait for it to complete
		server.start();
		server.join();
	}

	/**
	 * A simple servlet that will display the current date and time when loaded.
	 */
	@SuppressWarnings("serial")
	public static class TodayServlet extends HttpServlet {

		/** Title of web page. */
		private static final String TITLE = "Today";

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			// Output request for debugging (better if use logger)
			System.out.println(Thread.currentThread().getName() + ": " + request.getRequestURI());

			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			out.printf("<html>%n");
			out.printf("<head><title>%s</title></head>%n", TITLE);
			out.printf("<body>%n");

			out.printf("<p>It is %s.</p>%n", getDate());

			out.printf("</body>%n");
			out.printf("</html>%n");

			response.setStatus(HttpServletResponse.SC_OK);
		}
	}

	/**
	 * Returns the date and time in a long format. For example: "12:00 am on
	 * Saturday, January 01 2000".
	 *
	 * @return current date and time
	 */
	public static String getDate() {
		String format = "hh:mm a 'on' EEEE, MMMM dd yyyy";
		DateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(new Date());
	}
}
