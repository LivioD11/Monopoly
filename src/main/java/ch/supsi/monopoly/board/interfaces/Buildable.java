package ch.supsi.monopoly.board.interfaces;

import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.property.Building;
import ch.supsi.monopoly.board.property.DevelopmentLevel;
import ch.supsi.monopoly.board.property.Hotel;
import ch.supsi.monopoly.board.property.House;
import ch.supsi.monopoly.cli.Color;
import ch.supsi.monopoly.cli.TextFormatter;

import java.util.List;

public interface Buildable {
    boolean getIsBuildable();

    void bankGetbackProperty();

    void setLevel(DevelopmentLevel level);
    void updateRepresentation();
    Player getInteractedPlayer();
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

    /**
     * Aggiunge una casa o un hotel alla proprietà rispettando i limiti.
     */
    default void build() {
        if (!getIsBuildable()) return;

        if (canBuildHouse()) {
            executeBuild(new House(), DevelopmentLevel.HOUSES, "una casa");
        } else if (canBuildHotel()) {
            executeBuild(new Hotel(), DevelopmentLevel.HOTEL, "un albergo");
        }
    }

    default void executeBuild(Building building, DevelopmentLevel nextLevel, String buildingName) {
        if (getInteractedPlayer().getBalance() < building.getPrice()) {
            System.out.println(getInteractedPlayer() + TextFormatter.color(" saldo insufficiente", Color.RED));
            return;
        }

        if (nextLevel == DevelopmentLevel.HOTEL) {
            getBuildings().clear(); // Rimuove le case per far posto all'hotel
        }

        getBuildings().add(building);
        setLevel(nextLevel);
        getInteractedPlayer().payMoney(building.getPrice());
        updateRepresentation();

        System.out.println(getInteractedPlayer() + TextFormatter.color(" ha costruito " + buildingName, Color.YELLOW));
    }
}
