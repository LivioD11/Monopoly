package ch.supsi.monopoly.board.property;

import ch.supsi.monopoly.Bank;
import ch.supsi.monopoly.Config;
import ch.supsi.monopoly.Owner;
import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.Box;
import ch.supsi.monopoly.board.interfaces.Buildable;
import ch.supsi.monopoly.board.interfaces.Purchasable;
import ch.supsi.monopoly.board.interfaces.Taxable;

import java.util.List;

public class BoxProperty extends Box implements Taxable, Purchasable, Buildable {
    private static final int HOUSES_LIMIT = Config.getInt("box.property.houses.limit",0);
    private static final int HOTELS_LIMIT = Config.getInt("box.property.hotels.limit",0);
    private static final int PRICE_MIN = Config.getInt("box.property.price.min",0);
    private static final int PRICE_MAX = Config.getInt("box.property.price.max",0);
    private int price;
    private List<Building> buildings;

    public BoxProperty(String name) {
        super(name);
        this.price = generatePrice();
    }

    private static int generatePrice() {
        return (int) (Math.random() * (PRICE_MAX - PRICE_MIN + 1)) + PRICE_MIN;
    }

    public boolean buy(Owner buyer){
        // La proprietà è già acquistata.
        if(!(owner instanceof Bank))
            return false;

        // Il budget è insufficiente.
        if(price>buyer.getBalance())
            return false;

        buyer.payMoney(price);
        this.owner = buyer;
        return true;
    }

    // TODO: correggere la logica
    public void build(DevelopmentLevel level) {
        if (level == DevelopmentLevel.EMPTY || level == DevelopmentLevel.HOUSES) {
            if (countHouses(buildings) < HOUSES_LIMIT) {
                buildings.add(new House());
                level = DevelopmentLevel.HOUSES;
                return;
            }
        }

        if (countHouses(buildings) == HOUSES_LIMIT && countHotels(buildings) < HOTELS_LIMIT) {
            buildings.clear(); // opzionale: se le case vengono sostituite
            buildings.add(new Hotel());
            level = DevelopmentLevel.HOTEL;
            return;
        }

        // niente da fare: limite raggiunto
    }

    public void tax(Player player){
        player.payMoney(this.getValue());
    }

    public void applyEffect(Player player) {
        this.tax(player);
    }

    @Override
    public String toString() {
        return String.format("[ Cella proprietà: %s | Tassa: %d CHF ]",
                this.name, this.value);
    }

    // GETTERS

    public Owner getOwner(){
        return  this.owner;
    }
}
