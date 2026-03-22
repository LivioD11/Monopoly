package com.monopoly.board;

import com.monopoly.Player;
import com.monopoly.board.interfaces.Taxable;

public class BoxLuxury extends Box implements Taxable {
    private static final int LUXURY_TAX_AMOUNT = 200;

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
        return String.format("[ Cella proprietà: %s tassa di: %d CHF (%s)]",
                name, value, description);
    }
}
