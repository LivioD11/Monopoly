package ch.supsi.monopoly.utilities;

import ch.supsi.monopoly.Player;

import java.util.Scanner;

/**
 * Factory class responsabile della creazione e configurazione dei giocatori nel Monopoly.
 * <p>
 * Gestisce l'inizializzazione di un gruppo di giocatori, assicurandosi che ogni
 * partecipante abbia un nome e un simbolo identificativo univoco attraverso
 * un processo di validazione interattivo.
 * </p>
 * * @see Player
 * @see ScannerUtilities
 */
public class PlayerFactory {

    /** Array contenente i giocatori in fase di configurazione. */
    private Player[] players;

    /**
     * Inizializza la factory per un numero specifico di partecipanti.
     * * @param playersNumber Il numero totale di giocatori da creare per la partita.
     */
    public PlayerFactory(int playersNumber) {
        this.players = new Player[playersNumber];
    }

    /**
     * Avvia la procedura interattiva di configurazione per tutti i giocatori.
     * Per ogni giocatore, richiede input per nome e simbolo, verificandone l'univocità.
     * * @return Un array di oggetti {@link Player} completamente inizializzati.
     */
    public Player[] setup() {
        for (int i = 0; i < players.length; i++) {
            System.out.println("\n--- Configurazione Giocatore " + (i + 1) + " ---");
            String name = getUniqueName();
            char sign = getUniqueSign();
            players[i] = new Player(name, sign);
        }
        return players;
    }

    /**
     * Richiede un nome all'utente e continua a richiederlo finché non ne viene
     * inserito uno non ancora utilizzato dagli altri giocatori.
     * * @return Una stringa rappresentante il nome univoco scelto.
     */
    private String getUniqueName() {
        while (true) {
            String name = ScannerUtilities.getInputString("Nome: ").toUpperCase();
            if (isNameAvailable(name)) return name;
            System.out.println("Errore: Nome già occupato.");
        }
    }

    /**
     * Richiede un carattere (simbolo) all'utente e continua a richiederlo
     * finché non ne viene inserito uno univoco.
     * * @return Il carattere univoco scelto come pedina.
     */
    private char getUniqueSign() {
        while (true) {
            char sign = Character.toUpperCase(ScannerUtilities.getInputChar("Simbolo: "));
            if (isSignAvailable(sign)) return sign;
            System.out.println("Errore: Simbolo già occupato.");
        }
    }

    /**
     * Verifica se un nome è già stato assegnato a un altro giocatore nell'array.
     * Il confronto non è case-sensitive.
     * * @param name Il nome da controllare.
     * @return {@code true} se il nome è disponibile, {@code false} se è già occupato.
     */
    private boolean isNameAvailable(String name) {
        for (Player player : players) {
            if (player != null && player.getName().equalsIgnoreCase(name)) return false;
        }
        return true;
    }

    /**
     * Verifica se un simbolo è già stato scelto da un altro giocatore.
     * * @param sign Il carattere da controllare.
     * @return {@code true} se il simbolo è disponibile, {@code false} altrimenti.
     */
    private boolean isSignAvailable(char sign) {
        for (Player player : players) {
            if (player != null && player.getSign() == sign) return false;
        }
        return true;
    }
}