package ch.supsi.monopoly.board.jail;

import ch.supsi.monopoly.Config;
import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.Box;

public class BoxJail extends Box {
    private static final int JAIL_TIME = Config.getInt("box.jail.time", 0);

    public BoxJail(){
        super("Prigione");
    }

    @Override
    public void applyEffect(Player player) {

    }

    @Override
    public String toString() {
        return String.format("[ Casella: %s | Effetto: %s ]",
                this.name, this.description);
    }

}
