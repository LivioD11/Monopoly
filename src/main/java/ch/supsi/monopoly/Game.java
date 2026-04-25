package ch.supsi.monopoly;

import ch.supsi.monopoly.board.Board;
import ch.supsi.monopoly.board.Box;
import ch.supsi.monopoly.board.BoxStart;
import ch.supsi.monopoly.cli.Color;
import ch.supsi.monopoly.cli.TextFormatter;
import ch.supsi.monopoly.utilities.MenuOption;
import ch.supsi.monopoly.utilities.PlayerFactory;
import ch.supsi.monopoly.utilities.ScannerUtilities;
import java.util.Scanner;

public class Game {
    private final Board board;
    private final Player[] players;
    private static final int PLAYERS_NUMBER = 4;

    public Game(Scanner scanner) {
        PlayerFactory setupManager = new PlayerFactory(scanner, PLAYERS_NUMBER);
        this.players = setupManager.setup();
        this.board = new Board(players);
        play(scanner);
    }

    private void play(Scanner scanner) {
        int turnIndex = 0;
        boolean isGameOver = false;

        board.draw();

        while (!isGameOver) {
            Player currentPlayer = players[turnIndex % PLAYERS_NUMBER];
            showMenu(currentPlayer);

            int choice = ScannerUtilities.getInputInt(scanner, "Scegli un'opzione: ");
            MenuOption option = MenuOption.fromInt(choice);

            switch (option) {
                case VIEW_BALANCE ->
                        System.out.println("Saldo di " + currentPlayer.getName() + ": " + TextFormatter.formatCurrency(currentPlayer.getBalance()));

                case ROLL_DICE -> {
                    executeTurn(currentPlayer);
                    if (currentPlayer.isBroke()) {
                        System.out.println(currentPlayer.getName() + "è andato in bancarotta!");
                        isGameOver = true;
                    } else {
                        board.draw();
                        turnIndex++; // Prossimo turno solo se il giocatore ha mosso
                    }
                }

                default -> System.out.println(TextFormatter.color("Opzione non valida! Riprova.", Color.YELLOW));
            }
        }
        System.out.println("\n--- GARA CONCLUSA ---");
    }

    private void executeTurn(Player player) {
        int roll = Dice.roll() + Dice.roll();
        System.out.println("\n " + player.getName() + " ha lanciato i dadi: " + roll);

        int oldPos = player.getPosition();
        player.move(roll);
        int newPos = player.getPosition();

        // Logica Passaggio dal Via (Se la nuova pos è "tornata indietro" matematicamente)
        if (newPos < oldPos) {
            player.receiveMoney(BoxStart.getBonus());
            System.out.println("✨ Sei passato dal VIA! + " + TextFormatter.formatCurrency(BoxStart.getBonus()));
        }

        Box currentBox = board.getBox(newPos);
        System.out.println(" Atterrato su: " + currentBox.getName());
        currentBox.applyEffect(player);
    }

    private void showMenu(Player player) {
        System.out.println(TextFormatter.color("\n=== TURNO DI: " + player.getName().toUpperCase() + " ===",Color.CYAN));
        for (MenuOption option : MenuOption.values()) {
            if (option != MenuOption.UNKNOWN) System.out.println(option);
        }
    }
}