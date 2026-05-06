package ch.supsi.monopoly.board.interfaces;

import ch.supsi.monopoly.board.property.Building;
import ch.supsi.monopoly.board.property.DevelopmentLevel;
import ch.supsi.monopoly.board.property.Hotel;
import ch.supsi.monopoly.board.property.House;

import java.util.List;

public interface Buildable {
    void build();

    boolean getIsBuildable();

    void bankGetbackProperty();

    default int countHouses(List<Building>  buildings){
        int count = 0;

        if(buildings == null)
            return count;

        for (Building building : buildings)
            if(building instanceof House)
                count++;
        return count;
    }

    default int countHotels(List<Building> buildings){
        int count = 0;

        if(buildings == null)
            return count;

        for (Building building : buildings)
            if(building instanceof Hotel)
                count++;
        return count;
    }
}
