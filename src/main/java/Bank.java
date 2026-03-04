import cli.Color;
import cli.TextColorizer;

public abstract class Bank {
    private static final int INITIAL_BALANCE = 1000000;
    public static final int CONTRIBUTION = 2000;
    private static int balance = INITIAL_BALANCE;


    public static void payMoney(Player player, int amount){
        // Assicura il saldo sia effettivamente ridotto.
        amount = Math.abs(amount);

        player.receiveMoney(amount);
        verboseTransaction(amount, TransactionType.PAY);
    }

    public static void receiveMoney(Player player, int amount){
        // Assicura il saldo sia effettivamente incrementato.
        amount = Math.abs(amount);

        player.payMoney(amount);
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

    public static void print(){
        System.out.println(
                String.format("Bank: balance is %s",
                        TextColorizer.color(balance + "$", Color.YELLOW))
        );
    }
}
