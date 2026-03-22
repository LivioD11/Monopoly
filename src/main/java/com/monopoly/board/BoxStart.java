package com.monopoly.board;

public class BoxStart extends Box {
    private static final int BONUS = 100;

    public BoxStart(int value, String name) {
        super(value, name);
        this.setDescription("Ritira "+value);
    }

    public void applyEffect(Player player) {}

    public static int getBonus(){
        return BONUS;
    }
}
