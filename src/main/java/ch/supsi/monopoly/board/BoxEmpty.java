package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.cli.Color;

public class BoxEmpty extends Box
{
    public BoxEmpty(){
        super("CASELLA VUOTA");
        setDescription("Non implementata");
        color = Color.WHITE;
    }

    @Override
    public void applyEffect(Player player) {

    }
}
