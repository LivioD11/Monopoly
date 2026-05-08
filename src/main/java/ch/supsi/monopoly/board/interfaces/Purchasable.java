package ch.supsi.monopoly.board.interfaces;

import ch.supsi.monopoly.Owner;
import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.cli.Color;
import ch.supsi.monopoly.cli.TextFormatter;

public interface Purchasable {
    int getPrice();
    boolean getIsPurchasable();
    void setOwner(Owner owner);
    Owner getOwner();
    Player getInteractedPlayer();

    /**
     * Gestisce l'acquisto della proprietà.
     */
    default boolean buy() {
        if (!getIsPurchasable()) {
            return false;
        }

        if (getPrice() > getInteractedPlayer().getBalance()) {
            System.out.println(TextFormatter.color("Saldo insufficiente", Color.RED));
            return false;
        }

        getInteractedPlayer().payMoney(getPrice());
        setOwner(getInteractedPlayer());

        System.out.println(String.format("%s " + TextFormatter.color("ha acquistato la proprietà", Color.YELLOW), getInteractedPlayer().toString()));
        return true;
    }
}
