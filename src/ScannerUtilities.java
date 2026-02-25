public class ScannerUtilities {
    static String leggiStringa(String messaggio) {
        System.out.print(messaggio);
        while (true) {
            if (inputUtente.hasNextLine()) {
                String input = inputUtente.nextLine().trim();
                if (!input.isEmpty()) {
                    return input;
                }
            }
            System.out.print("Input non valido, riprova: " + RuotaDellaFortunaTest.sep);
        }
    }

    for (int i = 0; i < numGiocatori; i++) {
        String nome = "";
        boolean nomeValido = false;
        while (!nomeValido) {
            nome = ScannerUtilities.leggiStringa("Nome giocatore " + (i + 1) + ": ");
            System.out.println();
            nomeValido = true;
            for (int j = 0; j < i; j++) {
                if (giocatori[j].nome.equalsIgnoreCase(nome)) {
                    System.out.println("Nome già inserito. Scegline un altro." + RuotaDellaFortunaTest.sep);
                    nomeValido = false;
                    break;
                }
            }
        }
        giocatori[i] = new Giocatore(nome);
    }
}
