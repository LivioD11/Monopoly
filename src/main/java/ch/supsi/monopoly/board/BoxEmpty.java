package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.cli.Color;

public class BoxEmpty extends Box
{
    public BoxEmpty(){
        super("CASELLA VUOTA",Color.WHITE);
        setDescription("Non implementata");
    }

    @Override
    public void applyEffect(Player player) {

    }
}
