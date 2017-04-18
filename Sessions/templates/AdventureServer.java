import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class AdventureServer {

	public static void main(String[] args) throws Exception {
		ServletContextHandler servletContext = null;

		// TODO

		DefaultHandler defaultHandler = new DefaultHandler();
		defaultHandler.setServeIcon(true);

		ContextHandler defaultContext = new ContextHandler("/favicon.ico");
		defaultContext.setHandler(defaultHandler);

		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { defaultContext, servletContext });

		Server server = new Server(8080);
		server.setHandler(handlers);
		server.start();
		server.join();
	}
}
