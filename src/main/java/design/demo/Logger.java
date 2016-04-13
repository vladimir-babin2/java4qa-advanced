package design.demo;

public class Logger {
    public void log(String message, int level) {
        if (filter(message, level)) {
            append(message, level);
        }
    }

    //region Implementation
    private void append(String message, int level) {

    }

    private boolean filter(String message, int level) {
        return false;
    }
    //endregion
}

class LoggerTest {
    public static void main(String[] args) {
        Logger logger = new Logger();
        logger.log("message", 0);
    }
}
