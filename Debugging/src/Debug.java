/**
 * This class demonstrates a very debug class, used to motivate using a logging
 * package.
 */
public class Debug {

	/** Set this variable to {@code true} to turn on debug messages. */
	public static boolean on = false;

	/**
	 * If {@link #on} is {@code true}, this method will output debug messages to
	 * the console.
	 *
	 * @param message
	 *            to output when debugging
	 */
	public static void println(String message) {
		if (on) {
			System.out.println("[debug] " + message);
		}
	}

	/*
	 * PROBLEMS: Turning debugging on and off requires modification to source
	 * code, and re-compilation of that code. Would be nicer if we could control
	 * debug messages externally, via a flag or configuration file.
	 *
	 * This class is not configurable, so it is impossible to turn off debug
	 * messages for just one class. If this class is widely used, turning on
	 * debug messages could result in a lot of unnecessary output. We could try
	 * to have a per-class debug flag, but then we run into other issues.
	 *
	 * We may also want some debug messages within a class to output, but not
	 * all. For example, we may still want to see error messages. We could try
	 * to add multiple levels of debug statements, but then our class starts to
	 * get more and more complicated and computationally expensive.
	 *
	 * We may also want some debug messages to go to the console, and some to go
	 * to a file to refer to later. For example, we may want most of the debug
	 * messages to go to file, but want to know via the console when a serious
	 * error occurs. Again, we can try to achieve this in this class, but we
	 * have to worry about efficiency.
	 *
	 * Printing several messages to the console is not efficient, and will
	 * noticeably slow down your code. Printing to a file, especially in a
	 * multithreading context, can also be tricky!
	 *
	 * In some cases, more messages than the console can "remember" will be
	 * added, causing the early debug messages to be lost.
	 *
	 * In multi-threading contexts, need the timestamp of when the message was
	 * generated, not when it was output. It is possible that messages get added
	 * to the console out-of-order!
	 *
	 * It just so happens there is something that addresses all of our concerns,
	 * so we don't have to re-invent the wheel!
	 */
}
