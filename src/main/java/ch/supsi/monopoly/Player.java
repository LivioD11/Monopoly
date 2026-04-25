package ch.supsi.monopoly;


import ch.supsi.monopoly.cli.TransactionType;
import ch.supsi.monopoly.board.Board;
import ch.supsi.monopoly.cli.Action;
import ch.supsi.monopoly.cli.Color;
import ch.supsi.monopoly.cli.TextFormatter;

public class Player {
    private final String name;
    private final char sign;
    private int balance;
    private int position;
    private static final int START_POSITION = Board.INDEX_START;

    public Player(String name, char sign) {
        this.name = name;
        this.sign = sign;
        this.balance = 0;
        this.position = START_POSITION;

        // Ricevere i fondi iniziali dalla Banca
        this.receiveMoney(Bank.CONTRIBUTION);
    }

    public void payMoney(int amount){
        this.balance -= amount;
        Bank.getInstance().receiveMoney(amount);
        Action.verboseTransaction(String.format("Giocatore %s (%s)",this.name,this.sign),amount, TransactionType.PAY);
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
        String player = String.format("Giocatore %s (%s): il saldo è %s",
            this.name,
            this.sign,
            TextFormatter.color(TextFormatter.formatCurrency(this.balance), Color.YELLOW));
        return player;
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
        return this.balance <= 0;
    }
}
