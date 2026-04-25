package ch.supsi.monopoly.utilities;

import java.util.Scanner;

public final class ScannerUtilities {

    public static String getInputString(Scanner scanner, String message) {
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

    public static char getInputChar(Scanner scanner, String message){
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

    public static int getInputInt(Scanner scanner, String message) {
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
}
