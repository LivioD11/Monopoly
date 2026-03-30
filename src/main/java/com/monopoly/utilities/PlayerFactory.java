package com.monopoly.utilities;

import com.monopoly.Bank;
import com.monopoly.Player;

import java.util.Scanner;

public class PlayerFactory {
    private Player[] players;
    private Scanner scanner;

    public PlayerFactory(Scanner scanner, int playersNumber) {
        this.scanner = scanner;
        this.players = new Player[playersNumber];
    }

    public Player[] setup(Bank bank) {
        for (int i = 0; i < players.length; i++) {
            System.out.println("\n--- Configurazione Giocatore " + (i + 1) + " ---");
            String name = getUniqueName();
            char sign = getUniqueSign();
            players[i] = new Player(name, sign, bank);
        }
        return players;
    }

    private String getUniqueName() {
        while (true) {
            String name = ScannerUtilities.getInputString(scanner, "Nome: ");
            if (isNameAvailable(name)) return name;
            System.out.println("Errore: Nome già occupato.");
        }
    }

    private char getUniqueSign() {
        while (true) {
            char sign = ScannerUtilities.getInputChar(scanner, "Simbolo: ");
            if (isSignAvailable(sign)) return sign;
            System.out.println("Errore: Simbolo già occupato.");
        }
    }

    private boolean isNameAvailable(String name) {
        for (Player player : players) {
            if (player != null && player.getName().equalsIgnoreCase(name)) return false;
        }
        return true;
    }

    private boolean isSignAvailable(char sign) {
        for (Player player : players) {
            if (player != null && player.getSign() == sign) return false;
        }
        return true;
    }
}