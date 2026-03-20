public class BoxEstate extends Box{
    private static final int TOLL_MIN = 50;
    private static final int TOLL_MAX = 150;

    public BoxEstate(String str) {
        super(generateRandomNum() ,str);
    }

    public void applyEffect(Player player) {
        player.payMoney(this.getValue());
    }

    private static int generateRandomNum() {
        return (int) (Math.random() * (TOLL_MAX - TOLL_MIN + 1)) + TOLL_MIN;
    }
}
