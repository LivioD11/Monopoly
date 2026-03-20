public class BoxHeritage extends Box{
    private static final int tax = 200;

    public BoxHeritage() {
        super(tax,"TASSA PATRIMONIALE");

        changeTextDescription("paga 10% patrimonio");
    }

    public void applyEffect(Player player) {
        int tax = (int) (player.getBalance() * 0.1);
        player.payMoney(tax);
    }
}
