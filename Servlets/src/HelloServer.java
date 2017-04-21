import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 * Demonstrates the danger of using user-input in a web application, especially
 * regarding cross-site scripting (XSS) attacks.
 */
public class HelloServer {

	public static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);

		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(HelloServlet.class, "/hello");
		handler.addServletWithMapping(TodayServer.TodayServlet.class, "/today");

		server.setHandler(handler);
		server.start();
		server.join();
	}

	public static String dayOfWeek() {
		return Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.ENGLISH);
	}

	@SuppressWarnings("serial")
	public static class HelloServlet extends HttpServlet {

		private static final String TITLE = "Hello";

		@Override
		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			response.setContentType("text/html");
			response.setStatus(HttpServletResponse.SC_OK);

			PrintWriter out = response.getWriter();
			out.printf("<html>%n");
			out.printf("<head><title>%s</title></head>%n", TITLE);
			out.printf("<body>%n");

			String name = request.getParameter("name");
			name = name == null ? "anonymous" : name;

			/*
			 * This can lead to a cross-site scripting attack! NEVER directly
			 * use input from the user in your webpage.
			 *
			 * JavaScript:
			 * ?name=<script>window.open("http://www.usfca.edu/");</script>
			 * ?name=<script>alert("Mwahaha");</script>
			 *
			 * Some browsers are smart enough to not load Javascript found
			 * within the URL request... so these examples may no longer work.
			 * We can disable this security feature momentarily using:
			 *
			 * response.setIntHeader("X-XSS-Protection", 0);
			 *
			 * Note: Please don't actually do that :(
			 */

			response.setIntHeader("X-XSS-Protection", 0);

			out.printf("<h1>Hello, %s!</h1>%n", name);
			out.printf("<p>Thank you for visiting on this fine %s.</p>%n", dayOfWeek());

			out.printf("</body>%n");
			out.printf("</html>%n");
		}
	}
}
