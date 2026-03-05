import java.util.Scanner;

public abstract class ScannerUtilities {

    public static String getInputString(Scanner scanner, String message) {
        String output = "";

        while (output.isBlank()) {
            System.out.print(message);

            if(!scanner.hasNext()){
                System.out.println("Error: the string is empty");
                scanner.nextLine();
                continue;
            }

            output = scanner.next();
        }
        return output;

    }
}
