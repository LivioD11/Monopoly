package ch.supsi.monopoly.board.jail;

import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.Box;
import ch.supsi.monopoly.board.interfaces.Jailable;

public class BoxGoToJail extends Box implements Jailable {

    public BoxGoToJail(){
        super("Vai in Prigione");
    }


    @Override
    public void sendToJail(Player player) {
        BoxJail.getInstance(); //TODO: crea metodo add sentence
    }

    @Override
    public void applyEffect(Player player) {
        this.sendToJail(player);
    }
}
