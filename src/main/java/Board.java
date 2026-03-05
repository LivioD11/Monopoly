public class Board {
    private Box[] boxes;
    private static final int COLUMNS = 5;
    private static final int ROWS = 5;

    public Board() {
        this.boxes = new Box[16];
        for(int i = 0; i < 16; i++) {
            boxes[i] = new Box(i);
        }
    }

    // TO DO
    public void draw() {

    }
}
