public class Board {
    Box[] boxes = new Box[16];

    public Board() {
        boxes[0] = new Box(0);
        for(int i = 1; i < 16; i++) {
            boxes[i] = new Box(1);
        }
    }
}
