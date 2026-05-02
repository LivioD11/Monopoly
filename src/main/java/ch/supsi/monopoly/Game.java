package ch.supsi.monopoly;

import ch.supsi.monopoly.board.Board;
import ch.supsi.monopoly.board.Box;
import ch.supsi.monopoly.board.BoxStart;
import ch.supsi.monopoly.board.jail.BoxJail;
import ch.supsi.monopoly.cli.Color;
import ch.supsi.monopoly.cli.TextFormatter;
import ch.supsi.monopoly.utilities.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private final Board board;
    private final Player[] players;
    private static final int PLAYERS_NUMBER = Config.getInt("game.players.number", 0);
    private Menu menu;
    private int turnIndex;
    private boolean isGameOver;

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

    private void setupMenu(){
        Option option1 = new Option(
                "Visualizza saldo",
                () -> {
                    Player player = players[turnIndex % PLAYERS_NUMBER];
                    System.out.println(player.toString() + TextFormatter.color(TextFormatter.formatCurrency(player.getBalance()), Color.YELLOW));
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

    private void play() {
        board.draw();
        while (!isGameOver) {
            Player currentPlayer = players[turnIndex % PLAYERS_NUMBER];
            menu.setDescription("Turno di "+currentPlayer.getName());
            menu.displayAndSelect();
        }
        System.out.println("\n--- GARA CONCLUSA ---");
    }

    private void executeTurn() {
        Player player = players[turnIndex % PLAYERS_NUMBER];

        int roll1 = Dice.roll();
        int roll2 = Dice.roll();
        int roll = roll1 + roll2;

        System.out.println("\n " + player.getName() + " ha lanciato i dadi: " + roll);
        if (player.getStatus().equals(PlayerStatus.INACTIVE) && !checkEqualityDice(roll1, roll2)) {
            return;
        }

        if (player.getStatus().equals(PlayerStatus.INACTIVE)) {
            BoxJail.getInstance().releasePrisoner(player);
        }


        //TODO: rilascio prigioniero
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

        if (player.isBroke()) {
            System.out.println(player.toString() + "è andato in bancarotta!");
            isGameOver = true;
        } else {
            board.draw();
            turnIndex++; // Prossimo turno solo se il giocatore ha mosso
        }
    }

    private boolean checkEqualityDice(int roll1, int roll2) {
        if (roll1 == roll2)
            return true;
        return false;
    }
}