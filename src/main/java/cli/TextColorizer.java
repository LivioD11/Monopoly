package cli;

public abstract class TextColorizer {

    public static String color(String text, Color color) {
        return color.getCode() + text + Color.RESET.getCode();
    }

    public static String padAnsi(String text, int width) {
        String clean = text.replaceAll("\u001B\\[[;\\d]*m", "");
        int padding = width - clean.length();

        if (padding <= 0) return text;

        return text + " ".repeat(padding);
    }
}