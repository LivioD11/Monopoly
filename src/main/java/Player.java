import cli.Color;
import cli.TextColorizer;

import javax.sound.midi.Soundbank;

public class Player {
    private final String name;
    private final char sign;
    private int balance;

    public Player(String name, char sign) {
        this.name = name;
        this.sign = sign;
        this.balance = 0;

        // Riceve i primi 200 dalla banca
        Bank.payMoney(this,Bank.CONTRIBUTION);
    }

    public void print(){
        System.out.println(
                String.format("Player %s (%s): balance is %s",
                        this.name,
                        this.sign,
                        TextColorizer.color(this.balance + "$", Color.YELLOW))
        );
    }

    public void payMoney(int amount){

    }

    public void receiveMoney(int amount){

    }

    public void changeBalance(int founds){
        this.balance += founds;

        Color color = Color.GREEN;
        String action = "obtained";
        if(founds < 0){ //il giocatore paga un tot di soldi
            color = Color.RED;
            action = "payed";
        }
        System.out.println(
                String.format("Player %s (%s): %s %s",
                        this.name,
                        this.sign,
                        action,
                        TextColorizer.color(Math.abs(founds) + "$", color))
        );
    }

    // getters

    public String getName() { return this.name; }

    public char getSign(){
        return this.sign;
    }

    public int getBalance(){
        return this.balance;
    }

    public boolean isBroke(){
        if(this.balance <= 0)
            return true;
        return false;
    }
}
