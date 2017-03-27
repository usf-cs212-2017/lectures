import java.util.Arrays;

public class ThreadStateDemo {

	private Thread parentThread;
	private Thread workerThread;

	public ThreadStateDemo() throws InterruptedException {
		parentThread = Thread.currentThread();

		workerThread = new Worker();
		// TODO
	}

	private static void calculate(int size) {
		double[] junk = new double[size];

		for (int i = 0; i < junk.length; i++) {
			junk[i] = Math.random();
		}

		Arrays.sort(junk);
	}

	private void output(String id) {
		System.out.printf("%-11s: %s is %-10s %s is %s%n", id, parentThread.getName(), parentThread.getState(),
				workerThread.getName(), workerThread.getState());
	}

	private class Worker extends Thread {
		@Override
		public void run() {
			calculate(1000);
			// TODO
		}
	}

	public static void main(String[] args) throws InterruptedException {
		new ThreadStateDemo();
	}
}
