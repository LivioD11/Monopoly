package com.monopoly.cli;

public abstract class TextFormatter {

    /**
     * Ritorna la cifra sotto forma di stringa formattata.
     * Esempio: 1000 --> "1'000 CHF"
     * @param amount
     * @return
     */
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

    /**
     * Ritorna una Stringa del colore scelto. Se si stampa una seconda stringa
     *  in sucessione avrà il colore di default.
     * @param text
     * @param color
     * @return
     */
    public static String color(String text, Color color) {
        return color.getCode() + text + Color.RESET.getCode();
    }

    /**
     * Permette di applicare il padding corretto anche se la stringa è colorata.
     * Viene ritornata una string con il padding corretto.
     * @param text
     * @param width
     * @return
     */
    public static String padAnsi(String text, int width) {
        String clean = text.replaceAll("\u001B\\[[;\\d]*m", "");
        int padding = width - clean.length();

        if (padding <= 0) return text;

        return text + " ".repeat(padding);
    }
}