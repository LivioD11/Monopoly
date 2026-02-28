import cli.Color;
import cli.TextColorizer;

public abstract class Bank {
    private static int balance = Config.getInt("bank.balance", 0);
    public static final int CONTRIBUTION =Config.getInt("bank.contribution", 0);

    public static void distribute(Player player, int founds){
        player.changeBalance(founds);
        withdrawFounds(founds);
    }

    public static  void debit(Player player, int toll){

    }

    private static void withdrawFounds(int founds){
        balance -= founds;
        System.out.println(
                String.format("Bank: lost %s",
                        TextColorizer.color(founds + "$", Color.RED))
        );
    }

    public static void print(){
        System.out.println(
                String.format("Bank: balance is %s",
                        TextColorizer.color(balance + "$", Color.YELLOW))
        );
    }
}
