package ch.supsi.monopoly.board.property;

import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.cli.Color;
import ch.supsi.monopoly.cli.TextFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertyManagerTest {

    @BeforeEach
    public void setUp(){
        PropertyManager.getInstance().reset();
    }

    @Test
    public void testToString() {
        BoxProperty p1 = new BoxProperty("Proprietà 1");
        BoxProperty p2 = new BoxProperty("Proprietà 2");
        BoxProperty p3 = new BoxProperty("Proprietà 3");

        System.out.println(PropertyManager.getInstance().toString());
    }

    @Test
    public void testHasAllColorProperties(){
        Player player = new Player("Test Player", 'T');

        BoxProperty p1 = new BoxProperty("Proprietà 1");
        BoxProperty p2 = new BoxProperty("Proprietà 2");
        BoxProperty p3 = new BoxProperty("Proprietà 3");

        boolean property1EqualsProperty2 = p1.getColor().equals(p2.getColor());
        boolean property1EqualsProperty3 = p1.getColor().equals(p3.getColor());
        System.out.println(property1EqualsProperty2);
        System.out.println(property1EqualsProperty3);
        boolean expectedHasAllProperties = !(property1EqualsProperty3 || property1EqualsProperty2);

        boolean actualHasAllProperties = PropertyManager.getInstance().hasAllPropertiesOfColor(p1,player);

        p1.buy();

        System.out.println(
                "\n"+
                (actualHasAllProperties ?
                        TextFormatter.color("Ha tutte le proprietà di quel colore", Color.GREEN) :
                        TextFormatter.color("Non ha tutte le proprietà di quel colore", Color.RED)
                )+
                "\n");
        System.out.println(PropertyManager.getInstance().toString());

        assertEquals(expectedHasAllProperties,actualHasAllProperties);
    }
}
