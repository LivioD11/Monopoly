package ch.supsi.monopoly.utilities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

class ScannerUtilitiesTest {


    public static void setMockInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ScannerUtilities.updateScanner();
        // NOTA: Se lo scanner statico è già stato creato,
        // questa modifica potrebbe non avere effetto a seconda di quando
        // viene inizializzata la classe.
    }

    @Test
    void testGetInputString_ValidInput() {
        setMockInput("NomeUtente\n");
        String result = ScannerUtilities.getInputString("Inserisci nome: ");
        assertEquals("NomeUtente", result);
    }

    @Test
    void testGetInputString_WithSpacesAndRetry() {
        // Simula: stringa vuota (isBlank), poi input valido
        // Nota: .next() legge fino al primo spazio
        setMockInput(" \nValidInput\n");
        String result = ScannerUtilities.getInputString("Inserisci: ");
        assertEquals("ValidInput", result);
    }

    @Test
    void testGetInputChar_ValidAlphabetic() {
        setMockInput("A\n");
        char result = ScannerUtilities.getInputChar("Inserisci carattere: ");
        assertEquals('A', result);
    }

    @Test
    void testGetInputChar_RetryOnNonAlphabetic() {
        // Simula: '1' (non alfabetico), poi 'B' (valido)
        setMockInput("1\nB\n");
        char result = ScannerUtilities.getInputChar("Inserisci: ");
        assertEquals('B', result);
    }

    @Test
    void testGetInputInt_ValidInteger() {
        setMockInput("42\n");
        int result = ScannerUtilities.getInputInt("Inserisci numero: ");
        assertEquals(42, result);
    }

    @Test
    void testGetInputInt_RetryOnInvalidFormat() {
        // Simula: "abc" (non intero), poi "10" (valido)
        setMockInput("abc\n10\n");
        int result = ScannerUtilities.getInputInt("Inserisci: ");
        assertEquals(10, result);
    }

    @Test
    void testGetInputInt_NegativeValue() {
        setMockInput("-5\n");
        int result = ScannerUtilities.getInputInt("Inserisci: ");
        assertEquals(-5, result);
    }
}