package ch.supsi.monopoly.cli;

import ch.supsi.monopoly.Bank;
import ch.supsi.monopoly.Owner;
import ch.supsi.monopoly.board.property.BoxProperty;
import ch.supsi.monopoly.board.property.Building;
import ch.supsi.monopoly.board.property.DevelopmentLevel;

import java.util.List;

public final class Display {
    private Display(){

    }

    public static String[] boxRepresentation(String name, String description, int value, Owner owner, Color color){
        name = TextFormatter.color(name,color);

        String[] representation  = new String[]{
                "-".repeat(24),
                "|"+ TextFormatter.padAnsi(name,22)+"|",
                value > 0 ? String.format("|%-22s|", description) : String.format("|%-22s|", ""),
                owner != Bank.getInstance() ? String.format("|%-22s|", owner.toString()) : String.format("|%-22s|", ""),
                String.format("|%-22s|", ""),
                String.format("|%-22s|", ""),
                "-".repeat(24),
        };
        return representation;
    }

    public static String[] boxPropertyRepresentation(BoxProperty property){
        String name = TextFormatter.color(property.getName(),property.getColor());

        String[] representation  = new String[]{
                "-".repeat(24),
                "|"+ TextFormatter.padAnsi(name,22)+"|",
                property.getValue() > 0 ? String.format("|%-22s|", property.getDescription()) : String.format("|%-22s|", ""),
                String.format("|%-22s|", drawOwner(property.getOwner(), property.getPrice())),
                String.format("|%-22s|", ""),
                "|"+ TextFormatter.padAnsiAndEmoji(drawBuildings(property.getLevel(), property.getBuildings()),22)+"|",
                "-".repeat(24),
        };
        return representation;
    }

    private static String drawOwner(Owner owner, int price){
        String output = "Prezzo: "+TextFormatter.formatCurrency(price);
        if(!(owner instanceof Bank))
            output = owner.getName();
        return output;
    }

    private static String drawBuildings(DevelopmentLevel level, List<Building> buildings){
        String output = "";
        if(level == null)
            return output;

        if(level.equals(DevelopmentLevel.HOUSES)){
            for(Building building : buildings)
                output += "\uD83C\uDFE0";
            output += " ("+buildings.size()+" case)";
        }

        if(level.equals(DevelopmentLevel.HOTEL))
            output += "\uD83C\uDFE8 (hotel)";
        return output;
    }
}
