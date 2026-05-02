package ch.supsi.monopoly;

public interface Owner {
    String getName();
    int getBalance();
    void payMoney(int amount);
    void receiveMoney(int amount);
}
