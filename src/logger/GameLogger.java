package logger;

import java.io.PrintStream;

public class GameLogger {
	private static PrintStream out = System.out;// NOSONAR
	
	private GameLogger() {}
	
	public static void error(String message, Throwable e) {
		out.println("[ERROR] " + message + "\nException: " + e.getMessage());
	}
	
	public static void error(String message) {
		out.println("[ERROR] " + message);
	}
	
	public static void info(String message) {
		out.println("[INFO] " + message);
	}
	
	public static void warning(String message) {
		out.println("[WARNING] " + message);
	}
	
	public static void performance(String message) {
		out.println("[PERFORMANCE] " + message);
	}
}
