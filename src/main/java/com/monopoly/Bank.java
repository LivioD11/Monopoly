package com.monopoly;

import com.monopoly.cli.TransactionType;
import com.monopoly.cli.Action;
import com.monopoly.cli.Color;
import com.monopoly.cli.TextFormatter;


public class Bank {
    private static final int INITIAL_BALANCE = 1000000;
    public static final int CONTRIBUTION = 2000;
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

    public int getBalance(){
        return this.balance;
    }

    @Override
    public String toString(){
        return String.format("Banca: il saldo è %s",
                TextFormatter.color(TextFormatter.formatCurrency(this.balance), Color.YELLOW));
    }
}
