package ch.supsi.monopoly.cli;
import java.util.Random;

public enum Color {
    RESET("\u001B[0m"),
    BLACK("\u001B[30m"),
    RED("\u001B[31m"),
    GREEN("\033[32m"),
    YELLOW("\033[33m"),
    BLUE("\033[34m"),
    PURPLE("\u001B[35m"),
    CYAN("\033[36m"),
    WHITE("\u001B[37m"),
    GRAY("\033[37m"),
    BROWN("\033[94m"),
    PINK("\033[35m");

    private final String code;

    Color(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Color random() {
        Color[] values = values();
        Random random = new Random();
        return values[random.nextInt(1, values.length)];
    }
}