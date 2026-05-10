package ch.supsi.monopoly;

import ch.supsi.monopoly.board.Board;
import ch.supsi.monopoly.board.Box;
import ch.supsi.monopoly.board.BoxStart;
import ch.supsi.monopoly.board.jail.BoxJail;
import ch.supsi.monopoly.board.property.PropertyManager;
import ch.supsi.monopoly.cli.Color;
import ch.supsi.monopoly.cli.TextFormatter;
import ch.supsi.monopoly.utilities.*;

/**
 * Classe principale che gestisce il ciclo di vita e la logica di una partita a Monopoly.
 * Coordina l'inizializzazione dei giocatori, il tabellone e la turnazione.
 */
public class Game {
    private final Board board;
    private final Player[] players;
    private static final int PLAYERS_NUMBER = Config.getInt("game.players" +
            ".number", 1);
    private Menu menu;
    private int turnIndex;
    private boolean isGameOver;

    /**
     * Inizializza una nuova partita, configura il menu e avvia il loop di gioco.
     */
    public Game() {
        PlayerFactory setupManager = new PlayerFactory(PLAYERS_NUMBER);
        this.menu = new Menu("",true);
        this.setupMenu();
        this.isGameOver = false;
        this.turnIndex = 0;
        this.players = setupManager.setup();
        this.board = new Board(players);
    }

    public void start() {
        this.play();
    }

    /**
     * Configura le opzioni interattive del menu di gioco.
     */
    private void setupMenu(){
        Option option1 = new Option(
                "Visualizza saldo",
                () -> {
                    Player player = players[turnIndex % PLAYERS_NUMBER];
                    System.out.println(player.toString()+ " " + TextFormatter.color(TextFormatter.formatCurrency(player.getBalance()), Color.YELLOW));
                }
        );

        Option option2 = new Option(
                "Tira dadi",
                () -> {
                    this.executeTurn();
                }
        );

        menu.addOption(option1);
        menu.addOption(option2);
    }

    /**
     * Avvia il ciclo principale del gioco finché non viene raggiunta una condizione di fine partita.
     */
    private void play() {
        board.draw();
        while (!isGameOver) {
            this.executeRound();
        }
        System.out.println("\n--- GARA CONCLUSA ---");
    }

    /**
     * Esegue un giro completo: ogni giocatore effettua un turno.
     */
    private void executeRound() {
        System.out.println(TextFormatter.color("\n--- INIZIO NUOVO GIRO ---", Color.PURPLE));

        for (int i = 0; i < PLAYERS_NUMBER; i++) {
            // Se qualcuno è andato in bancarotta durante il giro, fermiamo tutto
            if (isGameOver) break;

            // Impostiamo l'indice del turno corrente per il giocatore i-esimo
            this.turnIndex = i;
            Player currentPlayer = players[turnIndex];

            String description = TextFormatter.color("TURNO DI " + currentPlayer.getName().toUpperCase(), Color.CYAN);
            menu.setDescription(description);

            // Visualizza il menu e attendi che il giocatore scelga "Tira dadi"
            menu.displayAndSelect();

            this.tick();
        }
    }

    /**
     * Esegue la logica di un singolo turno: lancio dadi, gestione prigione, movimento e rendite.
     */
    protected void executeTurn() {
        Player player = players[this.turnIndex];

        Dice.RollResult result = Dice.rollMultiple(2);

        System.out.println("\n" + player.toString() + " ha lanciato i dadi: " + result.total());

        // Se il giocatore è inattivo (prigione) e non fa un doppio, il turno finisce
        if (player.getStatus().equals(PlayerStatus.INACTIVE) && !result.allSame()) {
            turnIndex++; // Incremento il turno per passare al prossimo
            return;
        }

        // Se è inattivo ma ha fatto un doppio, viene rilasciato
        if (player.getStatus().equals(PlayerStatus.INACTIVE)) {
            BoxJail.getInstance().releasePrisoner(player);
        }

        int oldPos = player.getPosition();
        player.move(result.total());
        board.draw();
        int newPos = player.getPosition();

        // Logica Passaggio dal Via
        if (newPos < oldPos) {
            player.receiveMoney(BoxStart.getBonus());
            System.out.println("Sei passato dal VIA! + " + TextFormatter.formatCurrency(BoxStart.getBonus()));
        }

        Box currentBox = board.getBox(newPos);
        System.out.println("Atterrato su: " + currentBox.getName());
        currentBox.applyEffect(player);

        if (player.isBroke()) {
            System.out.println(player.toString() + " è andato in bancarotta!");
            isGameOver = true;
        }
    }

    protected void tick(){
        BoxJail.getInstance().processSentences();
        PropertyManager.getInstance().checkProperties();
    }

    // Setters

    protected void setTurnIndex(int turnIndex){
        if(turnIndex >= PLAYERS_NUMBER)
            return;

        if(turnIndex < 0)
            return;

        this.turnIndex = turnIndex;
    }

    // Getters

    public Board getBoard() {
        return board;
    }

    public Player[] getPlayers() {
        return players;
    }

    public Menu getMenu() {
        return menu;
    }

    public int getTurnIndex() {
        return turnIndex;
    }

    public boolean isGameOver() {
        return isGameOver;
    }
}