package ch.supsi.monopoly.board.property;

/**
 * Rappresenta l'astrazione di un edificio (Casa o Hotel) che può essere
 * costruito su una proprietà per aumentarne il valore e la rendita.
 * <p>
 * Questa classe definisce la struttura comune per tutti i tipi di edifici,
 * gestendo principalmente il costo associato alla loro costruzione.
 * </p>
 * * @see BoxProperty
 * @see House
 * @see Hotel
 */
public abstract class Building {

    /** Il costo di costruzione dell'edificio. */
    protected int price;

    /**
     * Inizializza un nuovo edificio con il prezzo specificato.
     * * @param price Il costo necessario per acquistare e costruire questo edificio.
     */
    public Building(int price){
        this.price = price;
    }

    /**
     * Restituisce il prezzo dell'edificio.
     * * @return Il valore intero rappresentante il costo di costruzione.
     */
    public int getPrice(){
        return this.price;
    }
}