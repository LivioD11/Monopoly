package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.interfaces.Taxable;

public class BoxProperty extends Box implements Taxable {

    public BoxProperty(String name) {
        super(name);
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
