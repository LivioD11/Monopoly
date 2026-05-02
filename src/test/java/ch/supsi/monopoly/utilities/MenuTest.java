package ch.supsi.monopoly.utilities;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class MenuTest {
    public static void setMockInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ScannerUtilities.updateScanner();
        // NOTA: Se lo scanner statico è già stato creato,
        // questa modifica potrebbe non avere effetto a seconda di quando
        // viene inizializzata la classe.
    }

    @Test
    void showMenu(){
        Menu menu = new Menu("Descrizione");
        Option op1 = new Option("Opzione 1", () -> {
            System.out.println("Opzione 1");
        });
        Option op2 = new Option("Opzione 2", () -> {});
        Option op3 = new Option("Opzione 3", () -> {});

        menu.addOption(op1);
        menu.addOption(op2);
        menu.addOption(op3);

        setMockInput("1\n");
        menu.displayAndSelect();
    }

    @Test
    void canRemoveAnOption(){
        Menu menu = new Menu("Descrizione");
        Option op1 = new Option("Opzione 1", () -> {
            System.out.println("Opzione 1");
        });
        Option op2 = new Option("Opzione 2", () -> {});
        Option op3 = new Option("Opzione 3", () -> {});

        menu.addOption(op1);
        menu.addOption(op2);
        menu.addOption(op3);

        menu.removeOption(op2);

        setMockInput("1\n");
        menu.displayAndSelect();
    }

    @Test
    void selectWrongOption(){
        Menu menu = new Menu("Descrizione");
        Option op1 = new Option("Opzione 1", () -> {
            System.out.println("Opzione 1");
        });
        Option op2 = new Option("Opzione 2", () -> {});
        Option op3 = new Option("Opzione 3", () -> {});

        menu.addOption(op1);
        menu.addOption(op2);
        menu.addOption(op3);

        menu.removeOption(op2);

        setMockInput("20\n1\n");
        menu.displayAndSelect();
    }

    @Test
    void canQuit(){
        Menu menu = new Menu("Descrizione");
        Option op1 = new Option("Opzione 1", () -> {
            System.out.println("Opzione 1");
        });
        Option op2 = new Option("Opzione 2", () -> {});
        Option op3 = new Option("Opzione 3", () -> {});

        menu.addOption(op1);
        menu.addOption(op2);
        menu.addOption(op3);

        menu.removeOption(op2);

        setMockInput("q\n");
        menu.displayAndSelect();
    }
}
