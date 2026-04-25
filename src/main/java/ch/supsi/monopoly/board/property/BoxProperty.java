package ch.supsi.monopoly.board.property;

import ch.supsi.monopoly.Bank;
import ch.supsi.monopoly.Owner;
import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.Box;
import ch.supsi.monopoly.board.interfaces.Buildable;
import ch.supsi.monopoly.board.interfaces.Purchasable;
import ch.supsi.monopoly.board.interfaces.Taxable;

public class BoxProperty extends Box implements Taxable, Purchasable, Buildable {
    private int price;
    private Building[] buildings;

    public BoxProperty(String name) {
        super(name);
    }

    public void buy(Owner buyer){

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
}
