package ch.supsi.monopoly;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    @BeforeEach
    void setUp() {
        Bank.getInstance().reset();
    }

    @Test
    @DisplayName("Il saldo iniziale deve essere esattamente 1.000.000 CHF")
    void testInitialBalance() {
        assertEquals(1000000, Bank.getInstance().getBalance(),
                "ERRORE: La banca deve iniziare con 1.000.000 CHF come da regolamento [cite: 14]");
    }

    @Test
    @DisplayName("Il saldo deve aumentare quando la banca riceve denaro")
    void testReceiveMoneyIncrementsBalance() {
        int initial = Bank.getInstance().getBalance();
        int deposit = 500;
        Bank.getInstance().receiveMoney(deposit);

        assertEquals(initial + deposit, Bank.getInstance().getBalance(),
                "ERRORE: Il saldo della banca non è aumentato dopo la ricezione");
    }

    @Test
    @DisplayName("Il saldo deve diminuire quando la banca paga (e gestire negativi)")
    void testPayMoneyDecrementsBalance() {
        int initial = Bank.getInstance().getBalance();
        int payment = 2000;

        // Testiamo anche che Math.abs funzioni: passando -2000 deve comunque detrarre 2000
        Bank.getInstance().payMoney(-payment);

        // Se il tuo codice attuale NON sottrae 'amount' nel metodo payMoney,
        // questo test FALLIRÀ, evidenziando il bug.
        assertEquals(initial - payment, Bank.getInstance().getBalance(),
                "ERRORE: Il saldo non è diminuito correttamente o Math.abs non è stato applicato");
    }

    @Test
    @DisplayName("Verifica che il reset riporti il saldo al valore iniziale")
    void testResetRestoresInitialFunds() {
        Bank.getInstance().payMoney(500000); // Svuota metà banca
        assertNotEquals(1000000, Bank.getInstance().getBalance());

        Bank.getInstance().reset();
        assertEquals(1000000, Bank.getInstance().getBalance(),
                "ERRORE: Il metodo reset non ha ripristinato il patrimonio a 1.000.000 [cite: 15]");
    }
}