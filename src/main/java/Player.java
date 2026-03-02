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

        // Receive initial funds from the Bank.
        Bank.distribute(this,Bank.CONTRIBUTION);
    }

    public void print(){
        System.out.println(
                String.format("Player %s (%s): balance is %s",
                        this.name,
                        this.sign,
                        TextColorizer.color(this.balance + "$", Color.YELLOW))
        );
    }

    public void changeBalance(int founds){
        this.balance += founds;

        Color color = Color.GREEN;
        String action = "obtained";
        if(founds < 0){
            color = Color.RED;
            action = "lost";
        }
        System.out.println(
                String.format("Player %s (%s): %s %s",
                        this.name,
                        this.sign,
                        action,
                        TextColorizer.color(founds + "$", color))
        );
    }

    // GETTERS

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
