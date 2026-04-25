package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Bank;
import ch.supsi.monopoly.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoxStationTest {

    private Bank bank;
    private BoxStation boxStation;
    private Player player;

    @BeforeEach
    void setUp() {
        // Creiamo una stazione (il valore sarà random tra 50 e 150)
        boxStation = new BoxStation("Stazione Termini");
        player = new Player("Viaggiatore", 'S');
    }

    @Test
    void testValueIsRandomlyGenerated() {
        int value = boxStation.getValue();
        // Verifica che il valore rispetti i limiti imposti dalla classe Box
        assertTrue(value >= 50 && value <= 150,
                "Il valore della stazione deve essere compreso tra 50 e 150 CHF");
    }

    @Test
    void testApplyEffectDeductsStationValue() {
        int saldoIniziale = player.getBalance();
        int valoreStazione = boxStation.getValue();

        boxStation.applyEffect(player);

        assertEquals(saldoIniziale - valoreStazione, player.getBalance(),
                "Il giocatore dovrebbe pagare il valore della stazione");
    }

    @Test
    void testTaxMethodDirectCall() {
        int saldoIniziale = player.getBalance();
        int valoreStazione = boxStation.getValue();

        // Verifica l'implementazione del metodo dell'interfaccia Taxable
        boxStation.tax(player);

        assertEquals(saldoIniziale - valoreStazione, player.getBalance());
    }

    @Test
    void testToStringFormat() {
        String ts = boxStation.toString();
        // Il formato richiesto è: [ Cella Stazione Termini ]
        assertEquals("[ Cella Stazione Termini ]", ts);
    }

    @Test
    void testInheritedDescription() {
        // Verifica che la descrizione di default di Box sia presente
        int val = boxStation.getValue();
        assertEquals("Paga " + val, boxStation.getDescription());
    }
}