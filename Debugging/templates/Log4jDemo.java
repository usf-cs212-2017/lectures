import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Log4jDemo {
	private static final Logger rootLogger = LogManager.getRootLogger();
	private static final Logger outerLogger = null; // TODO

	public static void testRoot() {
		// TODO
	}

	public static void testOuter() {
		// TODO
	}

	// TODO

	public static void main(String[] args) {
		// TODO
	}
}

//outerLogger.trace("Outer Trace");
//outerLogger.debug("Outer Debug");
//outerLogger.info("Outer Info");
//outerLogger.warn("Outer Warn");
//outerLogger.error("Outer Error");
//outerLogger.fatal("Outer Fatal");

//innerLogger.trace("Inner Trace");
//innerLogger.debug("Inner Debug");
//innerLogger.info("Inner Info");
//innerLogger.warn("Inner Warn");
//innerLogger.error("Inner Error");
//innerLogger.fatal("Inner Fatal");