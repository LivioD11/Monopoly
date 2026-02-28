package cli;

public abstract class TextColorizer {

    public static String color(String text, Color color) {
        return color.getCode() + text + Color.RESET.getCode();
    }
}