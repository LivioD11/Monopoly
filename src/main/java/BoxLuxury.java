public class BoxLuxury extends Box implements Taxable{

    public BoxLuxury(int value, String name) {
        super(value,name);
    }

    public void tax(Player player) {
        player.payMoney(this.value);
    }
}
