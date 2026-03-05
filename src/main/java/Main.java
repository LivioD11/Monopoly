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