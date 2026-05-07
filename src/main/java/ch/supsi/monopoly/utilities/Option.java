package ch.supsi.monopoly.utilities;

import java.util.Objects;

/**
 * Rappresenta una singola voce all'interno di un menu.
 * Ogni opzione è composta da un messaggio descrittivo e un'azione eseguibile.
 * * @author Lorenzo
 * @version 1.0
 */
public class Option {
    private String message;
    private Runnable action;

    /**
     * Costruisce una nuova Option.
     * * @param message Il testo che verrà visualizzato nel menu.
     * @param action  L'azione (sotto forma di lambda) da eseguire quando l'opzione viene selezionata.
     */
    public Option(String message, Runnable action) {
        this.message = message;
        this.action = action;
    }

    /**
     * Esegue l'azione associata a questa opzione.
     */
    public void trigger() {
        if (action != null) {
            action.run();
        }
    }

    /**
     * Restituisce il messaggio descrittivo dell'opzione.
     * * @return Il messaggio dell'opzione.
     */
    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Option other)) return false;

        return Objects.equals(message, other.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message);
    }
}
