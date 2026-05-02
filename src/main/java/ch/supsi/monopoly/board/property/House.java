package ch.supsi.monopoly.board.property;

import ch.supsi.monopoly.Config;

/**
 * Rappresenta una Casa, l'unità base di costruzione per le proprietà nel Monopoly.
 * <p>
 * Le case possono essere edificate su una proprietà per aumentarne la rendita.
 * Seguendo le regole del gioco, è necessario costruire un numero prestabilito di case
 * prima di poter edificare un {@link Hotel}.
 * </p>
 * * @see Building
 * @see Hotel
 * @see BoxProperty
 */
public class House extends Building {

    /** Il prezzo minimo per l'acquisto di una casa, letto dalla configurazione. */
    protected static final int MIN_PRICE = Config.getInt("building.house.price.min", 0);

    /** Il prezzo massimo per l'acquisto di una casa, letto dalla configurazione. */
    protected static final int MAX_PRICE = Config.getInt("building.house.price.max", 0);

    /** Il valore di rendita o l'incremento patrimoniale fornito dalla singola casa. */
    protected static final int VALUE = Config.getInt("building.house.value", 0);

    /**
     * Inizializza una nuova Casa calcolando un prezzo casuale tra i limiti definiti.
     * Il prezzo calcolato viene passato al costruttore della superclasse {@link Building}.
     */
    public House() {
        super(generatePrice());
    }

    /**
     * Genera un prezzo d'acquisto casuale basato sui parametri {@code MIN_PRICE} e {@code MAX_PRICE}.
     * * @return Un valore intero che rappresenta il costo di costruzione della casa.
     */
    private static int generatePrice() {
        return (int) (Math.random() * (MAX_PRICE - MIN_PRICE + 1)) + MIN_PRICE;
    }
}