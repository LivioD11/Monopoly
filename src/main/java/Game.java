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

    public void start(Scanner scanner) {
        for (int i = 0; i < PLAYER_NUMBER; i++) {
            boolean valid;
            String name;
            do {
                valid = true;
                String message = "Inserisci il nome del giocatore (" + (i + 1) + "): ";
                name = ScannerUtilities.getInputString(scanner, message);
                for (int j = 0; j < i; j++) {
                    if (name.equals(players[j].getName())) {
                        valid = false;
                        break;
                    }
                }
                if (!valid) {
                    System.out.println("Nome già utilizzato, metti nuovo !");
                }
            } while (!valid);

            char sign;
            do {
                valid = true;
                String message = "Inserisci il simbolo: ";
                sign = ScannerUtilities.getInputChar(scanner, message);
                for (int j = 0; j < i; j++) {
                    if (sign == players[j].getSign()) {
                        valid = false;
                        break;
                    }
                }
                if (!valid) {
                    System.out.println("segno già utilizzato, metti nuovo !");
                }
            } while (!valid);
            players[i] = new Player(name, sign);
        }
    }

    private void loop(){
        System.out.println("");
    }

    public boolean getIsPlaying(){
        return this.isPlaying;
    }
}
