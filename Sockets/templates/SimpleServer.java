public class SimpleServer {
	public static final int PORT = 5554;
	public static final String EOT = "EOT";
	public static final String EXIT = "SHUTDOWN";

	public static void main(String[] args) throws Exception {
		String input = null;

		try (
			AutoCloseable temp = new AutoCloseable() {@Override public void close() throws Exception {}};
		) {
//			System.out.println("Server: Waiting for connection...");

			// TODO

//				System.out.println("Server: Closing socket.");
//				System.out.println("Server: Shutting down.");
//				System.out.println("Server: Client disconnected.");
		}
	}
}
