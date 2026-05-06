package ch.supsi.monopoly.board.property;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PropertyManagerTest {
    @Test
    public void testIncrementSum() {
        int expected = 2;
        PropertyManager.getIstance().increment();
        PropertyManager.getIstance().increment();
        assertEquals(expected, PropertyManager.getIstance().getProva());
    }
}
