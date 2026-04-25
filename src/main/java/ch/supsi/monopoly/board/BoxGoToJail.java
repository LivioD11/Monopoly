package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.interfaces.Jailable;

public class BoxGoToJail extends Box implements Jailable {

    public BoxGoToJail(){
        super("Vai in Prigione");
    }

    @Override
    public void sendToJail(Player player) {

    }

    @Override
    public void applyEffect(Player player) {

    }
}
