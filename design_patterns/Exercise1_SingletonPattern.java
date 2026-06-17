// ─────────────────────────────────────────────
// Exercise 1: Singleton Pattern — Logger
// Ensures only ONE instance exists throughout
// the application lifecycle.
// Thread-safe via double-checked locking.
// ─────────────────────────────────────────────

class Logger {
    // volatile: ensures visibility across threads
    private static volatile Logger instance;
    private int logCount = 0;

    // Private constructor — no external instantiation
    private Logger() {
        System.out.println("[Logger] Instance created.");
    }

    // Double-checked locking — thread-safe, lazy init
    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    public void log(String level, String message) {
        logCount++;
        System.out.printf("[%s][Log #%d] %s%n", level.toUpperCase(), logCount, message);
    }

    public int getLogCount() { return logCount; }
}

public class Exercise1_SingletonPattern {
    public static void main(String[] args) {
        Logger log1 = Logger.getInstance();
        log1.log("INFO",  "Application started.");
        log1.log("DEBUG", "Loading configuration...");

        Logger log2 = Logger.getInstance();
        log2.log("WARN",  "Memory usage above 80%.");

        Logger log3 = Logger.getInstance();
        log3.log("ERROR", "Null pointer encountered.");

        System.out.println("\n--- Singleton Verification ---");
        System.out.println("log1 == log2 : " + (log1 == log2)); // true
        System.out.println("log2 == log3 : " + (log2 == log3)); // true
        System.out.println("Total logs   : " + log1.getLogCount());
        System.out.println("All three references point to the SAME Logger instance.");
    }
}
