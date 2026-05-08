package ch.supsi.monopoly.board.property;

import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.PlayerStatus;
import ch.supsi.monopoly.cli.Color;
import ch.supsi.monopoly.cli.TextFormatter;

import java.util.ArrayList;
import java.util.List;

public class PropertyManager {
    private List<BoxProperty> properties;
    private static PropertyManager propertyManager;

    private PropertyManager(){
        this.properties = new ArrayList<>();
    }

    protected void reset(){
        propertyManager = new PropertyManager();
    }

    public static PropertyManager getInstance() {
        if(propertyManager == null)
            propertyManager = new PropertyManager();
        return propertyManager;
    }

    public void addProperty(BoxProperty property){
        this.properties.add(property);
    }

    public boolean hasAllPropertiesOfColor(BoxProperty actualProperty, Player player){
        Color color = actualProperty.getColor();
        for(BoxProperty property : properties){
            if(actualProperty.equals(property))
                continue;

            if(color.equals(property.getColor()) && !player.equals(property.getOwner()))
                return false;
        }

        return true;
    }

    public void checkProperties(){
        for(BoxProperty property:properties){
            if(property.getIsPurchasable())
                continue;

            PlayerStatus status =((Player) property.getOwner()).getStatus();
            if(status.equals(PlayerStatus.BROKE) || status.equals(PlayerStatus.DEFEATED))
                property.bankGetbackProperty();
        }
    }

    @Override
    public String toString(){
        String output = "";
        for(BoxProperty property:properties)
            output += " - "+ TextFormatter.color(property.toString(), property.getColor()) + "\n";

        return output;
    }
}
