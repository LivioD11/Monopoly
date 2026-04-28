package ch.supsi.monopoly.board.property;

import ch.supsi.monopoly.Config;

public class House extends Building{
    protected static final int MIN_PRICE = Config.getInt("building.house.price.min",0);
    protected static final int MAX_PRICE = Config.getInt("building.house.price.max",0);
    protected static final int VALUE = Config.getInt("building.house.value",0);

    public House(){
        super(generatePrice());
    }

    private static int generatePrice() {
        return (int) (Math.random() * (MAX_PRICE - MIN_PRICE + 1)) + MIN_PRICE;
    }
}
