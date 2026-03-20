public class BoxEstate extends Box{
    private static final int TOLL_MIN = 50;
    private static final int TOLL_MAX = 150;
    private static final int tax = (int) (Math.random() * (TOLL_MAX - TOLL_MIN + 1)) +
            TOLL_MIN;;

    public BoxEstate(String str) {
        super(tax,str);
    }

    public void applyEffect(Player player) {
        player.payMoney(tax);
    }
}
