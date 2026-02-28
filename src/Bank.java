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
        if(balance<=0)
            balance = Config.getInt("bank.balance", 0);
    }
}
