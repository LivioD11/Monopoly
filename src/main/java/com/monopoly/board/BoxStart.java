package com.monopoly.board;

import com.monopoly.Player;
import com.monopoly.board.interfaces.Refundable;

public class BoxStart extends Box implements Refundable {
    private static final int BONUS = 100;

    public BoxStart(String name) {
        super(BONUS, name);
        this.setDescription("Ritira "+value);
    }

    public void refund(Player player){

    }

    public void applyEffect(Player player) {
        this.refund(player);
    }

    public static int getBonus(){
        return BONUS;
    }
}
