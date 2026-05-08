package ch.supsi.monopoly.board.interfaces;

import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.property.Building;
import ch.supsi.monopoly.board.property.DevelopmentLevel;
import ch.supsi.monopoly.board.property.Hotel;
import ch.supsi.monopoly.board.property.House;

import java.util.List;

public interface Buildable {
    void build(Player player);

    boolean getIsBuildable();

    void bankGetbackProperty();

    List<Building> getBuildings();
    DevelopmentLevel getLevel();
    int getHousesLimit();
    int getHotelsLimit();

    default int countHouses() {
        if (getBuildings() == null) return 0;
        return (int) getBuildings().stream().filter(b -> b instanceof House).count();
    }

    default int countHotels() {
        if (getBuildings() == null) return 0;
        return (int) getBuildings().stream().filter(b -> b instanceof Hotel).count();
    }

    default boolean canBuildHouse() {
        DevelopmentLevel lvl = getLevel();
        return (lvl == DevelopmentLevel.EMPTY || lvl == DevelopmentLevel.HOUSES)
                && countHouses() < getHousesLimit()
                && countHotels() == 0;
    }

    default boolean canBuildHotel() {
        DevelopmentLevel lvl = getLevel();
        return (lvl == DevelopmentLevel.HOUSES || lvl == DevelopmentLevel.HOTEL)
                && countHouses() == getHousesLimit()
                && countHotels() < getHotelsLimit();
    }
}
