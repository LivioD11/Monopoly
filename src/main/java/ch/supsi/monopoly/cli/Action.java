package com.monopoly.cli;

public abstract class Action {

    /**
     * Stampa a schermo le informazioni in merito alla transazione compiuta.
     * @param name
     * @param amount
     * @param transactionType
     */
    public static void verboseTransaction(String name, int amount, TransactionType transactionType){
        String transactionAction = "ha ricevuto";
        Color color = Color.GREEN;

        if(transactionType.equals(TransactionType.PAY)){
            transactionAction = "ha pagato";
            color = Color.RED;
        }

        amount = Math.abs(amount);

        System.out.println(
                String.format("%s: %s %s",
                        name,
                        transactionAction,
                        TextFormatter.color(TextFormatter.formatCurrency(amount), color))
        );
    }
}
