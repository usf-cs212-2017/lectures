import java.util.LinkedList;

public class WorkQueue {

	private final PoolWorker[] workers;
	private final LinkedList<Runnable> queue;

	private volatile boolean shutdown;
	public static final int DEFAULT = 5;

	public WorkQueue() {
		this(DEFAULT);
	}

	public WorkQueue(int threads) {
		this.queue = new LinkedList<Runnable>();
		this.workers = new PoolWorker[threads];

		shutdown = false;

		for (int i = 0; i < threads; i++) {
			workers[i] = new PoolWorker();
			workers[i].start();
		}
	}

	public void execute(Runnable r) {
		synchronized (queue) {
			queue.addLast(r);
			queue.notifyAll();
		}
	}

	public void shutdown() {
		shutdown = true;

		synchronized (queue) {
			queue.notifyAll();
		}
	}

	public int size() {
		return workers.length;
	}

	private class PoolWorker extends Thread {

		@Override
		public void run() {
			Runnable r = null;

			while (true) {
				synchronized (queue) {
					while (queue.isEmpty() && !shutdown) {
						try {
							queue.wait();
						}
						catch (InterruptedException ex) {
							System.err.println("Warning: Work queue interrupted " + "while waiting.");
							Thread.currentThread().interrupt();
						}
					}

					if (shutdown) {
						break;
					}
					else {
						r = queue.removeFirst();
					}
				}

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
