package design.demo;

public class Logger {
    public static void log(String message, int level) {
        if (filter(message, level)) {
            append(message, level);
        }
    }

    //region Implementation
    private static void append(String message, int level) {

    }

    private static boolean filter(String message, int level) {
        return false;
    }
    //endregion
}

class LoggerTest {
    public static void main(String[] args) {
        Logger.log("message", 0);
    }
}
