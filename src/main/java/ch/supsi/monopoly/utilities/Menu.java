package ch.supsi.monopoly.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestisce una collezione di oggetti {@link Option}.
 * Permette di visualizzare un menu interattivo in console e di selezionare
 * un'opzione tramite input numerico.
 * * @author Lorenzo
 * @version 1.0
 */
public class Menu {
    private List<Option> options;

    /**
     * Inizializza un nuovo Menu con una lista di opzioni vuota.
     */
    public Menu() {
        this.options = new ArrayList<>();
    }

    /**
     * Aggiunge un'opzione alla lista del menu.
     * * @param option L'oggetto {@link Option} da aggiungere.
     */
    public void addOption(Option option) {
        options.add(option);
    }

    /**
     * Rimuove un'opzione specifica dalla lista del menu.
     * * @param option L'oggetto {@link Option} da rimuovere.
     * @return true se l'opzione è stata rimossa, false altrimenti.
     */
    public boolean removeOption(Option option) {
        return options.remove(option);
    }

    /**
     * Visualizza le opzioni disponibili e attende l'input dell'utente.
     * In caso di input non valido (non numerico o fuori range), il metodo
     * richiede l'inserimento fino a quando non viene selezionata un'opzione valida.
     * Una volta selezionata, l'opzione viene attivata e il menu si chiude.
     */
    public void displayAndSelect() {
        if (options.isEmpty()) {
            System.out.println("Il menu è vuoto.");
            return;
        }

        int choice = -1;

        while (true) {
            System.out.println("\n--- MENU ---");
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i).getMessage());
            }
            String message = "Seleziona un'opzione: ";

            choice = ScannerUtilities.getInputInt(message);

            if (choice >= 1 && choice <= options.size()) {
                // Selezione valida
                break;
            } else {
                System.out.println("Errore: Inserire un numero compreso tra 1 e " + options.size());
            }
        }

        // Trigger dell'opzione e chiusura
        options.get(choice - 1).trigger();
    }
}
