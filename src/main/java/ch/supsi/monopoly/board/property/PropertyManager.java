package ch.supsi.monopoly.board.property;

import java.util.ArrayList;
import java.util.List;

public class PropertyManager {
    private List<BoxProperty> properties;
    private static PropertyManager propertyManager;
    private int prova;

    private PropertyManager(){
        this.properties = new ArrayList<>();
        prova = 0;
    }

    public static PropertyManager getIstance() {
        if(propertyManager == null)
            return new PropertyManager();
        else
            return propertyManager;
    }

    public int getProva() {
        return prova;
    }

    public void increment() {
        prova++;
    }
}
