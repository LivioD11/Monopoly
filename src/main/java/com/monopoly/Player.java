package com.monopoly;


import com.monopoly.cli.TransactionType;
import com.monopoly.board.Board;
import com.monopoly.cli.Action;
import com.monopoly.cli.Color;
import com.monopoly.cli.TextColorizer;

public class Player {
    private final String name;
    private final char sign;
    private int balance;
    private int coordinate;
    private static final int START_POSITION = Board.INDEX_START;

    public Player(String name, char sign) {
        this.name = name;
        this.sign = sign;
        this.balance = 0;
        this.coordinate = START_POSITION;

        // Ricevere i fondi iniziali dalla Banca
        this.receiveMoney(Bank.CONTRIBUTION);
    }

    public void payMoney(int amount){
        this.balance -= amount;
        Bank.receiveMoney(amount);
        Action.verboseTransaction(String.format("Player %s (%s)",this.name,this.sign),amount, TransactionType.PAY);
    }

    public void receiveMoney(int amount){
        this.balance += amount;
        Bank.payMoney(amount);
        Action.verboseTransaction(String.format("Player %s (%s)",this.name,this.sign),amount, TransactionType.RECEIVE);
    }

    public void advance(int steps) {
        this.coordinate = (this.coordinate + steps) % Board.BOX_NUMBER;
    }

    @Override
    public String toString(){
        String player = String.format("Player %s (%s): balance is %s",
            this.name,
            this.sign,
            TextColorizer.color(this.balance + "$", Color.YELLOW));
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

    public int getCoordinate() { return coordinate; }

    public boolean isBroke(){
        return this.balance <= 0;
    }
}
