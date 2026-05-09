package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.utilities.ScannerUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

class BoxLuxuryTest {

    private BoxLuxury boxLuxury;
    private Player player;

    private void setMockInput(String input) {
        try {
            InputStream in = new ByteArrayInputStream(input.getBytes());
            System.setIn(in);

            // Usiamo la reflection per chiamare il metodo privato/package-private
            java.lang.reflect.Method method = ScannerUtilities.class.getDeclaredMethod("updateScanner");
            method.setAccessible(true); // Rompe il guscio di protezione
            method.invoke(null);        // null perché il metodo è statico
        } catch (Exception e) {
            throw new RuntimeException("Errore nel reset dello scanner", e);
        }
    }

    @BeforeEach
    void setUp() {
        // Creiamo la casella "Tassa di Lusso"
        boxLuxury = new BoxLuxury("Tassa di Lusso");
        // Creiamo un giocatore (assumendo saldo iniziale standard, es. 1500 CHF)
        player = new Player("TestPlayer", 'L');
    }

    @Test
    void testValueIsFixedAt200() {
        // Verifica che il valore della casella sia effettivamente 200 come da specifica
        assertEquals(200, boxLuxury.getValue(), "La tassa di lusso deve essere di 200 CHF");
    }

    @Test
    void testApplyEffectDeductsFixedAmount() {
        int saldoIniziale = player.getBalance();
        int valoreTassa = boxLuxury.getValue(); // Dovrebbe essere 200

        boxLuxury.applyEffect(player);

        int saldoFinale = player.getBalance();
        assertEquals(saldoIniziale - valoreTassa, saldoFinale,
                "Il saldo del giocatore dovrebbe essere diminuito esattamente di 200 CHF");
    }

    @Test
    void testTaxMethodDirectCall() {
        int saldoIniziale = player.getBalance();

        // Chiamata diretta al metodo dell'interfaccia Taxable
        setMockInput("q\n");
        boxLuxury.applyEffect(player);
        assertEquals(saldoIniziale - 200, player.getBalance());
    }

    @Test
    void testDescriptionInheritedFromBox() {
        // BoxLuxury chiama super(200, name), quindi Box imposta "Paga 200"
        assertEquals("Paga: 200", boxLuxury.getDescription(),
                "La descrizione generata automaticamente dal costruttore Box non è corretta");
    }

    @Test
    void testToStringFormat() {
        String ts = boxLuxury.toString();
        // Verifica il formato specifico richiesto dall'override di BoxLuxury
        assertTrue(ts.contains("Cella : tassa di 200 CHF"));
        assertTrue(ts.contains("Tassa di Lusso"));
    }
}