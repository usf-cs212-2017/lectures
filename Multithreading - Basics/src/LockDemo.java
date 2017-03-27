/**
 * This class demonstrates the impact different types of lock objects have on
 * whether multiple threads may run synchronized blocks of code simultaneously.
 */
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

		/*
		 * Comment in/out different examples and see how the behavior changes.
		 */

		// TF TF TF TF
		worker1 = new Worker(LockDemo.class);
		worker2 = new Worker(LockDemo.Worker.class);

		// TF TF TF TF
		// worker1 = new Worker(staticLock);
		// worker2 = new Worker(staticLock);

		// TF TF TF TF
		// worker1 = new Worker(LockDemo.class);
		// worker2 = new Worker(LockDemo.class);

		// TT FF TT FF
		// worker1 = new Worker(outerLock);
		// worker2 = new Worker(outerLock);

		// TT FF TT FF
		// worker1 = new Worker(this);
		// worker2 = new Worker(this);

		// TT TT FF FF
		// worker1 = new Worker(new Object());
		// worker2 = new Worker(new Object());

		// TT TT FF FF
		// worker1 = new Worker(outerLock);
		// worker2 = new Worker(multiLock);

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
			this.lock = lock;
			// this.lock = new Object();
		}

		@Override
		public void run() {
			// synchronized(this)
			synchronized (lock) {
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

		/*
		 * We have the following threads TRYING to run at the same time:
		 *
		 * +---LockDemo A---+ +---LockDemo B---+ | +-A1-+ +-A2-+ | | +-B1-+
		 * +-B2-+ | | | | | | | | | | | | | | +----+ +----+ | | +----+ +----+ |
		 * +----------------+ +----------------+
		 *
		 * Whether A1, A2, B1, B2 are able to run simultaneously (e.g. threads
		 * are able to enter the "locked rooms" setup by each thread) depends on
		 * the type of lock used.
		 */
	}
}
