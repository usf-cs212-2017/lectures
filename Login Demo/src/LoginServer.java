import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

/**
 * Demonstrates simple user registration, login, and session tracking. This is a
 * simplified example, and **NOT** secure.
 */
public class LoginServer {
	private static final Logger log = Log.getLog();
	private static int PORT = 8080;

	public static void main(String[] args) {
		Server server = new Server(PORT);

		ServletHandler handler = new ServletHandler();
		server.setHandler(handler);

		handler.addServletWithMapping(LoginUserServlet.class, "/login");
		handler.addServletWithMapping(LoginRegisterServlet.class, "/register");
		handler.addServletWithMapping(LoginWelcomeServlet.class, "/welcome");
		handler.addServletWithMapping(LoginRedirectServlet.class, "/*");

		log.info("Starting server on port " + PORT + "...");

		try {
			server.start();
			server.join();

			log.info("Exiting...");
		}
		catch (Exception ex) {
			log.warn("Interrupted while running server.", ex);
			System.exit(-1);
		}
	}
}
