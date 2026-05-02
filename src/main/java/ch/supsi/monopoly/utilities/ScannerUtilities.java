package ch.supsi.monopoly.utilities;

import java.util.Scanner;

public final class ScannerUtilities {

    // Unica istanza statica per tutta l'applicazione
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Metodo utile hai per la creazione di test
     */
    static void updateScanner() {
        scanner = new Scanner(System.in);
    }

    public static String getInputString(String message) {
        String output = "";
        while (output.isBlank()) {
            System.out.print(message);

            if(!scanner.hasNext()){
                System.out.println("Errore: la stringa è vuota");
                scanner.nextLine();
                continue;
            }

            output = scanner.next();
        }
        return output;
    }

    public static char getInputChar(String message){
        char output = ' ';
        while (Character.isWhitespace(output)) {
            System.out.print(message);

            if(!scanner.hasNext()){
                System.out.println("Errore: non è stato inserito alcun carattere");
                scanner.nextLine();
                continue;
            }

            char input = scanner.next().charAt(0);

            if(!Character.isAlphabetic(input)){
                System.out.println("Errore: il carattere non è alfabetico");
                scanner.nextLine();
                continue;
            }

            output = input;
        }
        return output;
    }

    public static int getInputInt(String message) {
        int output = 0;
        boolean valid = false;

        while (!valid) {
            System.out.print(message);

            if (!scanner.hasNextInt()) {
                System.out.println("Errore: il valore inserito non è un intero valido");
                scanner.nextLine();
                continue;
            }

            output = scanner.nextInt();
            valid = true;
        }

        return output;
    }

    public static boolean getInputBoolean(String message){
        message = message + " (y/N): ";
        String input = scanner.next().trim().toLowerCase();
        scanner.nextLine();
        return input.equals("y");
    }
}
