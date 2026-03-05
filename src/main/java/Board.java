public class Board {
    private Box[] boxes;
    private static final int COLUMNS = 5;
    private static final int ROWS = 5;

    public Board() {
        this.boxes = new Box[20];
        for(int i = 0; i < 20; i++) {
            boxes[i] = new Box(i);
        }
    }

    // TO DO (pulire il codice)
    public void draw() {
        String emptySpace = "                        ";

        // Gestisce le righe di celle.
        for(int rows=0; rows < ROWS; rows++){
            int boxHeight = 7;
            int startIndex = 1;

            // Alla prima e ultima riga disegna anche il bordo superiore.
            if(rows==0 || rows == ROWS-1)
                startIndex = 0;

            // Alla penultima riga non disegna il bordo inferiore.
            if(rows==ROWS-2)
                boxHeight = 6;

            // Gestisce le componenti di una riga di celle
            for (int actualHeight = startIndex; actualHeight < boxHeight; actualHeight++) {

                // Gestisce il componente di una singola cella
                for (int columns = 0; columns < COLUMNS; columns++) {

                    // Controlla che la cella sia al bordo.
                    if (rows == 0 || rows == ROWS - 1 || columns == 0 || columns == COLUMNS - 1) {
                        // Disegna la componente della cella.
                        System.out.print(this.boxes[columns].draw(actualHeight));
                    } else {
                        // Lascia uno spazio vuoto (posizione centrale).
                        System.out.print(emptySpace);
                    }

                }
                System.out.println();
            }
        }
    }
}
