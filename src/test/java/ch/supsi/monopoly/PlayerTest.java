package ch.supsi.monopoly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Bank bank;
    private Player player;
    private final String TEST_NAME = "TestPlayer";
    private final char TEST_SIGN = 'T';

    @BeforeEach
    void setUp() {
        // Ripristiniamo lo stato della banca se necessario prima di ogni test
        // per evitare interferenze tra i test dovute ai metodi statici di Bank.
        player = new Player(TEST_NAME, TEST_SIGN);
    }

    @Test
    @DisplayName("Inizializzazione corretta del giocatore")
    void testConstructor() {
        assertAll("Verifica attributi iniziali",
                () -> assertEquals(TEST_NAME, player.getName(), "Il nome non corrisponde"),
                () -> assertEquals(TEST_SIGN, player.getSign(), "Il simbolo non corrisponde"),
                // Secondo le regole G1, riceve 2000 CHF all'inizio [cite: 14]
                () -> assertEquals(2000, player.getBalance(), "Il saldo iniziale dovrebbe essere 2000")
        );
    }

    @Test
    @DisplayName("Il giocatore può ricevere denaro")
    void testReceiveMoney() {
        int initialBalance = player.getBalance();
        int amount = 500;
        player.receiveMoney(amount);
        assertEquals(initialBalance + amount, player.getBalance(), "Il saldo non è aumentato correttamente");
    }

    @Test
    @DisplayName("Il giocatore può pagare denaro")
    void testPayMoney() {
        int initialBalance = player.getBalance();
        int amount = 300;
        player.payMoney(amount);
        assertEquals(initialBalance - amount, player.getBalance(), "Il saldo non è diminuito correttamente");
    }

    @Test
    @DisplayName("Avanzamento sul tabellone con gestione modulo")
    void testAdvance() {
        // Supponendo che Board.BOX_NUMBER sia 16 (per un 5x5) o 32 (per un 9x9)
        int steps = 5;
        int initialPos = player.getPosition();
        player.move(steps);

        // Verifica movimento semplice
        assertEquals(initialPos + steps, player.getPosition());

        // Verifica superamento del via (giro completo)
        player.move(100); // Valore arbitrario alto per forzare il modulo
        assertTrue(player.getPosition() < 32, "La coordinata deve restare entro i limiti del tabellone");
    }

    @Test
    @DisplayName("Verifica stato di bancarotta")
    void testIsBroke() {
        assertFalse(player.isBroke(), "Il giocatore non dovrebbe essere in bancarotta con saldo positivo");

        player.payMoney(player.getBalance()); // Saldo a 0
        assertTrue(player.isBroke(), "Il giocatore dovrebbe essere in bancarotta con saldo a 0 [cite: 22]");

        player.payMoney(100); // Saldo negativo
        assertTrue(player.isBroke(), "Il giocatore dovrebbe essere in bancarotta con saldo negativo");
    }
}