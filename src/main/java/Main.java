import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board board = new Board();
        board.draw();

        Scanner scanner = new Scanner(System.in);
        Game game = new Game(scanner);
        scanner.close();
    }
}

// TODO:modificare la partenza
// TODO:rendere il nome/simbolo univoco
// TODO:rendere il gioco un ciclo
// TODO:spostare i simboli sul cartellone

// inizio ci chiede nome una volta
// loop
// apertura menu -> tiro dado/mostra conto
// controlliamo che abbia il conto positivo
// passiamo all'altro giocatore
