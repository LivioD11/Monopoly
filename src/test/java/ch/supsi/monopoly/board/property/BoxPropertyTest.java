package ch.supsi.monopoly.board.property;

import ch.supsi.monopoly.Bank;
import ch.supsi.monopoly.Owner;
import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.cli.Color;
import ch.supsi.monopoly.utilities.ScannerUtilities;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoxPropertyTest {
    private Player player;

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
    }

    @Test
    @DisplayName("Il giocatore può acquistare la proprietà")
    void testPlayerCanBuyAProperty() {
        BoxProperty property = new BoxProperty("TestProperty");
        Owner oldOwner = property.getOwner();
        setMockInput("q\n");
        property.interact(player);
        property.buy();
        Owner newOwner = property.getOwner();

        assertEquals(Bank.getInstance(),oldOwner);
        assertEquals(player,newOwner);
    }

    @Test
    void testPlayerCanNotBuyAProperty() {
        BoxProperty property = new BoxProperty("TestProperty");
        Owner oldOwner = property.getOwner();
        player.payMoney(2000);
        setMockInput("1\ns\nq\n");
        property.interact(player);
        Owner newOwner = property.getOwner();

        assertEquals(Bank.getInstance(),oldOwner);
        assertEquals(Bank.getInstance(),newOwner);
    }

    @Test
    void testPlayerSelectWrongOption() {
        BoxProperty property = new BoxProperty("TestProperty");
        setMockInput("a\n3\n1\na\nq\n");
        property.interact(player);
    }

    @Test
    void testPlayerCanBuyProperty() {
        BoxProperty property = new BoxProperty("TestProperty");
        setMockInput("1\ns\n1\ns\nq\n");
        property.interact(player);

        for(int i=0;i<7;i++)
            System.out.println(property.draw(i,null));
    }

    @Test
    void testPlayerCanNotBuyProperty() {
        BoxProperty property = new BoxProperty("TestProperty");
        player.payMoney(2000);
        setMockInput("1\ns\nq\n");
        property.interact(player);
    }

    @Test
    void testPropertyIsPurchasable() {
        BoxProperty property = new BoxProperty("TestProperty");
        boolean expected = true;
        boolean actual = property.getIsPurchasable();
        assertEquals(expected,actual);
    }

    // Build

    @Test
    void testPlayerCanNotBuildOnBlack(){
        BoxProperty property = new BoxProperty("TestProperty", Color.BLACK);
        setMockInput("q\n");
        property.interact(player);
        property.buy();
        property.build();
        int expected = 0;
        int actual = property.getBuildings().size();
        assertEquals(expected,actual);
    }

    @Test
    void testPlayerCanNotBuildWithoutAllProperties(){
        BoxProperty property1 = new BoxProperty("TestProperty 1",Color.GREEN);
        BoxProperty property2 = new BoxProperty("TestProperty 2",Color.GREEN);
        setMockInput("q\n");
        property1.interact(player);
        property1.buy();
        property1.build();
        int expected = 0;
        int actual = property1.getBuildings().size();
        assertEquals(expected,actual);
    }

    @Test
    void testPlayerCanNotBuildWithoutFounds(){
        BoxProperty property = new BoxProperty("TestProperty 1",Color.GREEN);
        setMockInput("q\n");
        property.interact(player);
        property.buy();
        player.payMoney(2000);
        property.build();
        int expected = 0;
        int actual = property.getBuildings().size();
        assertEquals(expected,actual);
    }

    @Test
    void testPlayerCanBuildAnHouse(){
        PropertyManager.getInstance().reset();
        BoxProperty property = new BoxProperty("TestProperty 1",Color.GREEN);
        setMockInput("q\n");
        property.interact(player);
        property.buy();
        player.receiveMoney(30000);
        setMockInput("1\ns\nq\n");
        property.interact(player);
        int expected = 1;
        int actual = property.getBuildings().size();

        for(int i=0;i<7;i++)
            System.out.println(property.draw(i,null));

        assertEquals(expected,actual);
    }

    @Test
    void testPlayerCanBuildFourHouses(){
        PropertyManager.getInstance().reset();
        BoxProperty property = new BoxProperty("TestProperty 1",Color.GREEN);
        setMockInput("q\n");
        property.interact(player);
        property.buy();
        player.receiveMoney(30000);
        setMockInput("1\ns\n1\ns\n1\ns\n1\ns\nq\n");
        property.interact(player);
        int expected = 4;
        int actual = property.getBuildings().size();

        for(int i=0;i<7;i++)
            System.out.println(property.draw(i,null));

        assertEquals(expected,actual);
    }

    @Test
    void testPlayerCanBuildAnHotel(){
        PropertyManager.getInstance().reset();
        BoxProperty property = new BoxProperty("TestProperty 1",Color.GREEN);
        setMockInput("q\n");
        property.interact(player);
        property.buy();
        player.receiveMoney(30000);
        setMockInput("1\ns\n1\ns\n1\ns\n1\ns\n1\ns\nq\n");
        property.interact(player);
        int expected = 1;
        int actual = property.getBuildings().size();

        for(int i=0;i<7;i++)
            System.out.println(property.draw(i,null));

        assertEquals(expected,actual);
    }
}
