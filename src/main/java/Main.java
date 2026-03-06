import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        Game game = new Game(scanner);
        scanner.close();
    }
}


// inizio ci chiede nome una volta
// loop
// apertura menu -> tiro dado/mostra conto
// controlliamo che abbia il conto positivo
// passiamo all'altro giocatore
