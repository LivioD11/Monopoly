package com.monopoly.board;

import com.monopoly.Player;
import com.monopoly.board.interfaces.Taxable;

public class BoxStation extends Box implements Taxable {

    public BoxStation(String str) {
        super(str);
    }

    public void tax(Player player) {
        player.payMoney(this.getValue());
    }

    @Override
    public void applyEffect(Player player) {
        tax(player);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
