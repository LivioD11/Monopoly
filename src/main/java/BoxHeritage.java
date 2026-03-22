public class BoxHeritage extends Box implements Taxable{

    public BoxHeritage(int value, String name) {
        super(value, name);

        setDescription("paga 10% patrimonio");
    }

    public void tax(Player player) {
        int tax = (int) (player.getBalance() * 0.1);
        player.payMoney(tax);
    }
}
