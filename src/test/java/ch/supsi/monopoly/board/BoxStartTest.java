package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Bank;
import ch.supsi.monopoly.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxStartTest {

    private Bank bank;
    private BoxStart boxStart;
    private Player player;

    @BeforeEach
    void setUp() {
        boxStart = new BoxStart("VIA!");
        player = new Player("TestPlayer", 'V');
    }

    @Test
    void testBonusConstant() {
        // Verifica che il bonus sia effettivamente 100 come da specifica
        assertEquals(100, BoxStart.getBonus(), "Il bonus del VIA deve essere 100 CHF");
    }

    @Test
    void testConstructorSetsCorrectValue() {
        // Verifica che il valore della Box sia allineato al bonus
        assertEquals(100, boxStart.getValue());
    }

    @Test
    void testDescriptionIsSetCorrectly() {
        // Il costruttore imposta "Ritira 100"
        assertEquals("Ritira 100", boxStart.getDescription());
    }

    @Test
    void testApplyEffectDoesNothing() {
        int saldoIniziale = player.getBalance();

        // L'effetto della casella Start è vuoto (gestito esternamente da Game)
        boxStart.applyEffect(player);

        assertEquals(saldoIniziale, player.getBalance(),
                "L'applyEffect di BoxStart non dovrebbe modificare il saldo direttamente");
    }

    @Test
    void testToStringFormat() {
        String ts = boxStart.toString();
        // Formato: [ Cella VIA! | Rimborso: 100 CHF ]
        assertTrue(ts.contains("Cella VIA!"));
        assertTrue(ts.contains("Rimborso: 100 CHF"));
    }

    @Test
    void testNameIsCorrect() {
        assertEquals("VIA!", boxStart.getName());
    }
}