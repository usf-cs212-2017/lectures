import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Demonstrates how to use servlet contexts to configure which servlets handle
 * which requests.
 */
public class ContextServer {

	public static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		// Enable DEBUG logging
		System.setProperty("org.eclipse.jetty.LEVEL", "DEBUG");

		Server server = new Server(PORT);

		// Setup first context
		ServletContextHandler context1 = new ServletContextHandler();
		context1.setContextPath("/greetings");

		// All servlet paths will fall under first context
		// Hence under the /greetings path
		context1.addServlet(HelloServer.HelloServlet.class, "/hello");
		context1.addServlet(TodayServer.TodayServlet.class, "/");

		// Setup second context
		ServletContextHandler context2 = new ServletContextHandler();
		context2.setContextPath("/visits");

		// This servlet will handle all requests that start with /visits
		context2.addServlet(VisitServer.VisitServlet.class, "/");

		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { context1, context2 });

		server.setHandler(handlers);
		server.start();
		server.join();

		// http://localhost:8080/greetings/
		// http://localhost:8080/greetings/hello
		// http://localhost:8080/greetings/nowhere
		// http://localhost:8080/visits/
		// http://localhost:8080/visits/nowhere
	}
}
