import cli.Color;
import cli.TextColorizer;

import javax.sound.midi.Soundbank;

public class Player {
    private final String name;
    private final char sign;
    private int balance;

    public Player(String name, char sign) {
        this.name = name;
        this.sign = sign;
        this.balance = 0;

        // Ricevere i fondi iniziali dalla Banca.
        this.receiveMoney(Bank.CONTRIBUTION);
    }

    public void payMoney(int amount){
        this.balance -= amount;
        Bank.receiveMoney(amount);
        verboseTransaction(amount, TransactionType.PAY);
    }

    public void receiveMoney(int amount){
        this.balance += amount;
        Bank.payMoney(amount);
        verboseTransaction(amount, TransactionType.RECEIVE);
    }

    private void verboseTransaction(int amount, TransactionType transactionType){
        String transactionAction = "received";
        Color color = Color.GREEN;

        if(transactionType.equals(TransactionType.PAY)){
            transactionAction = "paid";
            color = Color.RED;
        }

        System.out.println(
                String.format("Player %s (%s): %s %s",
                        this.name,
                        this.sign,
                        transactionAction,
                        TextColorizer.color(Math.abs(amount) + "$", color))
        );
    }

    public void print(){
        System.out.println(
                String.format("Player %s (%s): balance is %s",
                        this.name,
                        this.sign,
                        TextColorizer.color(this.balance + "$", Color.YELLOW))
        );
    }

    // Getters

    public String getName() { return this.name; }

    public char getSign(){
        return this.sign;
    }

    public int getBalance(){
        return this.balance;
    }

    public boolean isBroke(){
        if(this.balance <= 0)
            return true;
        return false;
    }
}
