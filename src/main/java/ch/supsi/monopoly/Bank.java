package ch.supsi.monopoly;

import ch.supsi.monopoly.cli.TransactionType;
import ch.supsi.monopoly.cli.Action;
import ch.supsi.monopoly.cli.Color;
import ch.supsi.monopoly.cli.TextFormatter;


public class Bank implements Owner {
    private static final int INITIAL_BALANCE = Config.getInt("bank.balance", 0);;
    public static final int CONTRIBUTION = Config.getInt("bank.contribution", 0);;
    private static Bank bank;
    private int balance;

    private Bank(){
        this.balance = INITIAL_BALANCE;
    }

    public static Bank getInstance(){
        if(bank == null){
            bank = new Bank();
        }
        return  bank;
    }

    public void payMoney(int amount){
        // Assicura che il saldo sia effettivamente ridotto.
        amount = Math.abs(amount);
        this.balance -= amount;
        if(amount <= 0)
            reset();
        Action.verboseTransaction("Banca",amount, TransactionType.PAY);
    }

    public void receiveMoney(int amount){
        // Assicura che il saldo sia effettivamente incrementato.
        amount = Math.abs(amount);
        this.balance += amount;

        Action.verboseTransaction("Bank",amount, TransactionType.RECEIVE);
    }

    public void reset(){
        this.balance = INITIAL_BALANCE;
    }

    public String getName(){
        return "Banca";
    }

    public int getBalance(){
        return this.balance;
    }

    @Override
    public String toString(){
        return String.format("Banca: il saldo è %s",
                TextFormatter.color(TextFormatter.formatCurrency(this.balance), Color.YELLOW));
    }

    @Override
    public boolean equals(Object object) {
        return this == object;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(balance);
    }
}
