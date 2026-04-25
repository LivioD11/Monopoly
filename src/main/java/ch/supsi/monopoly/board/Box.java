package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.cli.TextFormatter;

public abstract class Box {
    protected static final int TOLL_MIN = 50;
    protected static final int TOLL_MAX = 150;
    protected final int value;
    protected String name;
    protected String description;
    private String[] representation;

    public static final int PLAYER_ROW_HEIGHT = 5;
    public static final int TOTAL_BOX_HEIGHT = 7;

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
        this.updateRepresentation();
    }

    public Box(String name){
        this(generateRandomNum(),name);
    }

    private static int generateRandomNum() {
        return (int) (Math.random() * (TOLL_MAX - TOLL_MIN + 1)) + TOLL_MIN;
    }

    private void updateRepresentation(){
        this.representation  = new String[]{
                "-".repeat(24),
                "|"+ TextFormatter.padAnsi(this.name,22)+"|",
                this.value > 0 ? String.format("|%-22s|", this.description) :
                String.format("|%-22s|", ""),
                String.format("|%-22s|", ""),
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

        // Se siamo alla riga 5 e ci sono giocatori
        if (index == 5 && players != null && players.length > 0) {
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
