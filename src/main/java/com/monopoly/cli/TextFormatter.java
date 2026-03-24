package com.monopoly.cli;

public abstract class TextFormatter {

    public static String formatCurrency(int amount) {
        String s = Integer.toString(Math.abs(amount));
        StringBuilder sb = new StringBuilder();
        int n = s.length();

        for (int i = 0; i < n; i++) {
            // Inserisce l'apostrofo ogni 3 cifre partendo da destra
            if (i > 0 && (n - i) % 3 == 0) {
                sb.append("'");
            }
            sb.append(s.charAt(i));
        }

        String formattedNumber = (amount < 0 ? "-" : "") + sb.toString();
        return formattedNumber + " CHF";
    }

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