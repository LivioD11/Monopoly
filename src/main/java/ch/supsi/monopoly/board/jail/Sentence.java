package ch.supsi.monopoly.board.jail;

import ch.supsi.monopoly.Config;
import ch.supsi.monopoly.Player;

public class Sentence {
    private static final int BAIL_AMOUNT = Config.getInt("box.jail.bailamount",0);
    private int jailTimeLeft;
    private Player prisoner;

    public Sentence(int jailTimeLeft, Player prisoner){
        this.jailTimeLeft = jailTimeLeft;
        this.prisoner = prisoner;
    }

    public void serveTime(){
        if (jailTimeLeft > 0) {
            jailTimeLeft--;
        }
        if (jailTimeLeft == 0 && this.canBeReleased()) {
            releasePrisoner();
        }
    }

    private boolean canBeReleased(){
        if(prisoner.getBalance() >= BAIL_AMOUNT){
            // Rilascia il prigioniero

            return true;
        }else {
            // Il prigioniero ha perso
            return false;
        }
    }

    private void releasePrisoner(){

    }
}
