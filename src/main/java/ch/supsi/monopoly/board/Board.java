package ch.supsi.monopoly.board;

import ch.supsi.monopoly.Config;
import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.jail.BoxGoToJail;
import ch.supsi.monopoly.board.jail.BoxJail;
import ch.supsi.monopoly.board.property.BoxProperty;
import ch.supsi.monopoly.utilities.FileUtilities;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private Box[] boxes;
    private static final String[] STREET_NAME = FileUtilities.leggiLineeFile("src/main/resources/street_names.csv");
    private static final int COLUMNS = Config.getInt("board.columns", 1);
    private static final int ROWS = Config.getInt("board.rows", 1);
    public static final int BOX_NUMBER = ROWS * 2 + (COLUMNS - 2) * 2;
    public static final int INDEX_START = COLUMNS + ROWS - 2;
    public static final int INDEX_STATION_SUD = ROWS/2;
    public static final int INDEX_STATION_OVEST = INDEX_STATION_SUD + (ROWS - 1);
    public static final int INDEX_STATION_NORD = INDEX_STATION_OVEST + (ROWS - 1);
    public static final int INDEX_STATION_EST = INDEX_STATION_NORD + (ROWS - 1);
    public static final int INDEX_LUXURY = INDEX_START + (ROWS + 1);
    public static final int INDEX_ASSETS = INDEX_START - 2;
    public static final int INDEX_JAIL = INDEX_START + (ROWS - 1);
    public static final int INDEX_GO_TO_JAIL = COLUMNS -1;
    private Player[] players;

    public Board(Player[] players) {
        this.boxes = new Box[BOX_NUMBER];
        this.players = players;
        int nomeIndex = 0;

        for (int i = 0; i < BOX_NUMBER; i++) {
            // Angolo in basso a destra
            if (i == INDEX_START) {
                boxes[i] = new BoxStart("VIA");
            // In centro in alto
            } else if (i == INDEX_STATION_SUD) {
                boxes[i] = new BoxStation("STAZIONE SUD");
            // In centro a destra
            } else if (i == INDEX_STATION_OVEST) {
                boxes[i] = new BoxStation("STAZIONE OVEST");
            // In centro in basso
            } else if (i == INDEX_STATION_NORD) {
                boxes[i] = new BoxStation("STAZIONE NORD");
            // In centro a sinistra
            } else if (i == INDEX_STATION_EST) {
                boxes[i] = new BoxStation("STAZIONE EST");

            } else if (i == INDEX_LUXURY) {
                boxes[i] = new BoxLuxury("TASSA DI LUSSO");

            } else if (i == INDEX_ASSETS) {
                boxes[i] = new BoxAssets("TASSA PATRIMONIALE");
            // Angolo in basso a sinistra
            } else if (i == INDEX_JAIL) {
                boxes[i] = BoxJail.getInstance();
            // Angolo in alto a destra
            } else if (i == INDEX_GO_TO_JAIL) {
                boxes[i] = new BoxGoToJail();
            }else if (isEmptyBoxIndex(i)) {
                boxes[i] = new BoxEmpty();
            }else {
                String name = (nomeIndex < STREET_NAME.length)
                        ? STREET_NAME[nomeIndex]
                        : "Via Generica";

                nomeIndex++;
                boxes[i] = new BoxProperty(name);
            }
        }

    }

    private boolean isEmptyBoxIndex(int i) {
        // Esempio: inserisce una BoxEmpty ogni N caselle, o basati su un array di indici fissi
        return i == 2 || i == 7 || i == 17 || i == 22 || i == 33 || i == 38;
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
                        List<Player> boxPlayers = getPlayersAtIndex(idCell);

                        // Inversione del controllo: passiamo l'altezza e i player,
                        // la Box sa cosa disegnare.
                        System.out.print(this.boxes[idCell].draw(actualHeight, boxPlayers));
                    } else {
                        // Siamo all'interno del tabellone
                        System.out.print(emptySpace);
                    }
                }
                System.out.println(); // Fine della linea di testo
            }
        }
    }

    private boolean isBorder(int row, int col) {
        return row == 0 || row == ROWS - 1 || col == 0 || col == COLUMNS - 1;
    }

    // Getters
    private List<Player> getPlayersAtIndex(int index) {
        List<Player> boxPlayers = new ArrayList<>();
        for (Player player : players) {
            if (player != null && player.getPosition() == index) {
                boxPlayers.add(player);
            }
        }
        return boxPlayers;
    }

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
