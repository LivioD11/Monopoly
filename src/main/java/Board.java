public class Board {
    private Box[] boxes;
    private static final int COLUMNS = 5;
    private static final int ROWS = 5;
    private Game game;

    public Board(Game game) {
        this.boxes = new Box[26];
        this.game = game;
        for(int i = 0; i < 26; i++) {

            // Crea la casella di partenza.
            if(i == 8){
                boxes[i] = new Box(BoxType.START, String.valueOf(i));
                continue;
            }
            boxes[i] = new Box(BoxType.TOLL, String.valueOf(i));
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
                    //


                    // Controlla che la casella sia al bordo.
                    int id_cell = getBoxIndex(row, column);

                    if (row == 0 || row == ROWS - 1 || column == 0 || column == COLUMNS - 1) {

                        if(actualHeight == 5){
                            char[] signs = game.getSignsAtIndex(id_cell);

                            if(signs.length > 0){
                                String s = "";
                                for(char c : signs){
                                    s += c + " ";
                                }

                                System.out.print(String.format("| %-21s|", s));
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

    public int getBoxValue(int value) {
        if (value == 8) {
            return 0;
        }
        return boxes[value].getValue();
    }

}
