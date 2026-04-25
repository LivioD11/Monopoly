package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.interfaces.Taxable;

public class BoxStation extends Box implements Taxable {

    public BoxStation(String str) {
        super(str);
    }

    public void tax(Player player) {
        player.payMoney(this.value);
    }

    @Override
    public void applyEffect(Player player) {
        tax(player);
    }

    @Override
    public String toString() {
        return String.format("[ Cella %s ]",
                this.name);
    }
}
