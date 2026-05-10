package ch.supsi.monopoly;

import ch.supsi.monopoly.utilities.ScannerUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GameTest {
    private Game game;

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
        setMockInput("a\na\nb\nb\nc\nc\nd\nd\n");
        game = new Game();
    }

    @Test
    @DisplayName("Il menu deve avere 2 opzioni")
    void testInitialBalance() {
        int expectedOptions = 2;
        int actualMenuOptions = game.getMenu().getOptions().size();
        assertEquals(expectedOptions, actualMenuOptions);
    }

    @Test
    @DisplayName("La prima opzione deve mostrare il saldo")
    void testPlayerCanSeeBalance() {
        setMockInput("1\n");
        game.getMenu().displayAndSelect();
    }

    @Test
    @DisplayName("La prima opzione deve lancaire il dado")
    void testPlayerCanRollDice() {
        setMockInput("2\nq\n");
        game.getMenu().displayAndSelect();
    }

    @Test
    @DisplayName("Devono essere presenti 4 giocatori")
    void testGameHasFourPlayers() {
        int extectedPlayerCount = 4;
        int actualPlayerCount = game.getPlayers().length;
        Player[] actualPlayers = game.getPlayers();
        assertEquals(extectedPlayerCount, actualPlayerCount);
        assertEquals("A",actualPlayers[0].getName());
        assertEquals("B",actualPlayers[1].getName());
        assertEquals("C",actualPlayers[2].getName());
        assertEquals("D",actualPlayers[3].getName());
    }

    @Test
    @DisplayName("Devono essere presenti 4 giocatori")
    void testGameFinish() {
        Player[] actualPlayers = game.getPlayers();
        for(Player actualPlayer : actualPlayers)
            actualPlayer.payMoney(2000);

        setMockInput("2\nq\n");
        game.start();

        boolean expectedGameIsOver = true;
        assertEquals(expectedGameIsOver,game.isGameOver());
    }

    @Test
    @DisplayName("Simulazione partita: i giocatori giocano diversi turni fino alla fine")
    void testGameFullSimulation() {

        Player[] players = game.getPlayers();

        while (!game.isGameOver())
        {
            for (int i = 0; i < 4; i++) {
                game.setTurnIndex(i);
                String input ="2\nq\n";
                setMockInput(input);

                game.executeTurn();

            }
            game.tick();
        }

        // Verifichiamo che almeno un giocatore sia effettivamente senza soldi
        boolean someoneIsBroke = false;
        for (Player p : game.getPlayers()) {
            System.out.println(p.toString() + " " + p.getBalance() + "\n");
            if (p.isBroke()) {
                someoneIsBroke = true;
                break;
            }
        }

        // 4. Verifichiamo che la condizione di GameOver sia stata raggiunta
        assertEquals(true, game.isGameOver(), "La partita dovrebbe terminare quando un giocatore va in bancarotta");


        assertEquals(true, someoneIsBroke, "Almeno un giocatore deve essere in bancarotta");
    }
}
