public class BoxStart extends Box{
    private static final int BONUS = 100;

    public BoxStart() {
        super(BONUS,"VIA");
    }

    public void applyEffect(Player player) {}

    public static int getBonus(){
        return BONUS;
    }
}
