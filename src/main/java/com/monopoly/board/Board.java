package com.monopoly.board;

import com.monopoly.Game;
import com.monopoly.cli.Color;
import com.monopoly.cli.TextFormatter;
import com.monopoly.utilities.FileUtilities;

public class Board {
    private Box[] boxes;
    private static final String[] STREET_NAME = FileUtilities.leggiLineeFile("src/main/java/resources/street_names.csv");
    private static final int COLUMNS = 9;
    private static final int ROWS = 9;
    public static final int BOX_NUMBER = ROWS * 2 + (COLUMNS - 2) * 2;
    public static final int INDEX_START = 16;
    public static final int INDEX_STSUD = ROWS/2;
    public static final int INDEX_STOVEST = INDEX_STSUD + (ROWS - 1);
    public static final int INDEX_STNORD = INDEX_STOVEST + (ROWS - 1);
    public static final int INDEX_STEST = INDEX_STNORD + (ROWS - 1);
    public static final int INDEX_LUX = INDEX_START + (ROWS - 1);
    public static final int INDEX_HER = INDEX_START - 2;


    private Game game;


    public Board(Game game) {
        this.boxes = new Box[BOX_NUMBER];
        this.game = game;
        int nomeIndex = 0;

        for (int i = 0; i < BOX_NUMBER; i++) {
            boxes[i] = switch (i) {
                case INDEX_START   -> new BoxStart("VIA");
                case INDEX_STSUD   -> new BoxStation("STAZIONE SUD");
                case INDEX_STOVEST -> new BoxStation("STAZIONE OVEST");
                case INDEX_STNORD  -> new BoxStation("STAZIONE NORD");
                case INDEX_STEST   -> new BoxStation("STAZIONE EST");
                case INDEX_LUX     -> new BoxLuxury("TASSA DI LUSSO");
                case INDEX_HER     -> new BoxAssets("TASSA PATRIMONIALE");
                default -> {
                    String name = (nomeIndex < STREET_NAME.length) ?
                            STREET_NAME[nomeIndex] : "Via Generica";
                    nomeIndex++;
                    String colorfulName = TextFormatter.color(name, Color.random());
                    yield new BoxProperty(colorfulName);
                }
            };
        }

    }



    public void draw() {
        final String emptySpace = " ".repeat(24);

        for (int row = 0; row < ROWS; row++) {
            // Determiniamo i limiti di disegno per questa riga di caselle
            int startIndex = (row == 0 || row == ROWS - 1) ? 0 : 1;
            int endIndex = (row == ROWS - 2) ? Box.TOTAL_BOX_HEIGHT - 1 : Box.TOTAL_BOX_HEIGHT;

            // Ciclo sulle singole linee di testo che compongono una casella
            for (int actualHeight = startIndex; actualHeight < endIndex; actualHeight++) {

                for (int col = 0; col < COLUMNS; col++) {

                    if (isBorder(row, col)) {
                        int idCell = getBoxIndex(row, col);
                        // Recuperiamo i segni dei giocatori (se presenti)
                        char[] players = game.getSignsAtIndex(idCell);

                        // Inversione del controllo: passiamo l'altezza e i player,
                        // la Box sa cosa disegnare.
                        System.out.print(this.boxes[idCell].draw(actualHeight, players));
                    } else {
                        // Siamo all'interno del tabellone
                        System.out.print(emptySpace);
                    }
                }
                System.out.println(); // Fine della linea di testo
            }
        }
    }

    /**
     * Metodo di supporto per rendere il codice più leggibile
     */
    private boolean isBorder(int row, int col) {
        return row == 0 || row == ROWS - 1 || col == 0 || col == COLUMNS - 1;
    }

    /**
     * Restituisce una stringa formattata contenente i simboli dei giocatori
     * presenti su una determinata casella.
     */


    // Getters

    private int getBoxIndex(int row, int col) {

        // riga superiore
        if (row == 0)
            return col;

        // colonna destra
        if (col == COLUMNS - 1)
            return (COLUMNS - 1) + row;

        // riga inferiore
        if (row == ROWS - 1)
            return (COLUMNS - 1) + (ROWS - 1) + (COLUMNS - 1 - col);

        // colonna sinistra
        return (COLUMNS - 1) * 2 + (ROWS - 1) + (ROWS - 1 - row);
    }

    public Box getBox(int value) {
        return boxes[value];
    }

}
