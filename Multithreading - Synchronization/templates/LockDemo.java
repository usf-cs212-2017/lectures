@SuppressWarnings("unused")
public class LockDemo {

	private final Thread worker1;
	private final Thread worker2;

	private final static Object staticLock = new Object();
	private final Object outerLock;
	private final Object multiLock;

	public LockDemo(String name) {
		outerLock = new Object();
		multiLock = new Object();

		// TODO
		worker1 = new Worker(LockDemo.class);
		worker2 = new Worker(LockDemo.Worker.class);

		worker1.setName(name + "1");
		worker2.setName(name + "2");

		worker1.start();
		worker2.start();
	}

	public void joinAll() throws InterruptedException {
		worker1.join();
		worker2.join();
	}

	private class Worker extends Thread {

		private final Object lock;

		public Worker(Object lock) {
			this.lock = lock; // TODO
		}

		@Override
		public void run() {
			synchronized (lock) { // TODO
				System.out.println(this.getName() + ": " + Thread.holdsLock(lock));

				try {
					// This thread will keep its lock while sleeping!
					Thread.sleep(1000);
				}
				catch (InterruptedException ex) {
					Thread.currentThread().interrupt();
				}
			}

			System.out.println(this.getName() + ": " + Thread.holdsLock(lock));
		}
	}

	public static void main(String[] args) throws InterruptedException {
		LockDemo demo1 = new LockDemo("A");
		LockDemo demo2 = new LockDemo("B");

		demo1.joinAll();
		demo2.joinAll();
	}
}
