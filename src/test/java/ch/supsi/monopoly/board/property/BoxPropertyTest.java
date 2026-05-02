package ch.supsi.monopoly.board.property;

import ch.supsi.monopoly.Bank;
import ch.supsi.monopoly.Owner;
import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.BoxAssets;
import ch.supsi.monopoly.utilities.ScannerUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoxPropertyTest {
    private Player player;
    private BoxProperty property;

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
        setMockInput("NomeUtente");
        player = new Player("TestPlayer", 'T');
        property = new BoxProperty("TestProperty");
    }

    @Test
    void testPlayerCanBuyAProperty() {
        Owner oldOwner = property.getOwner();
        property.buy(player);
        Owner newOwner = property.getOwner();

        assertEquals(Bank.getInstance(),oldOwner);
        assertEquals(player,newOwner);
    }

    @Test
    void testPlayerCanNotBuyAProperty() {
        Owner oldOwner = property.getOwner();
        player.payMoney(2000);
        property.buy(player);
        Owner newOwner = property.getOwner();

        assertEquals(Bank.getInstance(),oldOwner);
        assertEquals(Bank.getInstance(),newOwner);
    }

    @Test
    void testCanBuildHouses() {
        property.build();
        property.build();

        for(int i=0; i < 7; i++)
            System.out.println(property.draw(i,null));
    }

    @Test
    void testCanBuildHotels() {
        property.build();
        property.build();
        property.build();
        property.build();
        property.build();

        for(int i=0; i < 7; i++)
            System.out.println(property.draw(i,null));
    }

    @Test
    void testPlayerSelectWrongOption() {
        setMockInput("a\n3\n1\na\n");
        property.interact(player);
    }

    @Test
    void testPlayerCanBuyProperty() {
        setMockInput("1\ny\n");
        property.interact(player);
    }

    @Test
    void testPlayerCanNotBuyProperty() {
        player.payMoney(2000);
        setMockInput("1\ny\n");
        property.interact(player);
    }
}
