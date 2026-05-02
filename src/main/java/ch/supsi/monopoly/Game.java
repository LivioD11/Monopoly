package ch.supsi.monopoly;

import ch.supsi.monopoly.board.Board;
import ch.supsi.monopoly.board.Box;
import ch.supsi.monopoly.board.BoxStart;
import ch.supsi.monopoly.board.jail.BoxJail;
import ch.supsi.monopoly.cli.Color;
import ch.supsi.monopoly.cli.TextFormatter;
import ch.supsi.monopoly.utilities.*;

/**
 * Classe principale che gestisce il ciclo di vita e la logica di una partita a Monopoly.
 * <p>
 * Coordina l'inizializzazione dei giocatori tramite {@link PlayerFactory}, la creazione
 * del tabellone e la gestione dei turni. Implementa il ciclo di gioco principale
 * e gestisce le condizioni di vittoria/sconfitta (bancarotta).
 * </p>
 *
 * @see Board
 * @see Player
 * @see PlayerFactory
 */
public class Game {
    /** Il tabellone di gioco contenente le caselle. */
    private final Board board;

    /** Array dei giocatori partecipanti alla partita. */
    private final Player[] players;

    /** Numero totale di giocatori, recuperato dalla configurazione. */
    private static final int PLAYERS_NUMBER = Config.getInt("game.players.number", 0);

    /** Menu interattivo per gestire le azioni del giocatore durante il turno. */
    private Menu menu;

    /** Indice incrementale per determinare a quale giocatore spetta il turno. */
    private int turnIndex;

    /** Flag che indica se la partita è terminata. */
    private boolean isGameOver;

    /**
     * Costruttore della classe Game.
     * Inizializza il sistema di setup, configura il menu delle azioni,
     * istanzia i giocatori e il tabellone, e avvia il loop di gioco tramite {@link #play()}.
     */
    public Game() {
        PlayerFactory setupManager = new PlayerFactory(PLAYERS_NUMBER);
        this.menu = new Menu("");
        this.setupMenu();
        this.isGameOver = false;
        this.turnIndex = 0;
        this.players = setupManager.setup();
        this.board = new Board(players);
        play();
    }

    /**
     * Configura le opzioni disponibili nel menu interattivo:
     * <ul>
     * <li>Visualizzazione del saldo corrente del giocatore di turno.</li>
     * <li>Lancio dei dadi per l'esecuzione del movimento e degli effetti.</li>
     * </ul>
     */
    private void setupMenu(){
        // Implementazione delle opzioni del menu
    }

    /**
     * Avvia il ciclo principale del gioco (Game Loop).
     * Continua a richiedere input al giocatore di turno finché {@code isGameOver} non diventa {@code true}.
     */
    private void play() {
        board.draw();
        while (!isGameOver) {
            Player currentPlayer = players[turnIndex % PLAYERS_NUMBER];
            menu.setDescription("Turno di "+currentPlayer.getName());
            menu.displayAndSelect();
        }
        System.out.println("\n--- GARA CONCLUSA ---");
    }

    /**
     * Esegue la logica di un singolo turno di gioco:
     * <ol>
     * <li>Lancio dei dadi.</li>
     * <li>Verifica dello stato di detenzione (Prigione) e possibile rilascio.</li>
     * <li>Spostamento del giocatore sul tabellone.</li>
     * <li>Gestione del passaggio dal "VIA!".</li>
     * <li>Applicazione dell'effetto della casella di atterraggio.</li>
     * <li>Verifica della bancarotta.</li>
     * </ol>
     */
    private void executeTurn() {
        // Logica di esecuzione del turno
    }

    /**
     * Verifica se i due dadi lanciati presentano lo stesso valore (doppio).
     * Viene utilizzato principalmente per la logica di uscita dalla prigione.
     * * @param roll1 Risultato del primo dado.
     * @param roll2 Risultato del secondo dado.
     * @return {@code true} se i valori sono uguali, {@code false} altrimenti.
     */
    private boolean checkEqualityDice(int roll1, int roll2) {
        return roll1 == roll2;
    }
}