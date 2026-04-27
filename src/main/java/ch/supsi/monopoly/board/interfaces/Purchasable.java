package ch.supsi.monopoly.board.interfaces;

import ch.supsi.monopoly.Owner;

public interface Purchasable {
    boolean buy(Owner buyer);
    Owner getOwner();
}
