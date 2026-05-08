package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Config;
import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.cli.Color;

public class BoxStart extends Box {
    private static final int BONUS = Config.getInt("box.start.bonus",0);

    public BoxStart(String name) {
        super(BONUS, name, Color.WHITE);
        this.setDescription("Ritira "+value);
    }

    @Override
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
