package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.cli.Color;

public class BoxParking extends Box {

    public BoxParking() {
        super("PARCHEGGIO", Color.WHITE);
    }

    @Override
    public void applyEffect(Player player) {

    }

    @Override
    public String toString() {
        return String.format("[ %s: Riposati un turno! ]", this.name);
    }
}
