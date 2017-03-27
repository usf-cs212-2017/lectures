import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BoundedBuffer<E> {

	/*
	 * Think of a bounded buffer as a circular buffer. For example:
	 *
	 * 0 1 7 2 versus 0 1 2 3 4 5 6 7 8 6 3 5 4
	 *
	 * We add elements at the end of the buffer, increment the "end" index. We
	 * remove elements from the "beginning" of the buffer, and increment the
	 * "beg" index. These indices wrap around the circle as necessary. If the
	 * "beg" and "end" indices are the same, then we have run out of room in the
	 * buffer.
	 */

	// http://docs.oracle.com/javase/tutorial/java/generics/restrictions.html#createArrays

	private Object[] buffer;

	private int beg;
	private int end;
	private int num;
	private int max;

	private static Logger logger = LogManager.getLogger();

	public BoundedBuffer(int bufferSize) {
		buffer = new Object[bufferSize];

		beg = 0;
		end = 0;
		num = 0;
		max = buffer.length;
	}


	public synchronized void put(E item) throws InterruptedException {
		// TODO
		logger.debug("put(): waiting until buffer not full.");
		logger.debug("put(): woke up, checking buffer.");

		logger.debug("put(): adding {} in buffer.", item);

		// TODO

		logger.debug("put(): buffer now has {} elements.", num);
		logger.debug("put(): range is now ({}, {}).", beg, end);

		// TODO
	}

	public void putAll(E[] items) throws InterruptedException {
		// TODO
	}

	@SuppressWarnings("unchecked")
	public synchronized E get() throws InterruptedException {
		// TODO
		logger.debug("get(): waiting until buffer not empty.");
		logger.debug("get(): woke up, checking buffer.");

		logger.debug("get(): getting {} from buffer.", buffer[beg]);

		// TODO

		logger.debug("put(): buffer now has {} elements.", num);
		logger.debug("put(): range is now ({}, {}).", beg, end);

		// TODO
		return null;
	}
}
