package ch.supsi.monopoly.board.jail;

import ch.supsi.monopoly.Config;
import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.Box;

import java.util.ArrayList;
import java.util.List;

public class BoxJail extends Box {
    private static final int JAIL_TIME = Config.getInt("box.jail.time", 0);
    private List<Sentence> sentences;

    public BoxJail(){
        super("Prigione");
        this.sentences = new ArrayList<>();
    }

    @Override
    public void applyEffect(Player player) {
        this.processSentences();
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
