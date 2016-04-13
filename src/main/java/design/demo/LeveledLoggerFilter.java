package design.demo;

public class LeveledLoggerFilter {
    public boolean filter(String message, int level) {
        return level > 1;
    }
}
