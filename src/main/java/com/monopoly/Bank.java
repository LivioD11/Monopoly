package com.monopoly;

import com.monopoly.cli.TransactionType;
import com.monopoly.cli.Action;
import com.monopoly.cli.Color;
import com.monopoly.cli.TextFormatter;


public abstract class Bank {
    private static final int INITIAL_BALANCE = 1000000;
    public static final int CONTRIBUTION = 2000;
    private static int balance = INITIAL_BALANCE;

    public static void payMoney(int amount){
        // Assicura che il saldo sia effettivamente ridotto.
        amount = Math.abs(amount);
        balance -= amount;
        if(amount <= 0)
            reset();
        Action.verboseTransaction("Banca",amount, TransactionType.PAY);
    }

    public static void receiveMoney(int amount){
        // Assicura che il saldo sia effettivamente incrementato.
        amount = Math.abs(amount);
        balance += amount;

        Action.verboseTransaction("Bank",amount, TransactionType.RECEIVE);
    }

    public static void reset(){
        balance = INITIAL_BALANCE;
    }

    public static int getBalance(){
        return balance;
    }

    // TODO
    public static void print(){
        System.out.println(
                String.format("Banca: il saldo è %s",
                        TextFormatter.color(TextFormatter.formatCurrency(balance), Color.YELLOW))
        );
    }
}
