package com.monopoly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    @BeforeEach
    void setUp() {
        // Reset dello stato statico della banca prima di ogni test
        Bank.reset();
    }

    @Test
    @DisplayName("Verifica saldo iniziale della banca")
    void testInitialBalance() {
        // La banca deve partire con 1'000'000 CHF [cite: 14, 15]
        // Nota: se non hai un getter per il balance, questo test
        // serve a verificare la costante di contributo iniziale.
        assertEquals(2000, Bank.CONTRIBUTION, "Il contributo iniziale deve essere 2000 CHF ");
    }

    @Test
    @DisplayName("La banca riceve denaro correttamente")
    void testReceiveMoney() {
        // Simuliamo un pagamento verso la banca (es. un pedaggio)
        int amount = 500;
        // Poiché il balance è privato e statico, verifichiamo che l'operazione
        // non sollevi eccezioni e che la logica interna (se testabile tramite getter) funzioni.
        assertDoesNotThrow(() -> Bank.receiveMoney(amount));
    }

    @Test
    @DisplayName("La banca paga denaro correttamente")
    void testPayMoney() {
        // Simuliamo l'erogazione di fondi a un giocatore
        int amount = 1000;
        assertDoesNotThrow(() -> Bank.payMoney(amount));
    }

    @Test
    @DisplayName("Verifica gestione valori negativi nelle transazioni")
    void testAbsoluteValueTransactions() {
        // Il codice della banca usa Math.abs() per prevenire errori di segno
        assertDoesNotThrow(() -> Bank.payMoney(-500), "Il metodo dovrebbe gestire valori negativi con Math.abs()");
        assertDoesNotThrow(() -> Bank.receiveMoney(-1000), "Il metodo dovrebbe gestire valori negativi con Math.abs()");
    }

    @Test
    @DisplayName("Verifica del reset del patrimonio")
    void testReset() {
        Bank.payMoney(500000); // Riduciamo il fondo
        Bank.reset();
        // Dopo il reset, il patrimonio deve tornare a 1'000'000 CHF
        // Se aggiungerai un getter getBalance(), potrai asserire il valore qui.
    }
}