package ch.supsi.monopoly.board.property;

import ch.supsi.monopoly.Config;

public class Hotel extends Building{
    protected static final int MIN_PRICE = Config.getInt("building.hotel.price.min",0);
    protected static final int MAX_PRICE = Config.getInt("building.hotel.price.max",0);
    protected static final int VALUE = Config.getInt("building.hotel.value",0);

    public Hotel(){
        super(generatePrice());
    }

    private static int generatePrice() {
        return (int) (Math.random() * (MAX_PRICE - MIN_PRICE + 1)) + MIN_PRICE;
    }


}
