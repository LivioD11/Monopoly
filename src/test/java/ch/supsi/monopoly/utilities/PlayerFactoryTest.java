package ch.supsi.monopoly.utilities;

import ch.supsi.monopoly.Player;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class PlayerFactoryTest {

    private void setMockInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ScannerUtilities.updateScanner();
        // NOTA: Se lo scanner statico è già stato creato,
        // questa modifica potrebbe non avere effetto a seconda di quando
        // viene inizializzata la classe.
    }

    @Test
    void testSetupSuccess() {
        // Simuliamo l'input per 2 giocatori:
        // G1: Nome "Mario", Simbolo 'M'
        // G2: Nome "Luigi", Simbolo 'L'
        String input = "Mario\nM\nLuigi\nL\n";
        setMockInput(input);
        PlayerFactory factory = new PlayerFactory(2);

        Player[] players = factory.setup();

        assertEquals(2, players.length);
        assertEquals("MARIO", players[0].getName());
        assertEquals('M', players[0].getSign());
        assertEquals("LUIGI", players[1].getName());
        assertEquals('L', players[1].getSign());
    }

    @Test
    void testUniqueNameValidation() {
        // Simuliamo un conflitto di nomi:
        // G1: "Mario", 'M'
        // G2: "Mario" (Errore!), "Luigi", 'L'
        String input = "Mario\nM\nMario\nLuigi\nL\n";
        setMockInput(input);
        PlayerFactory factory = new PlayerFactory( 2);

        Player[] players = factory.setup();

        assertEquals("MARIO", players[0].getName());
        assertEquals("LUIGI", players[1].getName(), "Il secondo giocatore dovrebbe chiamarsi Luigi dopo il fallimento del primo tentativo");
    }

    @Test
    void testUniqueSignValidation() {
        // Simuliamo un conflitto di simboli:
        // G1: "Mario", 'X'
        // G2: "Luigi", 'X' (Errore!), 'Y'
        String input = "Mario\nX\nLuigi\nX\nY\n";
        setMockInput(input);
        PlayerFactory factory = new PlayerFactory( 2);

        Player[] players = factory.setup();

        assertEquals('X', players[0].getSign());
        assertEquals('Y', players[1].getSign(), "Il secondo simbolo dovrebbe essere Y dopo il rifiuto di X");
    }

    @Test
    void testCaseInsensitiveNameValidation() {
        // Verifica che "MARIO" e "mario" siano considerati lo stesso nome
        String input = "Mario\nM\nMARIO\nLuigi\nL\n";
        setMockInput(input);
        PlayerFactory factory = new PlayerFactory( 2);

        Player[] players = factory.setup();

        assertNotEquals(players[0].getName(), players[1].getName());
        assertEquals("LUIGI", players[1].getName());
    }
}