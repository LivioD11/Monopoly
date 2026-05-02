package ch.supsi.monopoly.board.jail;

import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.Box;
import ch.supsi.monopoly.board.interfaces.Jailable;

/**
 * Rappresenta la casella "Vai in Prigione" nel tabellone del Monopoly.
 * <p>
 * Quando un giocatore atterra su questa casella, viene attivato l'effetto
 * che lo trasferisce immediatamente alla casella della prigione,
 * terminando il suo turno o applicando le restrizioni previste dal gioco.
 * </p>
 * * @see Box
 * @see Jailable
 */
public class BoxGoToJail extends Box implements Jailable {
    /**
     * Crea una nuova istanza della casella "Vai in Prigione" con il nome predefinito.
     */
    public BoxGoToJail(){
        super("Vai in Prigione");
    }

    /**
     * Gestisce il trasferimento logico del giocatore verso la prigione.
     * Utilizza l'istanza unica di {@link BoxJail} per processare il posizionamento.
     * * @param player Il giocatore che deve essere spedito in prigione.
     */
    @Override
    public void sendToJail(Player player) {
        BoxJail.getInstance();
        player.goToPrison();
    }

    /**
     * Esegue l'effetto della casella quando un giocatore vi atterra sopra.
     * In questo caso, invoca il metodo {@link #sendToJail(Player)}.
     * * @param player Il giocatore che è atterrato sulla casella.
     */
    @Override
    public void applyEffect(Player player) {
        this.sendToJail(player);
    }
}
