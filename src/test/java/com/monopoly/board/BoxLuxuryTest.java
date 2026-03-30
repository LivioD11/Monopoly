package com.monopoly.board;

import com.monopoly.Bank;
import com.monopoly.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxLuxuryTest {

    private BoxLuxury boxLuxury;
    private Player player;

    @BeforeEach
    void setUp() {
        Bank bank = new Bank();
        // Creiamo la casella "Tassa di Lusso"
        boxLuxury = new BoxLuxury("Tassa di Lusso");
        // Creiamo un giocatore (assumendo saldo iniziale standard, es. 1500 CHF)
        player = new Player("TestPlayer", 'L',bank);
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
        boxLuxury.tax(player);

        assertEquals(saldoIniziale - 200, player.getBalance());
    }

    @Test
    void testDescriptionInheritedFromBox() {
        // BoxLuxury chiama super(200, name), quindi Box imposta "Paga 200"
        assertEquals("Paga 200", boxLuxury.getDescription(),
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