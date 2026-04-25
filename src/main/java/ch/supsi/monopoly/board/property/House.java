package ch.supsi.monopoly.board.property;

import ch.supsi.monopoly.Config;

public class House extends Building{
    protected static final int MIN_PRICE = Config.getInt("building.house.price.min",0);
    protected static final int MAX_PRICE = Config.getInt("building.house.price.max",0);
}
