import java.util.LinkedList;

public class WorkQueue {

	private final PoolWorker[] workers;
	private final LinkedList<Runnable> queue;

	public static final int DEFAULT = 5;

	public WorkQueue() {
		this(DEFAULT);
	}

	public WorkQueue(int threads) {
		this.queue = new LinkedList<Runnable>();
		this.workers = new PoolWorker[threads];

		// TODO
	}

	public void execute(Runnable r) {
		// TODO
	}

	public int size() {
		return workers.length;
	}

	private class PoolWorker extends Thread {

		@Override
		public void run() {
			Runnable r = null;

			while (true) {
				// TODO

				try {
					r.run();
				}
				catch (RuntimeException ex) {
					System.err.println("Warning: Work queue encountered an " + "exception while running.");
				}
			}
		}
	}
}
