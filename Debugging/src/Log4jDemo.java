import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class demonstrates very basic logging using Log4j2. It is accompanied by
 * the log4j2.xml configuration file.
 */
public class Log4jDemo {
	private static final Logger rootLogger = LogManager.getRootLogger();
	private static final Logger outerLogger = LogManager.getLogger(Log4jDemo.class);

	public static void testOuter() {
		outerLogger.trace("Outer Trace");
		outerLogger.debug("Outer Debug");
		outerLogger.info("Outer Info");
		outerLogger.warn("Outer Warn");
		outerLogger.error("Outer Error");
		outerLogger.fatal("Outer Fatal");
	}

	public static void testRoot() {
		rootLogger.trace("Root Trace");
		rootLogger.debug("Root Debug");
		rootLogger.info("Root Info");
		rootLogger.warn("Root Warn");
		rootLogger.error("Root Error");
		rootLogger.fatal("Root Fatal");
	}

	public static class Inner {
		private static Logger innerLogger = LogManager.getLogger(Log4jDemo.Inner.class);

		public static void testInner() {
			innerLogger.trace("Inner Trace");
			innerLogger.debug("Inner Debug");
			innerLogger.info("Inner Info");
			innerLogger.warn("Inner Warn");
			innerLogger.error("Inner Error");
			innerLogger.fatal("Inner Fatal");
		}
	}

	public static void main(String[] args) {
		testRoot();
		System.out.println();

		testOuter();
		System.out.println();

		Inner.testInner();
	}
}
