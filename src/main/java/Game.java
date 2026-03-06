import java.util.Scanner;

public class Game {
    private boolean isPlaying;
    private Board board;
    private Player[] players;

    private static final int PLAYER_NUMBER = 2;

    public Game(Scanner scanner){
        this.isPlaying = false; // true??
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
        gameItself(scanner);
    }

    private void gameItself(Scanner scanner){
        boolean isPlaying = true;
        int currPlayer = 0;

        do {
            menu();
            String question = "Cosa scegli? ";
            int choice = ScannerUtilities.getInputInt(scanner, question);
            if (choice == 1) {
                System.out.println("Il saldo di " + players[currPlayer].getName() + " è " + players[currPlayer].getBalance());
            } else if (choice == 2) {
                System.out.println("ok");

            } else {
                System.out.println("Numero non valido! O 1 o 2.");
            }

        } while (isPlaying);


    }

    public void menu() {
        System.out.println("\n" + "Cosa vuoi fare?\n" + "1. Visualizza saldo " +
                "del " +
                "giocatore corrente\n" +
                "2. Lancia il dado e muovi giocatore corrente");
    }

    public int dado() {
        int MAX = 4;
        int MIN = 1;
        return (int) (Math.random() * MAX) + MIN;
    }

    public boolean getIsPlaying(){
        return this.isPlaying;
    }


}
