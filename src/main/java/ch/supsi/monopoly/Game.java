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
 * Coordina l'inizializzazione dei giocatori, il tabellone e la turnazione.
 */
public class Game {
    private final Board board;
    private final Player[] players;
    private static final int PLAYERS_NUMBER = Config.getInt("game.players.number", 0);
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
            Player currentPlayer = players[turnIndex % PLAYERS_NUMBER];
            String description = TextFormatter.color("TURNO DI "+currentPlayer.getName().toUpperCase(),Color.CYAN);
            menu.setDescription(description);
            menu.displayAndSelect();
        }
        System.out.println("\n--- GARA CONCLUSA ---");
    }

    /**
     * Esegue la logica di un singolo turno: lancio dadi, gestione prigione, movimento e rendite.
     */
    protected void executeTurn() {
        board.draw();
        Player player = players[turnIndex % PLAYERS_NUMBER];

        int roll1 = Dice.roll();
        int roll2 = Dice.roll();
        int roll = roll1 + roll2;

        System.out.println("\n" + player.toString() + " ha lanciato i dadi: " + roll);

        // Se il giocatore è inattivo (prigione) e non fa un doppio, il turno finisce
        if (player.getStatus().equals(PlayerStatus.INACTIVE) && !checkEqualityDice(roll1, roll2)) {
            turnIndex++; // Incremento il turno per passare al prossimo
            return;
        }

        // Se è inattivo ma ha fatto un doppio, viene rilasciato
        if (player.getStatus().equals(PlayerStatus.INACTIVE)) {
            BoxJail.getInstance().releasePrisoner(player);
        }

        int oldPos = player.getPosition();
        player.move(roll);
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
        } else {

            turnIndex++;
        }
    }

    /**
     * Verifica se i due dadi hanno lo stesso valore.
     * @param roll1 Risultato primo dado.
     * @param roll2 Risultato secondo dado.
     * @return true se i dadi sono uguali.
     */
    private boolean checkEqualityDice(int roll1, int roll2) {
        return roll1 == roll2;
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