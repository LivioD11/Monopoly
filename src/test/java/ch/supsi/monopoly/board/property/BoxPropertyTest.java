package ch.supsi.monopoly.board.property;

import ch.supsi.monopoly.Bank;
import ch.supsi.monopoly.Owner;
import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.BoxAssets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoxPropertyTest {
    private Player player;
    private BoxProperty property;
    private Scanner createScanner(String input) {
        return new Scanner(new ByteArrayInputStream(input.getBytes()));
    }

    @BeforeEach
    void setUp() {
        Scanner scanner = createScanner("NomeUtente");
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
        Scanner scanner = createScanner("a\n6");
        property.interact(player);
    }

    @Test
    void testPlayerCanBuyProperty() {
        Scanner scanner = createScanner("1\ny");
        property.interact(player);
    }

    @Test
    void testPlayerCanNotBuyProperty() {
        player.payMoney(2000);
        Scanner scanner = createScanner("1\ny");
        property.interact(player);
    }
}
