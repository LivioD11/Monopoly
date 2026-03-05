import java.util.Scanner;

public class Game {
    private boolean isPlaying;
    private Board board;
    private Player[] players;

    private static final int PLAYER_NUMBER = 2;

    public Game(Scanner scanner){
        this.isPlaying = false;
        this.board = new Board();
        this.players = new Player[PLAYER_NUMBER];
        this.start(scanner);
    }

    public void start(Scanner scanner){
        for (int i = 0; i < PLAYER_NUMBER; i++) {
            String message = "Inserisci il nome del giocatore ("+(i+1)+"): ";
            String name = ScannerUtilities.getInputString(scanner, message);

            message = "Inserisci il simbolo: ";
            char sign = ScannerUtilities.getInputChar(scanner,message);

            players[i] = new Player(name,sign);
        }

        this.loop();
    }

    private void loop(){
        while(getIsPlaying()){

        }
    }

    public boolean getIsPlaying(){
        return this.isPlaying;
    }
}
