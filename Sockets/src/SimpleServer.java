import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Demonstrates server-side and client-side sockets.
 *
 * @see SimpleServer
 * @see SimpleClient
 */
public class SimpleServer {
	public static final int PORT = 5554;
	public static final String EOT = "EOT";
	public static final String EXIT = "SHUTDOWN";

	public static void main(String[] args) throws IOException {
		String input = null;

		try (ServerSocket server = new ServerSocket(PORT);) {
			// keep looping to accept clients
			while (!server.isClosed()) {
				System.out.println("Server: Waiting for connection...");

				try (
						Socket socket = server.accept();
						BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				) {
					// while client is connected
					while (!socket.isClosed()) {
						// read line from client socket
						input = reader.readLine();
						System.out.println("Server: " + input);

						// check for shutdown cases
						if (input.equals(EOT)) {
							System.out.println("Server: Closing socket.");
							socket.close();
						}
						else if (input.equals(EXIT)) {
							System.out.println("Server: Shutting down.");
							socket.close();
							server.close();
						}
					}
				}

				System.out.println("Server: Client disconnected.");
			}
		}
	}
}
