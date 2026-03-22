package com.monopoly.board;

import com.monopoly.Player;
import com.monopoly.board.interfaces.Taxable;

public class BoxAssets extends Box implements Taxable {

    public BoxAssets(String name) {
        super(name);
        setDescription("paga 10% patrimonio");
    }

    public void tax(Player player) {
        int tax = (int) (player.getBalance() * 0.1);
        player.payMoney(tax);
    }

    @Override
    public void applyEffect(Player player) {
        this.tax(player);
    }

    @Override
    public String toString() {
        return String.format("[ Cella tassa patrimoniale: %s tassa di: %d CHF (%s)]",
                name, value, description);
    }
}
