package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Bank;
import ch.supsi.monopoly.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxParkingTest {

    private BoxParking boxParking;
    private Bank bank;
    private Player player;

    @BeforeEach
    void setUp() {
        boxParking = new BoxParking();
        player = new Player("TestPlayer", 'P');
    }

    @Test
    void testNameIsCorrect() {
        // Verifica che il costruttore super("PARCHEGGIO") funzioni
        assertEquals("PARCHEGGIO", boxParking.getName());
    }

    @Test
    void testApplyEffectDoesNothingToBalance() {
        int saldoIniziale = player.getBalance();

        // Eseguiamo l'effetto (che dovrebbe essere vuoto)
        boxParking.applyEffect(player);

        int saldoFinale = player.getBalance();
        assertEquals(saldoIniziale, saldoFinale, "Il parcheggio non deve modificare il saldo del giocatore");
    }

    @Test
    void testValueIsWithinRandomRange() {
        // Poiché BoxParking chiama super("PARCHEGGIO"), la classe Box genera un valore random
        // tra 50 e 150. Verifichiamo che rispetti i limiti della classe padre.
        int value = boxParking.getValue();
        assertTrue(value >= 50 && value <= 150, "Il valore random della casella deve essere tra 50 e 150");
    }

    @Test
    void testDescriptionDefaultsToValue() {
        // Box genera automaticamente "Paga [valore]" se non sovrascritto
        int value = boxParking.getValue();
        assertEquals("Paga " + value, boxParking.getDescription());
    }

    @Test
    void testToStringFormat() {
        String ts = boxParking.toString();
        // Verifica il formato specifico dell'override: [ PARCHEGGIO: Riposati un turno! ]
        assertEquals("[ PARCHEGGIO: Riposati un turno! ]", ts);
    }
}