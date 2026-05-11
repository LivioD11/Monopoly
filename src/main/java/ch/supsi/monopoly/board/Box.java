package ch.supsi.monopoly.board;

import ch.supsi.monopoly.*;
import ch.supsi.monopoly.cli.Color;
import ch.supsi.monopoly.cli.Display;
import ch.supsi.monopoly.cli.TextFormatter;

import java.util.List;


public abstract class Box {
    protected static final int TOLL_MIN = Config.getInt("box.toll.min", 0);
    protected static final int TOLL_MAX = Config.getInt("box.toll.max", 0);
    protected final int value;
    protected String name;
    protected String description;
    protected Owner owner;
    protected Player interactedPlayer;
    protected Color color;
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

    public Box(int value, String name, Color color) {
        this.value = value;
        this.name = name;
        this.description = "Paga: "+this.getValue();
        this.owner = Bank.getInstance();
        this.color = color;
        this.updateRepresentation();
    }

    public Box(int value, String name){
        this(value,name,Color.random());
    }

    public Box(String name, Color color){
        this(generateRandomNum(),name,color);
    }

    public Box(String name){
        this(generateRandomNum(),name);
    }

    private static int generateRandomNum() {
        return (int) (Math.random() * (TOLL_MAX - TOLL_MIN + 1)) + TOLL_MIN;
    }

    protected void updateRepresentation(){
        this.representation = Display.boxRepresentation(name,description,value,owner,color);
    }

    @Override
    public String toString() {
        return String.format("[ Cella: %s valore: %d CHF (%s)]",
                name, value, description);
    }

    public String draw(int index, List<Player> players) {
        if (index < 0 || index >= representation.length) {
            return "";
        }

        // Gestione riga dei giocatori
        if (index == PLAYER_ROW_HEIGHT && players != null && !players.isEmpty()) {
            StringBuilder playersContent = new StringBuilder();
            int visibleLength = 0;

            for (Player player : players) {
                String sign = "[" + player.getSign() + "] ";
                Color color = (player.getStatus() == PlayerStatus.INACTIVE) ? Color.RED : Color.CYAN;

                playersContent.append(TextFormatter.color(sign, color));
                visibleLength += sign.length(); // Contiamo solo i caratteri che si vedono davvero
            }

            // Calcoliamo il padding manualmente per ignorare i codici colore
            int totalWidth = 22;
            int paddingNeeded = Math.max(0, totalWidth - visibleLength);
            String padding = " ".repeat(paddingNeeded);

            return "|" + playersContent.toString() + padding + "|";
        }

        return representation[index];
    }

    public abstract void applyEffect(Player player);

    public void interact(Player player){

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

    public Color getColor(){ return this.color; }

    public Player getInteractedPlayer(){
        return this.interactedPlayer;
    }

    public Owner getOwner(){
        return  this.owner;
    }
}
