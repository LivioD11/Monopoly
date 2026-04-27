package ch.supsi.monopoly.board.property;

import ch.supsi.monopoly.Config;

public class Hotel extends Building{
    protected static final int MIN_PRICE = Config.getInt("building.hotel.price.min",0);
    protected static final int MAX_PRICE = Config.getInt("building.hotel.price.max",0);

    public Hotel(){

    }

    private void build(){

    }
}
