package ch.supsi.monopoly.utilities;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

class ScannerUtilitiesTest {

    private Scanner createScanner(String input) {
        return new Scanner(new ByteArrayInputStream(input.getBytes()));
    }

    @Test
    void testGetInputString_ValidInput() {
        Scanner scanner = createScanner("NomeUtente");
        String result = ScannerUtilities.getInputString(scanner, "Inserisci nome: ");
        assertEquals("NomeUtente", result);
    }

    @Test
    void testGetInputString_WithSpacesAndRetry() {
        // Simula: stringa vuota (isBlank), poi input valido
        // Nota: .next() legge fino al primo spazio
        Scanner scanner = createScanner("   \n ValidInput");
        String result = ScannerUtilities.getInputString(scanner, "Inserisci: ");
        assertEquals("ValidInput", result);
    }

    @Test
    void testGetInputChar_ValidAlphabetic() {
        Scanner scanner = createScanner("A");
        char result = ScannerUtilities.getInputChar(scanner, "Inserisci carattere: ");
        assertEquals('A', result);
    }

    @Test
    void testGetInputChar_RetryOnNonAlphabetic() {
        // Simula: '1' (non alfabetico), poi 'B' (valido)
        Scanner scanner = createScanner("1\nB");
        char result = ScannerUtilities.getInputChar(scanner, "Inserisci: ");
        assertEquals('B', result);
    }

    @Test
    void testGetInputInt_ValidInteger() {
        Scanner scanner = createScanner("42");
        int result = ScannerUtilities.getInputInt(scanner, "Inserisci numero: ");
        assertEquals(42, result);
    }

    @Test
    void testGetInputInt_RetryOnInvalidFormat() {
        // Simula: "abc" (non intero), poi "10" (valido)
        Scanner scanner = createScanner("abc\n10");
        int result = ScannerUtilities.getInputInt(scanner, "Inserisci: ");
        assertEquals(10, result);
    }

    @Test
    void testGetInputInt_NegativeValue() {
        Scanner scanner = createScanner("-5");
        int result = ScannerUtilities.getInputInt(scanner, "Inserisci: ");
        assertEquals(-5, result);
    }
}