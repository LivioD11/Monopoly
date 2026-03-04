enum Type {
    TOLL,START
}

public class Box {
    private final int bonus = 100;
    private final int TOLL_MAX = 101;
    private final int TOLL_MIN = 50;
    private final int tax;
    private Type type;

    public Box(int num) {
        if(num == 0) {
            type = type.START;
            tax = bonus;
        } else {
            type = type.TOLL;
            tax = (int) (Math.random() * TOLL_MAX + TOLL_MIN);
        }
    }

    private void initialize(){

    }

}
