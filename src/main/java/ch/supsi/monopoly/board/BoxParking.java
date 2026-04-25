package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Player;

public class BoxParking extends Box {

    public BoxParking() {
        super("PARCHEGGIO");
    }

    @Override
    public void applyEffect(Player player) {

    }

    @Override
    public String toString() {
        return String.format("[ %s: Riposati un turno! ]", this.name);
    }
}
