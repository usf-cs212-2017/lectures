import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;

/**
 * Demonstrates how to use session tracking and enum types to create a simple
 * adventure game.
 *
 * @see AdventureServer
 * @see AdventureServlet
 * @see AdventureRoom
 * @see Direction
 */
public class AdventureServer {

	public static void main(String[] args) throws Exception {
		// type of handler that supports sessions
		ServletContextHandler servletContext = null;

		// turn on sessions and set context
		servletContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
		servletContext.setContextPath("/");
		servletContext.addServlet(AdventureServlet.class, "/");

		// default handler for favicon.ico requests
		DefaultHandler defaultHandler = new DefaultHandler();
		defaultHandler.setServeIcon(true);

		ContextHandler defaultContext = new ContextHandler("/favicon.ico");
		defaultContext.setHandler(defaultHandler);

		// setup handler order
		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { defaultContext, servletContext });

		// setup jetty server
		Server server = new Server(8080);
		server.setHandler(handlers);
		server.start();
		server.join();
	}
}
