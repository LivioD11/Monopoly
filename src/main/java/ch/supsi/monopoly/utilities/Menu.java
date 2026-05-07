package ch.supsi.monopoly.utilities;

import ch.supsi.monopoly.cli.Color;
import ch.supsi.monopoly.cli.TextFormatter;

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
    private String description;
    private List<Option> options;
    private boolean removeQuit;
    private boolean needToQuit;
    private Runnable action;

    /**
     * Inizializza un nuovo Menu con una lista di opzioni vuota.
     */
    public Menu(String description) {
        this.description = description;
        this.removeQuit = false;
        this.options = new ArrayList<>();
    }

    public Menu(String description, boolean removeQuit){
        this(description);
        this.removeQuit = removeQuit;
    }

    public Menu(String description, boolean removeQuit, boolean needToQuit){
        this(description,removeQuit);
        this.needToQuit = needToQuit;
    }

    /**
     * Aggiunge un'opzione alla lista del menu.
     * * @param option L'oggetto {@link Option} da aggiungere.
     */
    public void addOption(Option option) {
        if (!options.contains(option)) {
            options.add(option);
        }
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

        while (true) {
            System.out.println("\n------");
            System.out.println(description);
            System.out.println();

            for (int i = 0; i < options.size(); i++) {
                System.out.println("(" + (i + 1) + ") - " + options.get(i).getMessage());
            }
            if(!removeQuit)
                System.out.println("(q) - Quit");

            // Usiamo getString perché l'input può essere sia un numero che 'q'
            String input = ScannerUtilities.getInputString("\nSeleziona un'opzione: ").toLowerCase();

            if (input.equals("q") && !removeQuit) {
                System.out.println("Uscita dal menu...");
                return; // Esce direttamente dal metodo
            }

            try {
                int choice = Integer.parseInt(input);
                if (choice >= 1 && choice <= options.size()) {
                    options.get(choice - 1).trigger();
                    if(!needToQuit)
                        // Chiudo il menu
                        break;
                    this.reset();
                } else {
                    System.out.println(TextFormatter.color("Errore: Numero fuori range.", Color.RED));
                }
            } catch (NumberFormatException e) {
                System.out.println(TextFormatter.color("Errore: Inserisci un numero o 'q'.", Color.RED));
            }
        }
    }
    public void setReset(Runnable action){
        this.action=action;
    }

    private void reset(){
        options.clear();
        action.run();
    }

    public void setDescription(String description){
        this.description = description;
    }

    // Getters

    public String getDescription() {
        return description;
    }

    public List<Option> getOptions() {
        return options;
    }

    public boolean getRemoveQuit() {
        return removeQuit;
    }
}
