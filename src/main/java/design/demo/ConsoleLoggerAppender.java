package design.demo;

public class ConsoleLoggerAppender {
    public void append(String message, int level) {
        System.out.println(message);
    }
}
