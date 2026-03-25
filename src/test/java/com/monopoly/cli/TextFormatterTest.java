package com.monopoly.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class TextFormatterTest {

    @ParameterizedTest
    @CsvSource({
            "1000, 1'000 CHF",
            "50, 50 CHF",
            "1000000, 1'000'000 CHF",
            "0, 0 CHF",
            "-1500, -1'500 CHF"
    })
    void testFormatCurrency(int amount, String expected) {
        assertEquals(expected, TextFormatter.formatCurrency(amount));
    }

    @Test
    void testColorFormatting() {
        String text = "Test";
        // Assumendo che Color.RED.getCode() restituisca "\u001B[31m"
        String result = TextFormatter.color(text, Color.RED);

        assertTrue(result.startsWith(Color.RED.getCode()));
        assertTrue(result.endsWith(Color.RESET.getCode()));
        assertTrue(result.contains(text));
    }

    @Test
    void testPadAnsiNormalString() {
        String text = "Ciao"; // lunghezza 4
        int width = 10;
        String padded = TextFormatter.padAnsi(text, width);

        assertEquals(10, padded.length());
        assertEquals("Ciao      ", padded);
    }

    @Test
    void testPadAnsiWithColorCodes() {
        // Stringa colorata: il testo visibile è "M" (lunghezza 1)
        // ma la stringa contiene i codici ANSI (es. "\u001B[31mM\u001B[0m")
        String coloredText = TextFormatter.color("M", Color.RED);
        int width = 5;

        String padded = TextFormatter.padAnsi(coloredText, width);

        // La lunghezza totale della stringa sarà > 5 a causa dei codici ANSI,
        // ma noi dobbiamo verificare che il padding aggiunto sia corretto.
        // Se il testo visibile è 1 e la larghezza è 5, devono esserci 4 spazi.

        String clean = padded.replaceAll("\u001B\\[[;\\d]*m", "");
        assertEquals(5, clean.length(), "La lunghezza del testo pulito dai codici ANSI deve essere pari alla width");
        assertTrue(padded.endsWith("    "), "Dovrebbe avere 4 spazi di padding alla fine");
    }

    @Test
    void testPadAnsiTextWiderThanWidth() {
        String text = "Supercalifragilistichespiralidoso";
        String result = TextFormatter.padAnsi(text, 5);

        assertEquals(text, result, "Se il testo è più lungo della larghezza, deve tornare invariato");
    }
}