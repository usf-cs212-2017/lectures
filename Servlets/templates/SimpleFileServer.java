import org.eclipse.jetty.server.Server;

public class SimpleFileServer {

	public static final int PORT = 8080;

	public static void main(String[] args) throws Exception {
//		System.setProperty("org.eclipse.jetty.LEVEL", "DEBUG");

		Server server = new Server(PORT);

		// TODO

		server.start();
		server.join();
	}
}
