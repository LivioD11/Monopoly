package com.monopoly.board;

import com.monopoly.Bank;
import com.monopoly.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxPropertyTest {

    private Bank bank;
    private BoxProperty boxProperty;
    private Player player;

    @BeforeEach
    void setUp() {
        // Creiamo una proprietà (il valore verrà generato randomicamente tra 50 e 150)
        boxProperty = new BoxProperty("Viale Giardini");
        player = new Player("TestPlayer", 'G');
    }

    @Test
    void testRandomValueRange() {
        int value = boxProperty.getValue();
        // Verifica che il valore generato dal costruttore super(name) sia nel range [50, 150]
        assertTrue(value >= 50 && value <= 150,
                "Il valore della proprietà deve essere compreso tra 50 e 150 CHF");
    }

    @Test
    void testApplyEffectDeductsCorrectValue() {
        int saldoIniziale = player.getBalance();
        int costoProprieta = boxProperty.getValue();

        boxProperty.applyEffect(player);

        assertEquals(saldoIniziale - costoProprieta, player.getBalance(),
                "Il saldo del giocatore non è stato aggiornato correttamente con il valore della proprietà");
    }

    @Test
    void testTaxMethodDirectCall() {
        int saldoIniziale = player.getBalance();
        int costoProprieta = boxProperty.getValue();

        // Chiamata esplicita al metodo dell'interfaccia Taxable
        boxProperty.tax(player);

        assertEquals(saldoIniziale - costoProprieta, player.getBalance());
    }

    @Test
    void testToStringFormat() {
        String ts = boxProperty.toString();
        int val = boxProperty.getValue();

        // Verifica il formato: [ Cella proprietà: Viale Giardini | Tassa: X CHF ]
        assertTrue(ts.contains("Cella proprietà: Viale Giardini"));
        assertTrue(ts.contains("Tassa: " + val + " CHF"));
    }

    @Test
    void testDescriptionUpdate() {
        // Verifica che la descrizione ereditata rifletta il valore generato
        int val = boxProperty.getValue();
        assertEquals("Paga " + val, boxProperty.getDescription());
    }
}