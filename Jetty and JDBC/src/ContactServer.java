import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * An example showing how to use the DatabaseConnector and JDBC to connect to a
 * database and retrieve information safely without directly using user input to
 * avoid an XSS or SQL injection attack.
 *
 * THIS ASSUMES YOU HAVE ALREADY SETUP THE CONTACT TABLES FROM THE EARLIER SQL DEMOS.
 *
 * @see DatabaseConenctor
 * @see ContactServer
 * @see ContactSimpleServlet
 * @see ContactComplexServlet
 */
public class ContactServer {

	public static void main(String[] args) throws Exception {

		DatabaseConnector connector = new DatabaseConnector("database.properties");

		// only start server if able to reach database
		if (connector.testConnection()) {
			Server server = new Server(8080);

			// default handler for favicon.ico requests
			ContextHandler defaultHandler = new ContextHandler("/favicon.ico");
			defaultHandler.setHandler(new DefaultHandler());

			// call servlet constructors so they use the same connector
			// use servlet holders to map servlet instances to contexts
			ServletHandler servletHandler = new ServletHandler();
			servletHandler.addServletWithMapping(new ServletHolder(new ContactSimpleServlet(connector)), "/simple");
			servletHandler.addServletWithMapping(new ServletHolder(new ContactComplexServlet(connector)), "/");

			HandlerList handlers = new HandlerList();
			handlers.addHandler(defaultHandler);
			handlers.addHandler(servletHandler);

			server.setHandler(handlers);
			server.start();
			server.join();
		}
	}
}
