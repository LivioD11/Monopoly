public class Player {
    private final String name;
    private final String sign;
    private int balance;

    public Player(String name, String sign) {
        this.name = name;
        this.sign = sign;
        this.balance = 0;
        Bank.distribute(this,Bank.CONTRIBUTION); // Receive initial funds from the Bank
    }

    public void changeBalance(int founds){
        this.balance += founds;
    }
}
