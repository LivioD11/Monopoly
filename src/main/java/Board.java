import cli.Color;
import cli.TextColorizer;

public class Board {
    private Box[] boxes;
    private static final String[] STREET_NAME = {
            "Vicolo Corto", "Vicolo Stretto",
            "Bastioni Gran Sasso", "Viale Monterosa", "Viale Vesuvio",
            "Via Accademia", "Corso Ateneo", "Piazza Università",
            "Via Verdi", "Corso Raffaello", "Piazza Dante",
            "Via Marco Polo", "Corso Magellano", "Largo Colombo",
            "Viale Costantino", "Viale Traiano", "Piazza Giulio Cesare",
            "Via Roma", "Corso Impero", "Largo Augusto",
            "Viale dei Giardini", "Parco della Vittoria",
            "Società Acqua Potabile", "Società Elettrica"
    };
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
                case INDEX_START   -> new BoxStart();
                case INDEX_STSUD   -> new BoxStation("STAZIONE SUD");
                case INDEX_STOVEST -> new BoxStation("STAZIONE OVEST");
                case INDEX_STNORD  -> new BoxStation("STAZIONE NORD");
                case INDEX_STEST   -> new BoxStation("STAZIONE EST");
                case INDEX_LUX     -> new BoxLuxury();
                case INDEX_HER     -> new BoxHeritage();
                default -> {
                    String name = (nomeIndex < STREET_NAME.length) ?
                            STREET_NAME[nomeIndex] : "Via Generica";
                    nomeIndex++;
                    String colorfulName = TextColorizer.color(name, Color.random());
                    yield new BoxEstate(colorfulName);
                }
            };
        }

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

    // TODO (pulire il codice).
    public void draw() {
        String emptySpace = " ".repeat(24);

        // Gestisce le righe di caselle.
        for(int row=0; row < ROWS; row++){
            int boxHeight = 7;
            int startIndex = 1;

            // Alla prima e ultima riga disegna anche il bordo superiore.
            if(row==0 || row == ROWS-1)
                startIndex = 0;

            // Alla penultima riga non disegna il bordo inferiore.
            if(row==ROWS-2)
                boxHeight = 6;

            // Gestisce le componenti di una riga di caselle.
            for (int actualHeight = startIndex; actualHeight < boxHeight; actualHeight++) {

                // Gestisce il componente di una singola casella.
                for (int column = 0; column < COLUMNS; column++) {

                    // Controlla che la casella sia al bordo.
                    int id_cell = getBoxIndex(row, column);

                    if (row == 0 || row == ROWS - 1 || column == 0 || column == COLUMNS - 1) {

                        // TODO: fix
                        if(actualHeight == 5){
                            char[] signs = game.getSignsAtIndex(id_cell);

                            if(signs.length > 0){
                                String s = "";
                                for(char c : signs){
                                    s += c + " ";
                                }

                                System.out.print(String.format("|%-22s|", s));
                            } else {
                                System.out.print(this.boxes[id_cell].draw(actualHeight));
                            }

                        } else {
                            System.out.print(this.boxes[id_cell].draw(actualHeight));
                        }

                    } else {
                        System.out.print(emptySpace);
                    }

                }
                System.out.println();
            }
        }
    }

    public Box getBox(int value) {
        return boxes[value];
    }

}
