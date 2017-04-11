import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Demonstrates server-side and client-side sockets.
 *
 * @see SimpleServer
 * @see SimpleClient
 */
public class SimpleClient {

	public static void main(String[] args) throws IOException {
		try (
				Socket socket = new Socket("127.0.0.1", SimpleServer.PORT);
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				PrintWriter writer = new PrintWriter(socket.getOutputStream());
		) {
			System.out.println("Client: Started...");
			String input = null;

			while (!socket.isClosed()) {
				// read line from console
				input = reader.readLine();

				// send to server over socket
				writer.println(input);
				writer.flush();

				// check for shutdown cases
				if (input.equals(SimpleServer.EOT)) {
					System.out.println("Client: Ending client.");
					socket.close();
				}
				else if (input.equals(SimpleServer.EXIT)) {
					System.out.println("Client: Shutting down server.");
					socket.close();
				}
			}
		}
	}
}
