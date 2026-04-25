package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Player;

public class BoxStart extends Box {
    private static final int BONUS = 100;

    public BoxStart(String name) {
        super(BONUS, name);
        this.setDescription("Ritira "+value);
    }

    public void applyEffect(Player player) {

    }

    public static int getBonus(){
        return BONUS;
    }

    @Override
    public String toString() {
        return String.format("[ Cella %s | Rimborso: %d CHF ]",
                this.name, this.value);
    }
}
