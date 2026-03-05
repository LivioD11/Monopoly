enum Type {
    TOLL,START
}

public class Box {
    private static final int bonus = 100;
    private static final int TOLL_MIN = 50;
    private static final int TOLL_MAX = 150;

    private final int value;
    private Type type;

    // TO DO
    public Box(int num) {
        if(num == 0) {
            type = type.START;
            this.value = bonus;
        } else {
            type = type.TOLL;
            this.value = (int) (Math.random() * TOLL_MAX + TOLL_MIN);
        }
    }

    private void initialize(){

    }

}
