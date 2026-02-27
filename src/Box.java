enum type {
    PEDAGGIO,VIA
}
public class Box {
    private final type place;
    private final int tax;

    public Box(int num) {
        if(num == 0) {
            place = type.VIA;
            tax = 100;
        } else {
            place = type.PEDAGGIO;
            tax = (int) (Math.random() * 101 + 50);
        }
    }
}
