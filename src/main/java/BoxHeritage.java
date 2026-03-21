public class BoxHeritage extends Box{
    private static final int TAX = 200;

    public BoxHeritage() {
        super(TAX,"TASSA PATRIMONIALE");

        //changeTextDescription("paga 10% patrimonio");
    }

    public void applyEffect(Player player) {
        int tax = (int) (player.getBalance() * 0.1);
        player.payMoney(tax);
    }
}
