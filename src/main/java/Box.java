enum Type {
    TOLL,START
}

public class Box {
    private static final int bonus = 100;
    private static final int TOLL_MIN = 50;
    private static final int TOLL_MAX = 150;

    private String[] rappresentation;

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

        this.rappresentation  = new String[]{
                "-".repeat(24),
                String.format("| %-21s|", "Pedaggio"),
                String.format("|%-22s|", ""),
                String.format("|%-22s|", ""),
                String.format("|%-22s|", ""),
                String.format("|%-22s|", ""),
                "-".repeat(24),
        };
    }

    public String draw(int index){
        return this.rappresentation[index];
    }

}
