import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MirrorDriver {

	private final BoundedBuffer<String> buffer;
	private final ProducerThread producer;
	private final ConsumerThread consumer;

	private static final Logger logger = LogManager.getLogger();
	private static final String EXIT = "exit";

	public MirrorDriver() {
		buffer = new BoundedBuffer<String>(5);
		producer = new ProducerThread();
		consumer = new ConsumerThread();

		// TODO
	}

	private class ProducerThread extends Thread {

		private final Scanner scanner;

		public ProducerThread() {
			super();
			scanner = new Scanner(System.in);
		}

		@Override
		public void run() {
			while (true) {
				String input = scanner.nextLine();
				String[] words = input.split("\\s");

				try {
					// TODO
				}
				catch (Exception ex) {
					logger.debug("ProducerThread.run() encounterd exception!");
					break;
				}
			}
		}
	}

	private class ConsumerThread extends Thread {

		public ConsumerThread() {
			super();
		}

		@Override
		public void run() {
			while (true) {
				try {
					// TODO
				}
				catch (Exception ex) {
					logger.debug("ConsumerThread.run() interrupted!");
					break;
				}
			}
		}
	}

	public static void main(String[] args) {
		new MirrorDriver();
	}
}
