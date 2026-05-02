package ch.supsi.monopoly.board.jail;

import ch.supsi.monopoly.Config;
import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.PlayerStatus;
import ch.supsi.monopoly.board.Box;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta la casella della Prigione nel tabellone del Monopoly.
 * <p>
 * Implementa il pattern <b>Singleton</b> per garantire che esista un'unica istanza della prigione
 * durante la partita. Gestisce l'elenco dei giocatori detenuti tramite oggetti {@link Sentence}.
 * </p>
 * * @see Box
 * @see Sentence
 */
public class BoxJail extends Box {

    /** Numero di turni predefiniti di permanenza in prigione, caricati dalla configurazione. */
    private static final int JAIL_TIME = Config.getInt("box.jail.time", 0);

    /** Elenco delle condanne attive per i giocatori in prigione. */
    private List<Sentence> sentences;

    /** Istanza unica della casella Prigione. */
    private static BoxJail boxJail;

    /**
     * Costruttore privato per impedire l'istanziazione esterna (Singleton).
     * Inizializza il nome della casella e la lista delle condanne.
     */
    private BoxJail(){
        super("Prigione");
        this.sentences = new ArrayList<>();
    }

    /**
     * Restituisce l'istanza unica di {@code BoxJail}. Se non esiste, viene creata.
     * @return L'istanza Singleton di {@code BoxJail}.
     */
    public static BoxJail getInstance() {
        if (boxJail == null) {
            boxJail = new BoxJail();
        }
        return boxJail;
    }

    /**
     * Aggiunge un giocatore alla prigione creando una nuova condanna.
     * @param player Il giocatore da imprigionare.
     */
    public void addSentence(Player player) {
        Sentence sentence = new Sentence(player);
        sentences.add(sentence);
    }

    /**
     * Rimuove dalla lista interna tutte le condanne associate a giocatori
     * che sono tornati in stato {@link PlayerStatus#ACTIVE}.
     */
    public void cleanSentences(){
        for(Sentence sentence: sentences)
            if(sentence.getPrisoner().getStatus().equals(PlayerStatus.ACTIVE))
                sentences.remove(sentence);
    }

    /**
     * Applica l'effetto del transito sulla casella.
     * Per la prigione, l'effetto immediato all'atterraggio è nullo (semplice transito),
     * a meno che il giocatore non vi sia stato spedito forzatamente.
     * @param player Il giocatore che atterra sulla casella.
     */
    @Override
    public void applyEffect(Player player) {

    }

    /**
     * Rilascia immediatamente un giocatore specifico dalla prigione.
     * @param prisoner Il giocatore da liberare.
     */
    public void releasePrisoner(Player prisoner) {
        for(Sentence sentence : sentences) {
            if (sentence.getPrisoner().equals(prisoner)) {
                sentence.releasePrisoner();
            }
        }
        this.cleanSentences();
    }

    /**
     * Aggiorna lo stato di tutte le condanne correnti, facendo avanzare il tempo
     * di permanenza (serveTime) per ogni prigioniero e pulendo le condanne scadute.
     */
    public void processSentences() {
        sentences.forEach(Sentence::serveTime);
        this.cleanSentences();
    }

    /**
     * Restituisce una rappresentazione testuale dello stato della casella.
     * @return Stringa formattata contenente nome e descrizione della casella.
     */
    @Override
    public String toString() {
        return String.format("[ Casella: %s | Effetto: %s ]",
                this.name, this.description);
    }

}