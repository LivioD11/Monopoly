package com.monopoly.cli;

public abstract class Action {

    public static void verboseTransaction(String name, int amount, TransactionType transactionType){
        String transactionAction = "received";
        Color color = Color.GREEN;

        if(transactionType.equals(TransactionType.PAY)){
            transactionAction = "paid";
            color = Color.RED;
        }

        System.out.println(
                String.format("%s: %s %s",
                        name,
                        transactionAction,
                        TextColorizer.color(Math.abs(amount) + "$", color))
        );
    }
}
