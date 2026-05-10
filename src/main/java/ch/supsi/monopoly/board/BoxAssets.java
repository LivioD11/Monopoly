package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.interfaces.Taxable;

public class BoxAssets extends Box implements Taxable {

    public BoxAssets(String name) {
        super(name);
        setDescription("paga 10% patrimonio");
    }

    @Override
    public void tax() {
        int tax = (int) (getInteractedPlayer().getBalance() * 0.1);
        getInteractedPlayer().payMoney(tax);
    }

    @Override
    public void applyEffect(Player player) {
        this.interactedPlayer = player;
        this.tax();
    }

    @Override
    public String toString() {
        return String.format("[ Casella: %s | Effetto: %s ]",
                this.name, this.description);
    }
}
