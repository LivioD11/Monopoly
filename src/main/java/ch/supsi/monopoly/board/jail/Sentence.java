package ch.supsi.monopoly.board.jail;

import ch.supsi.monopoly.Config;
import ch.supsi.monopoly.Player;

public class Sentence {
    private static final int BAIL_AMOUNT = Config.getInt("box.jail.bailamount",0);
    private static final int JAIL_TIME = Config.getInt("box.jail.jailtime",0);
    private int jailTimeLeft;
    private Player prisoner;

    public Sentence(Player prisoner){
        this.jailTimeLeft = JAIL_TIME;
        this.prisoner = prisoner;
        prisoner.setInactive();
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
        } else {
            // Il prigioniero ha perso
            return false;
        }
    }

    private void releasePrisoner(){
        prisoner.payMoney(BAIL_AMOUNT);
        prisoner.setActive();
    }
}
