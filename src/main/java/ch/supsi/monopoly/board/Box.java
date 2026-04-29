package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Bank;
import ch.supsi.monopoly.Config;
import ch.supsi.monopoly.Owner;
import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.cli.TextFormatter;

import java.util.Scanner;

public abstract class Box {
    protected static final int TOLL_MIN = Config.getInt("box.toll.min", 0);
    protected static final int TOLL_MAX = Config.getInt("box.toll.max", 0);
    protected final int value;
    protected String name;
    protected String description;
    protected Owner owner;
    protected String[] representation;


    public static final int PLAYER_ROW_HEIGHT = Config.getInt("box.height.player.row",0);
    public static final int TOTAL_BOX_HEIGHT = Config.getInt("box.height.total",0);

    // START: quando ci passi sopra prendi 100CHF  !!!
    // PARCHEGGIO: ci passi sopra e non fa nulla
    // TASSA DI LUSSO: ci finisci sopra e paghi 200CHF
    // TASSA SUL PATRIMONIO: ci finisci sopra e paghi il 10% del tuo patrimonio
    // PROPRIETA': dove paghi cifre random
    // STAZIONE: è al centro di ogni lato

    /**
     *  COSTRUTTORI
     */

    public Box(int value, String name) {
        this.value = value;
        this.name = name;
        this.description = "Paga "+this.value;
        this.owner = Bank.getInstance();
        this.updateRepresentation();
    }

    public Box(String name){
        this(generateRandomNum(),name);
    }

    private static int generateRandomNum() {
        return (int) (Math.random() * (TOLL_MAX - TOLL_MIN + 1)) + TOLL_MIN;
    }

    protected void updateRepresentation(){
        this.representation  = new String[]{
                "-".repeat(24),
                "|"+ TextFormatter.padAnsi(this.name,22)+"|",
                this.value > 0 ? String.format("|%-22s|", this.description) : String.format("|%-22s|", ""),
                this.owner != Bank.getInstance() ? String.format("|%-22s|", this.owner.toString()) : String.format("|%-22s|", ""),
                String.format("|%-22s|", ""),
                String.format("|%-22s|", ""),
                "-".repeat(24),
        };
    }

    @Override
    public String toString() {
        return String.format("[ Cella: %s valore: %d CHF (%s)]",
                name, value, description);
    }

    public String draw(int index, char[] players) {
        if (index < 0 || index >= representation.length) {
            return "";
        }

        // Se siamo alla riga PLAYER_ROW_HEIGHT e ci sono giocatori
        if (index == PLAYER_ROW_HEIGHT && players != null && players.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (char player : players) {
                sb.append("[").append(player).append("] ");
            }
            // Restituisce la riga formattata con i giocatori
            return String.format("|%-22s|", sb.toString().trim());
        }

        // Altrimenti restituisce la riga pre-calcolata nell'array representation
        return representation[index];
    }

    public abstract void applyEffect(Player player);

    public void interact(Scanner scanner, Player player){

    }

    // SETTERS

    public void setDescription(String description){
        this.description = description;
        this.updateRepresentation();
    }

    // GTTERS

    public int getValue() {
        return value;
    }

    public String getName(){
        return this.name;
    }

    public String getDescription(){
        return this.description;
    }
}
