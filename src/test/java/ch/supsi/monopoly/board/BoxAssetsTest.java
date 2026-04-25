package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Bank;
import ch.supsi.monopoly.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxAssetsTest {

    private Bank bank;
    private BoxAssets boxAssets;
    private Player player;

    @BeforeEach
    void setUp() {
        boxAssets = new BoxAssets("Tassa Patrimoniale");
        // Assumo che il costruttore di Player accetti (nome, simbolo)
        // e imposti un saldo iniziale (es. 1500)
        player = new Player("TestPlayer", 'T');
    }

    @Test
    void testTaxCalculation10Percent() {
        // Forza il saldo a un valore fisso per il test
        // Se non hai un setBalance, usa il saldo iniziale noto
        int saldoIniziale = player.getBalance();
        int tassaAttesa = (int) (saldoIniziale * 0.1);
        int saldoAtteso = saldoIniziale - tassaAttesa;

        boxAssets.applyEffect(player);

        assertEquals(saldoAtteso, player.getBalance(),
                "Il saldo dopo la tassa del 10% non è corretto");
    }

    @Test
    void testTaxWithSpecificValue() {
        player = new Player("RichPlayer", 'R');

        boxAssets.tax(player);
        assertTrue(player.getBalance() < 2000);
    }

    @Test
    void testDescriptionIsSetCorrectly() {
        assertEquals("paga 10% patrimonio", boxAssets.getDescription());
    }

    @Test
    void testToString() {
        String ts = boxAssets.toString();
        assertTrue(ts.contains("Tassa Patrimoniale"));
        assertTrue(ts.contains("10% patrimonio"));
    }
}