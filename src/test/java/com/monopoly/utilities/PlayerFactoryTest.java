package com.monopoly.utilities;

import com.monopoly.Player;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFactoryTest {

    private Scanner createScanner(String input) {
        return new Scanner(new ByteArrayInputStream(input.getBytes()));
    }

    @Test
    void testSetupSuccess() {
        // Simuliamo l'input per 2 giocatori:
        // G1: Nome "Mario", Simbolo 'M'
        // G2: Nome "Luigi", Simbolo 'L'
        String input = "Mario\nM\nLuigi\nL\n";
        Scanner scanner = createScanner(input);
        PlayerFactory factory = new PlayerFactory(scanner, 2);

        Player[] players = factory.setup();

        assertEquals(2, players.length);
        assertEquals("Mario", players[0].getName());
        assertEquals('M', players[0].getSign());
        assertEquals("Luigi", players[1].getName());
        assertEquals('L', players[1].getSign());
    }

    @Test
    void testUniqueNameValidation() {
        // Simuliamo un conflitto di nomi:
        // G1: "Mario", 'M'
        // G2: "Mario" (Errore!), "Luigi", 'L'
        String input = "Mario\nM\nMario\nLuigi\nL\n";
        Scanner scanner = createScanner(input);
        PlayerFactory factory = new PlayerFactory(scanner, 2);

        Player[] players = factory.setup();

        assertEquals("Mario", players[0].getName());
        assertEquals("Luigi", players[1].getName(), "Il secondo giocatore dovrebbe chiamarsi Luigi dopo il fallimento del primo tentativo");
    }

    @Test
    void testUniqueSignValidation() {
        // Simuliamo un conflitto di simboli:
        // G1: "Mario", 'X'
        // G2: "Luigi", 'X' (Errore!), 'Y'
        String input = "Mario\nX\nLuigi\nX\nY\n";
        Scanner scanner = createScanner(input);
        PlayerFactory factory = new PlayerFactory(scanner, 2);

        Player[] players = factory.setup();

        assertEquals('X', players[0].getSign());
        assertEquals('Y', players[1].getSign(), "Il secondo simbolo dovrebbe essere Y dopo il rifiuto di X");
    }

    @Test
    void testCaseInsensitiveNameValidation() {
        // Verifica che "MARIO" e "mario" siano considerati lo stesso nome
        String input = "Mario\nM\nMARIO\nLuigi\nL\n";
        Scanner scanner = createScanner(input);
        PlayerFactory factory = new PlayerFactory(scanner, 2);

        Player[] players = factory.setup();

        assertNotEquals(players[0].getName(), players[1].getName());
        assertEquals("Luigi", players[1].getName());
    }
}