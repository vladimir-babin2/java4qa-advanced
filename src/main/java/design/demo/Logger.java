package design.demo;

public class Logger {
    public void log(String message) {
        System.out.println(message);
    }

    public void log(String... messages) {
        for (String message : messages) {
            log(message);
        }
    }
}

class LoggerTest {
    public static void main(String[] args) {
        Logger logger = new Logger();
        logger.log("");
    }
}
