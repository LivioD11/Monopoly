package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Config;
import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.interfaces.Taxable;

public class BoxLuxury extends Box implements Taxable {
    private static final int LUXURY_TAX_AMOUNT = Config.getInt("box.luxury.tax",0);

    public BoxLuxury(String name) {
        super(LUXURY_TAX_AMOUNT,name);
    }

    public void tax(Player player) {
        player.payMoney(this.value);
    }

    @Override
    public void applyEffect(Player player) {
        this.tax(player);
    }

    @Override
    public String toString() {
        return String.format("[ Cella : tassa di %d CHF (%s) ]",
                this.value, this.name);
    }
}
