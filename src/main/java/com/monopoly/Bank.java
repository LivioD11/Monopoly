package com.monopoly;

import com.monopoly.TransactionType;
import com.monopoly.cli.Color;
import com.monopoly.cli.TextColorizer;


public abstract class Bank {
    private static final int INITIAL_BALANCE = 1000000;
    public static final int CONTRIBUTION = 2000;
    private static int balance = INITIAL_BALANCE;

    public static void payMoney(int amount){
        // Assicura che il saldo sia effettivamente ridotto.
        amount = Math.abs(amount);
        verboseTransaction(amount, TransactionType.PAY);
    }

    public static void receiveMoney(int amount){
        // Assicura che il saldo sia effettivamente incrementato.
        amount = Math.abs(amount);
        balance += amount;

        verboseTransaction(amount, TransactionType.RECEIVE);
    }

    public static void reset(){
        balance = INITIAL_BALANCE;
    }

    private static void verboseTransaction(int amount, TransactionType transactionType){
        String transactionAction = "received";
        Color color = Color.GREEN;

        if(transactionType.equals(TransactionType.PAY)){
            transactionAction = "paid";
            color = Color.RED;
        }

        System.out.println(
                String.format("Bank: %s %s",
                        transactionAction,
                        TextColorizer.color(amount + "$", color))
        );
    }

    // TODO
    public static void print(){
        System.out.println(
                String.format("Bank: balance is %s",
                        TextColorizer.color(balance + "$", Color.YELLOW))
        );
    }
}
