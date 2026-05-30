package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SystemLogger {
    private static SystemLogger instance;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private SystemLogger() {
    }

    public static SystemLogger getInstance() {
        if (instance == null) {
            instance = new SystemLogger();
        }
        return instance;
    }

    public void log(String message) {
        String time = LocalDateTime.now().format(formatter);
        System.out.println("[BMS LOG " + time + "] " + message);
    }
}