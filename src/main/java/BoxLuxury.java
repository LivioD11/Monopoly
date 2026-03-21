public class BoxLuxury extends Box{
    private static final int TAX = 200;

    public BoxLuxury() {
        super(TAX,"TASSA DI LUSSO");
    }

    public void applyEffect(Player player) {
        player.payMoney(TAX);
    }
}
