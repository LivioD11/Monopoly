public class Box {
    private static final int bonus = 100;
    private static final int TOLL_MIN = 50;
    private static final int TOLL_MAX = 150;

    private String[] rappresentation;

    private final int value;
    private BoxType type;
    public String n;

    // TODO
    public Box(BoxType type, String n) {
        this.type = type;
        this.n = n;

        if(this.type.equals(BoxType.START)) {
            this.value = bonus;
        } else {
            this.value = (int) (Math.random() * (TOLL_MAX - TOLL_MIN + 1)) + TOLL_MIN;
        }

        this.rappresentation  = new String[]{
                "-".repeat(24),
                String.format("| %-21s|", this.type.getName()),
                String.format("| %-21s|", this.value+"$"),
                String.format("|%-22s|", ""),
                String.format("|%-22s|", ""),
                String.format("|%-22s|", ""),
                "-".repeat(24),
        };
    }

    public int getValue() {
        return value;
    }

    public String draw(int index){
        return this.rappresentation[index];
    }

}
