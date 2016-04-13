package design.demo;

public class Logger {
    private LeveledLoggerFilter loggerFilter = new LeveledLoggerFilter();
    private ConsoleLoggerAppender loggerAppender = new ConsoleLoggerAppender();

    public void log(String message, int level) {
        if (loggerFilter.filter(message, level)) {
            loggerAppender.append(message, level);
        }
    }
}

class LoggerTest {
    public static void main(String[] args) {
        new Logger().log("message", 3);
    }
}
