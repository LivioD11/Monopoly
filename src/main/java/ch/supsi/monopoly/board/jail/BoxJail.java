package ch.supsi.monopoly.board.jail;

import ch.supsi.monopoly.Config;
import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.Box;

import java.util.ArrayList;
import java.util.List;

public class BoxJail extends Box {
    private static final int JAIL_TIME = Config.getInt("box.jail.time", 0);
    private List<Sentence> sentences;
    private static BoxJail boxJail;

    private BoxJail(){
        super("Prigione");
        this.sentences = new ArrayList<>();
    }

    public static BoxJail getInstance() {
        if (boxJail == null) {
            boxJail = new BoxJail();
        }
        return boxJail;
    }

    public void addSentence(Player player) {
        Sentence sentence = new Sentence(player);
        sentences.add(sentence);
    }

    @Override
    public void applyEffect(Player player) {
        this.processSentences(); //TODO: da rivedere
    }

    public void releasePrisoner(Player prisoner) {
        for(Sentence sentence : sentences) {
            if (sentence.getPrisoner().equals(prisoner)) {
                sentence.releasePrisoner();
            }
        }
    }

    public void processSentences() {
        sentences.forEach(Sentence::serveTime);
    }

    @Override
    public String toString() {
        return String.format("[ Casella: %s | Effetto: %s ]",
                this.name, this.description);
    }

}
