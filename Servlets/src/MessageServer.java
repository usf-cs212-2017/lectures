import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class MessageServer {

	public static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		Server server = new Server(8080);

		ServletHandler handler = new ServletHandler();
		handler.addServletWithMapping(new ServletHolder(new MessageServlet()), "/pie");
		handler.addServletWithMapping(new ServletHolder(new MessageServlet()), "/cake");

		server.setHandler(handler);
		server.start();
		server.join();
	}
}
