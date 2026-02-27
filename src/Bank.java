public class Bank {
    private static int money = 1000000;

    public void giveStartMoney(Player player) {
        player.addMoney(100);
        money -= 100;
        if(money < 100) {
            money += 1000000;
        }

//        if (money >= 100) {
//            money -= 100;
//        } else {
//            int tempMoney = 100 - money;
//            resetMoney();
//            money -= tempMoney;
//        }
    }

    private static void resetMoney() {
        Bank.money = 1000000;
    }
}
