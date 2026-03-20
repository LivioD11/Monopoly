public class BoxStart extends Box{
    private static final int bonus = 100;

    public BoxStart() {
        super(bonus,"VIA");
    }

    public void applyEffect(Player player) {}

    public static int getBonus(){
        return bonus;
    }
}
