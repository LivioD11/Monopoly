import java.util.Scanner;

public class Game {
    private boolean isPlaying;
    private Board board;
    private Player[] players;

    private static final int PLAYER_NUMBER = 2;

    public Game(Scanner scanner){
        this.isPlaying = false; // true??
        this.board = new Board(this);
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
        board.draw();
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
                int value = dice();
                System.out.println("E' uscito il numero " + value);

                if (players[currPlayer].getCoordinate() + value >= 8) {
                    players[currPlayer].receiveMoney(100); //passo dal via
                    System.out.println(players[currPlayer].getName() + " è " +
                            "passato dal via! Riceve 100.");
                }

                players[currPlayer].advance(value);
                players[currPlayer].payMoney(board.getBoxValue(players[currPlayer].getCoordinate()));
                System.out.println("Il giocatore è sulla box " + players[currPlayer].getCoordinate());

                if (players[currPlayer].isBroke()) {
                    isPlaying = false;
                }

                //TODO:aggiornare il disegno!
                currPlayer = (currPlayer + 1) % PLAYER_NUMBER;

                board.draw();
            } else {
                System.out.println("Numero non valido! O 1 o 2.");
            }

        } while (isPlaying);

        System.out.println("Gioco finito!");
    }

    public char[] getSignsAtIndex(int index) {  // disegnerò o no il segno/i
        int size = 0;
        int i = 0;
        for (Player player : players) {
            if(player.getCoordinate() == index) {
                size++;
            }
        }
        char[] signs = new char[size];

        for (Player player : players) {
            if(player.getCoordinate() == index) {
                signs[i++] = player.getSign();
            }
        }

        return signs;
    }

    public void menu() {
        System.out.println("\n" + "Cosa vuoi fare?\n" + "1. Visualizza saldo " +
                "del " +
                "giocatore corrente\n" +
                "2. Lancia il dado e muovi giocatore corrente");
    }

    public int dice() {
        int MAX = 4;
        int MIN = 1;
        return (int) (Math.random() * MAX) + MIN;
    }

    public boolean getIsPlaying(){
        return this.isPlaying;
    }


}
