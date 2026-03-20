public class BoxLuxury extends Box{
    private static final int tax = 200;

    public BoxLuxury() {
        super(tax,"TASSA DI LUSSO");
    }

    public void applyEffect(Player player) {
        player.payMoney(tax);
    }
}
