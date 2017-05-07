import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class ContactServer {

	public static void main(String[] args) throws Exception {

		DatabaseConnector connector = new DatabaseConnector("database.properties");

		if (connector.testConnection()) {
			Server server = new Server(8080);

			ContextHandler defaultHandler = new ContextHandler("/favicon.ico");
			defaultHandler.setHandler(new DefaultHandler());

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
