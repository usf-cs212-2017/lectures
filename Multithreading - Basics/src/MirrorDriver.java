import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Illustrates how to use a generic type, basic synchronization, and the use of
 * {@link Thread#wait()} and {@link Thread#notifyAll()} to create a thread-safe
 * data structure. Uses the produce-consumer model to demo this data structure
 * with multiple threads.
 *
 * @see MirrorDriver
 * @see BoundedBuffer
 * @see ProducerThread
 * @see ConsumerThread
 */
public class MirrorDriver {

	private final BoundedBuffer<String> buffer;
	private final ProducerThread producer;
	private final ConsumerThread consumer;

	private static final Logger logger = LogManager.getLogger();
	private static final String EXIT = "exit";

	/**
	 * Creates an empty bounded buffer, and starts the producer and consumer
	 * threads.
	 */
	public MirrorDriver() {
		buffer = new BoundedBuffer<String>(5);
		producer = new ProducerThread();
		consumer = new ConsumerThread();

		producer.start();
		consumer.start();
	}

	/**
	 * Produces elements by scanning the console for user input. When found,
	 * splits the input into individual words and adds each word to the bounded
	 * buffer.
	 */
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
					if (words.length > 1) {
						buffer.putAll(words);
					}
					else {
						buffer.put(input);

						if (input.toLowerCase().equals(EXIT)) {
							break;
						}
					}
				}
				catch (Exception ex) {
					logger.debug("ProducerThread.run() encounterd exception!");
					break;
				}
			}
		}
	}

	/**
	 * Consumes elements from the bounded buffer, and outputs each element to
	 * the console in reverse.
	 */
	private class ConsumerThread extends Thread {

		public ConsumerThread() {
			super();
		}

		@Override
		public void run() {
			while (true) {
				try {
					String output = buffer.get();

					// output each element in reverse
					for (int i = output.length() - 1; i >= 0; i--) {
						System.out.print(output.charAt(i));
					}

					System.out.println();

					if (output.toLowerCase().equals(EXIT)) {
						break;
					}
				}
				catch (InterruptedException ex) {
					logger.debug("ConsumerThread.run() interrupted!");
					break;
				}
			}
		}
	}

	/*
	 * TEST CASES: ab cd ef gh ij kl <--> ba dc fe hg ji lk stressed wolf <-->
	 * desserts flow pool tool <--> loop loot deer keep <--> reed peek racecar
	 * <--> racecar
	 */

	public static void main(String[] args) {
		new MirrorDriver();
	}
}
