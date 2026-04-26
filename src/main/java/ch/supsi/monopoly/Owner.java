package ch.supsi.monopoly;

public interface Owner {
    int getBalance();
    void payMoney(int amount);
    void receiveMoney(int amount);
}
