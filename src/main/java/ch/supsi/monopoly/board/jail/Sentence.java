package ch.supsi.monopoly.board.jail;

import ch.supsi.monopoly.Config;
import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.cli.Color;
import ch.supsi.monopoly.cli.TextFormatter;

/**
 * Gestisce la "condanna" di un giocatore all'interno della prigione.
 * <p>
 * Si occupa di monitorare i turni rimanenti (jailTimeLeft), gestire il pagamento
 * della cauzione e aggiornare lo stato di attività del giocatore nel gioco.
 * </p>
 * * @see Player
 * @see BoxJail
 */
public class Sentence {

    /** L'importo della cauzione da pagare per uscire, recuperato dalla configurazione. */
    private static final int BAIL_AMOUNT = Config.getInt("box.jail.bailamount", 0);

    /** La durata massima della permanenza in prigione definita nelle impostazioni. */
    private static final int JAIL_TIME = Config.getInt("box.jail.jailtime", 0);

    /** Turni rimanenti prima del rilascio obbligatorio. */
    private int jailTimeLeft;

    /** Il giocatore attualmente detenuto. */
    private Player prisoner;

    /**
     * Crea una nuova condanna per un giocatore.
     * Imposta il giocatore in stato inattivo e inizializza il timer della prigione.
     * * @param prisoner Il giocatore che deve scontare la condanna.
     */
    public Sentence(Player prisoner){
        this.jailTimeLeft = JAIL_TIME;
        this.prisoner = prisoner;
        prisoner.setInactive();

        System.out.println(prisoner.toString() + TextFormatter.color(" condanna di "+JAIL_TIME + " giorni", Color.YELLOW));
    }

    /**
     * Riduce il tempo rimanente della condanna.
     * Se il tempo scade e il giocatore può permettersi la cauzione, conclude la detenzione.
     */
    public void serveTime(){
        if (jailTimeLeft > 0) {
            jailTimeLeft--;
            System.out.println(prisoner.toString() + TextFormatter.color(String.format(" (%d) giorni di condanna rimanenti",jailTimeLeft),Color.PURPLE));
        }

        if(jailTimeLeft != 0) {
            return;
        }

        System.out.println(prisoner.toString() + TextFormatter.color(String.format(" condanna terminata!", jailTimeLeft), Color.BLUE));

        if (this.canBeReleased()) {
            endDetention();
            return;
        }

        System.out.println(prisoner.toString() + TextFormatter.color(" muore in prigione!",Color.RED));
        prisoner.setDefeated();
    }

    /**
     * Verifica se il prigioniero ha i requisiti economici per essere rilasciato.
     * * @return {@code true} se il saldo del giocatore è sufficiente per la cauzione, {@code false} altrimenti.
     */
    private boolean canBeReleased(){
        if(prisoner.getBalance() >= BAIL_AMOUNT){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Conclude il periodo di detenzione prelevando il denaro della cauzione
     * e riattivando il giocatore.
     */
    private void endDetention(){
        prisoner.payMoney(BAIL_AMOUNT);
        this.releasePrisoner();
    }

    /**
     * Imposta lo stato del giocatore come attivo, permettendogli di tornare in gioco.
     */
    public void releasePrisoner(){
        System.out.println(prisoner.toString() + TextFormatter.color(" viene rilasciato!",Color.GREEN));
        prisoner.setActive();
    }

    /**
     * Restituisce il riferimento al giocatore che sta scontando questa specifica condanna.
     * * @return L'oggetto {@link Player} detenuto.
     */
    public Player getPrisoner() {
        return prisoner;
    }
}