package ch.supsi.monopoly.board.jail;

import ch.supsi.monopoly.Dice;
import ch.supsi.monopoly.Game;
import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.PlayerStatus;
import ch.supsi.monopoly.board.Board;
import ch.supsi.monopoly.utilities.ScannerUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoxJailTest {

    Game game;
    Player player;
    int indexStart;
    int indexGoToJail;
    int indexJail;

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
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        // BoxGoToPrison Position
        Field field = Board.class.getDeclaredField("INDEX_GO_TO_JAIL");
        field.setAccessible(true);
        Object value = field.get(null);
        indexGoToJail = field.getInt(null);

        // BoxJail position;
        field = Board.class.getDeclaredField("INDEX_JAIL");
        field.setAccessible(true);
        value = field.get(null);
        indexJail = field.getInt(null);

        // BoxStart position;
        field = Board.class.getDeclaredField("INDEX_START");
        field.setAccessible(true);
        value = field.get(null);
        indexStart = field.getInt(null);

        setMockInput("a\na\nb\nb\nc\nc\nd\nd\n");
        game = new Game();

        player = game.getPlayers()[0];
    }

    @Test
    void testPlayerCanGoToJail() {
        int position = indexGoToJail - indexStart;
        player.move(position);
        game.getBoard().getBox(player.getPosition()).applyEffect(player);

        // Check player position equals to jail position
        assertEquals(indexJail,player.getPosition());
        // Check player status is INACTIVE (in prison)
        assertEquals(PlayerStatus.INACTIVE, player.getStatus());
    }

    @Test
    void testPlayerExpireSentence() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int position = indexGoToJail - indexStart;
        player.move(position);
        game.getBoard().getBox(player.getPosition()).applyEffect(player);

        java.lang.reflect.Method method = Game.class.getDeclaredMethod("tick");
        method.setAccessible(true);
        method.invoke(game);
        method.invoke(game);
        method.invoke(game);

        assertEquals(PlayerStatus.ACTIVE, player.getStatus());
    }

    @Test
    void testPlayerCanNotPayBailAmount() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        int position = indexGoToJail - indexStart;
        player.payMoney(1999);
        player.move(position);
        game.getBoard().getBox(player.getPosition()).applyEffect(player);

        java.lang.reflect.Method method = Game.class.getDeclaredMethod("tick");
        method.setAccessible(true);
        method.invoke(game);
        method.invoke(game);
        method.invoke(game);

        assertEquals(PlayerStatus.DEFEATED, player.getStatus());
    }

    @Test
    void testPlayerCanBeReleasedRollingDices() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException{
        // Mockiamo il metodo statico Dice.rollMultiple
        setMockInput("q\n");
        try (var mockedDice = org.mockito.Mockito.mockStatic(Dice.class)) {
            // Forza un "doppio" (es. totale 8, allSame true)
            mockedDice.when(() -> Dice.rollMultiple(2))
                    .thenReturn(new Dice.RollResult(8, true));


            int position = indexGoToJail - indexStart;
            player.move(position);
            game.getBoard().getBox(player.getPosition()).applyEffect(player);



            // Esegui la logica del turno
            java.lang.reflect.Method method = Game.class.getDeclaredMethod("executeTurn");
            method.setAccessible(true);
            method.invoke(game);

            // Verifica: il giocatore deve essere tornato ACTIVE grazie al doppio
            assertEquals(PlayerStatus.ACTIVE, player.getStatus());
        }
    }
}
