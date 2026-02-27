public class Player {
    private final String name;
    private final char sign;
    private int money = 2000;

    public Player(String name, char sign) {
        this.name = name;
        this.sign = sign;
    }

    public int getMoney() {
        return this.money;
    }

    public void addMoney(int amount) {
        money += amount; //qui andrà aggiunta un'eccezione
    }

    public void removeMoney(int amount) {
        money -= amount;
    }

}
