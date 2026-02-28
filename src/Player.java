public class Player {
    private final String name;
    private final char sign;
    private int balance;

    public Player(String name, char sign) {
        this.name = name;
        this.sign = sign;
        this.balance = 0;

        // Receive initial funds from the Bank.
        Bank.distribute(this,Bank.CONTRIBUTION);
    }

    public void print(){
        System.out.printf("Player %s (%s): balance is %d$%n",this.name,this.sign,this.balance);
    }

    public void changeBalance(int founds){
        this.balance += founds;
    }

    // GETTERS

    public boolean isBroke(){
        if(this.balance <= 0)
            return true;
        return false;
    }

    public char getSign(){
        return this.sign;
    }
}
