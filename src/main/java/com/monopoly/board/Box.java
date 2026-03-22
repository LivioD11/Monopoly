package com.monopoly.board;

import com.monopoly.Player;
import com.monopoly.cli.TextColorizer;

public abstract class Box {
    protected static final int TOLL_MIN = 50;
    protected static final int TOLL_MAX = 150;
    protected final int value;
    protected String name;
    protected String description;
    private String[] representation;

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

    //

    private static int generateRandomNum() {
        return (int) (Math.random() * (TOLL_MAX - TOLL_MIN + 1)) + TOLL_MIN;
    }

    private void updateRepresentation(){
        this.representation  = new String[]{
                "-".repeat(24),
                "|"+TextColorizer.padAnsi(this.name,22)+"|",
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

    public String draw(int index) {
        if (index < 0 || index >= representation.length) {
            return "";
        }
        return representation[index];
    }

    /**
     * Metodo per interagire con le classi figlie
     * in modo generico.
     * @param player
     */
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
