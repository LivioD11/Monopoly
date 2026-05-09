package ch.supsi.monopoly.board.interfaces;

import ch.supsi.monopoly.Owner;
import ch.supsi.monopoly.Player;

public interface Taxable {
    Player getInteractedPlayer();
    Owner getOwner();
    int getValue();

    default void tax() {
        if(!getInteractedPlayer().equals(getOwner()))
            getInteractedPlayer().payMoney(getValue());
    }
}
