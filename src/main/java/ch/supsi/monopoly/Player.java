package ch.supsi.monopoly;


import ch.supsi.monopoly.cli.TransactionType;
import ch.supsi.monopoly.board.Board;
import ch.supsi.monopoly.cli.Action;
import ch.supsi.monopoly.cli.Color;
import ch.supsi.monopoly.cli.TextFormatter;

public class Player implements Owner{
    private final String name;
    private final char sign;
    private int balance;
    private int position;
    private PlayerStatus status;
    private static final int START_POSITION = Board.INDEX_START;

    public Player(String name, char sign) {
        this.name = name;
        this.sign = sign;
        this.balance = 0;
        this.status = PlayerStatus.ACTIVE;
        this.position = START_POSITION;

        // Ricevere i fondi iniziali dalla Banca
        this.receiveMoney(Bank.CONTRIBUTION);
    }

    public void payMoney(int amount){
        this.balance -= amount;
        Bank.getInstance().receiveMoney(amount);
        Action.verboseTransaction(String.format("Giocatore %s (%s)",this.name,this.sign),amount, TransactionType.PAY);
        if(this.balance<=0)
            this.status = PlayerStatus.BROKE;
    }

    public void receiveMoney(int amount){
        this.balance += amount;
        Bank.getInstance().payMoney(amount);
        Action.verboseTransaction(String.format("Giocatore %s (%s)",this.name,this.sign),amount, TransactionType.RECEIVE);
    }

    public void move(int steps) {
        this.position = (this.position + steps) % Board.BOX_NUMBER;
    }

    @Override
    public String toString(){
        String player = String.format("Giocatore %s (%s):",
            this.name,
            this.sign);
        return player;
    }

    @Override
    public boolean equals(Object o) {
        // 1. Verifica se è lo stesso riferimento in memoria
        if (this == o) return true;

        // 2. Verifica se l'oggetto è nullo o appartiene a una classe diversa
        if (o == null || getClass() != o.getClass()) return false;

        Player player = (Player) o;

        if (sign != player.sign) return false;
        return name != null ? name.equals(player.name) : player.name == null;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (int) sign;
        return result;
    }

    // Setters

    public void setActive(){
        if(this.status.equals(PlayerStatus.INACTIVE))
            this.status = PlayerStatus.ACTIVE;
    }

    public void setInactive(){
        if(this.status.equals(PlayerStatus.ACTIVE))
            this.status = PlayerStatus.INACTIVE;
    }

    // Getters

    public String getName() { return this.name; }

    public char getSign(){
        return this.sign;
    }

    public int getBalance(){
        return this.balance;
    }

    public int getPosition() { return position; }

    public boolean isBroke(){
        if(this.status.equals(PlayerStatus.BROKE))
            return true;
        return false;
    }
}
