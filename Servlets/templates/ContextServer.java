import org.eclipse.jetty.server.Server;

public class ContextServer {

	public static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
		Server server = new Server(PORT);

		// TODO

		server.start();
		server.join();
	}
}
