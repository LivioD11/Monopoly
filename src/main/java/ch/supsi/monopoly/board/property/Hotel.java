package ch.supsi.monopoly.board.property;

import ch.supsi.monopoly.Config;

/**
 * Rappresenta un Hotel, il livello massimo di costruzione realizzabile su una proprietà.
 * <p>
 * Un hotel aumenta drasticamente il valore della rendita della casella su cui è costruito.
 * Il costo d'acquisto viene determinato casualmente all'interno di un range definito
 * nei file di configurazione del sistema.
 * </p>
 * * @see Building
 * @see BoxProperty
 */
public class Hotel extends Building {

    /** Il prezzo minimo possibile per la costruzione di un hotel. */
    protected static final int MIN_PRICE = Config.getInt("building.hotel.price.min", 0);

    /** Il prezzo massimo possibile per la costruzione di un hotel. */
    protected static final int MAX_PRICE = Config.getInt("building.hotel.price.max", 0);

    /** Il valore intrinseco o l'incremento di rendita apportato dall'hotel. */
    protected static final int VALUE = Config.getInt("building.hotel.value", 0);

    /**
     * Crea un nuovo Hotel calcolando dinamicamente il suo prezzo di acquisto.
     * Il prezzo viene passato al costruttore della superclasse {@link Building}.
     */
    public Hotel() {
        super(generatePrice());
    }

    /**
     * Genera un prezzo casuale compreso tra {@code MIN_PRICE} e {@code MAX_PRICE}.
     * * @return Un valore intero rappresentante il prezzo generato.
     */
    private static int generatePrice() {
        return (int) (Math.random() * (MAX_PRICE - MIN_PRICE + 1)) + MIN_PRICE;
    }
}