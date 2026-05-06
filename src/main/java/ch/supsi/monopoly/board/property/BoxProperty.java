package ch.supsi.monopoly.board.property;

import ch.supsi.monopoly.Bank;
import ch.supsi.monopoly.Config;
import ch.supsi.monopoly.Owner;
import ch.supsi.monopoly.Player;
import ch.supsi.monopoly.board.Box;
import ch.supsi.monopoly.board.interfaces.Buildable;
import ch.supsi.monopoly.board.interfaces.Purchasable;
import ch.supsi.monopoly.board.interfaces.Taxable;
import ch.supsi.monopoly.cli.Action;
import ch.supsi.monopoly.cli.Color;
import ch.supsi.monopoly.cli.TextFormatter;
import ch.supsi.monopoly.utilities.Menu;
import ch.supsi.monopoly.utilities.Option;

import java.util.ArrayList;
import java.util.List;

/**
 * Rappresenta una casella di proprietà acquistabile nel tabellone del Monopoly.
 * Gestisce l'acquisto, la tassazione e la costruzione di edifici (case/hotel).
 */
public class BoxProperty extends Box implements Taxable, Purchasable, Buildable {
    private static final int HOUSES_LIMIT = Config.getInt("box.property.houses.limit",0);
    private static final int HOTELS_LIMIT = Config.getInt("box.property.hotels.limit",0);
    private static final int MIN_PRICE = Config.getInt("box.property.price.min",0);
    private static final int MAX_PRICE = Config.getInt("box.property.price.max",0);
    private Menu menu;
    private int price;
    private List<Building> buildings;
    private DevelopmentLevel level;

    /**
     * Costruttore della proprietà. Genera un prezzo casuale e inizializza gli edifici.
     * @param name Nome della casella.
     */
    public BoxProperty(String name) {
        super(name);
        this.buildings = new ArrayList<>();
        this.price = generatePrice();
        this.level = DevelopmentLevel.EMPTY;
        PropertyManager.getIstance().addProperty(this);
    }

    protected BoxProperty(String name, Color color){
        this(name);
        this.color = color;
    }

    /**
     * Configura le opzioni del menu (Acquisto/Costruzione) in base allo stato della proprietà.
     * @param player Il giocatore corrente.
     */
    private void setupMenu(Player player) {
        Option option1 = new Option(
                "Acquistare la proprietà",
                () -> {
                    String message = "Acquistare la proprietà";
                    if(Action.confirmAction(message))
                        this.buy(player);
                });
        Option option2 = new Option(
                "Costruire nella priprietà",
                () -> {
                    String message = "Costruire nella priprietà";
                    if(Action.confirmAction(message))
                        this.build();
                });

        if(getIsPurchasable())
            menu.addOption(option1);
        if(player.equals(owner))
            menu.addOption(option2);
    }

    @Override
    protected void updateRepresentation(){
        this.representation  = new String[]{
                "-".repeat(24),
                "|"+ TextFormatter.padAnsi(this.name,22)+"|",
                this.value > 0 ? String.format("|%-22s|", this.description) : String.format("|%-22s|", ""),
                String.format("|%-22s|", drawOwner()),
                String.format("|%-22s|", ""),
                "|"+ TextFormatter.padAnsiAndEmoji(this.drawBuildings(),22)+"|",
                "-".repeat(24),
        };
    }

    private String drawOwner(){
        String output = "Price: "+TextFormatter.formatCurrency(this.price);
        if(!getIsPurchasable())
            output = owner.getName();
        return output;
    }

    private String drawBuildings(){
        String output = "";
        if(level == null)
            return output;

        if(level.equals(DevelopmentLevel.HOUSES)){
            for(Building building : this.buildings)
                output += "\uD83C\uDFE0";
            output += " ("+this.buildings.size()+" case)";
        }

        if(level.equals(DevelopmentLevel.HOTEL))
            output += "\uD83C\uDFE8 (hotel)";
        return output;
    }

    private static int generatePrice() {
        return (int) (Math.random() * (MAX_PRICE - MIN_PRICE + 1)) + MIN_PRICE;
    }

    /**
     * Gestisce l'acquisto della proprietà.
     * @param buyer Il potenziale acquirente.
     * @return true se l'acquisto va a buon fine.
     */
    public boolean buy(Owner buyer){
        if(!(owner instanceof Bank))
            return false;

        if(price>buyer.getBalance())
            return false;

        buyer.payMoney(price);
        this.owner = buyer;

        System.out.println(
                String.format(
                        "%s "+TextFormatter.color("ha acquistato la proprietà",Color.YELLOW),
                        buyer.toString()
                )
        );
        return true;
    }

    /**
     * Aggiunge una casa o un hotel alla proprietà rispettando i limiti.
     */
    public void build() {
        if(!getIsBuildable())
            return;

        Building building;

        // Logica per le case
        if (level == DevelopmentLevel.EMPTY || level == DevelopmentLevel.HOUSES) {
            building = new House();
            if(owner.getBalance()<building.getPrice())
                return;

            int currentHouses = 0;
            for(Building b : buildings) if(b instanceof House) currentHouses++;

            if (currentHouses < HOUSES_LIMIT) {

                buildings.add(building);
                level = DevelopmentLevel.HOUSES;
                this.updateRepresentation();
                System.out.println(owner.toString() + TextFormatter.color(" ha costruito una casa",Color.YELLOW));
                return;
            }
        }

        // Logica per l'hotel
        int currentHouses = 0;
        building = new Hotel();
        if(owner.getBalance()<building.getPrice())
            return;
        for(Building b : buildings) if(b instanceof House) currentHouses++;
        int currentHotels = 0;
        for(Building b : buildings) if(b instanceof Hotel) currentHotels++;

        if (currentHouses == HOUSES_LIMIT && currentHotels < HOTELS_LIMIT) {
            buildings.clear();
            buildings.add(building);
            level = DevelopmentLevel.HOTEL;
            this.updateRepresentation();
            System.out.println(owner.toString() + TextFormatter.color(" ha costruito un albergo",Color.YELLOW));
            return;
        }
    }
    public void bankGetbackProperty(){
        this.owner = Bank.getInstance();
    }

    public void tax(Player player){
        player.payMoney(this.getValue());
    }

    public void applyEffect(Player player) {
        this.tax(player);
    }

    @Override
    public void interact(Player player) {
        this.menu = new Menu(this.toString());
        this.setupMenu(player);
        menu.displayAndSelect();
    }

    @Override
    public String toString() {
        return String.format("[ Cella proprietà: %s | Tassa: %d CHF ]",
                this.name, this.value);
    }

    // Getters

    public Owner getOwner(){
        return  this.owner;
    }

    public boolean getIsPurchasable(){
        return !(owner instanceof Bank);
    }

    public Menu getMenu() {
        return menu;
    }

    public int getPrice() {
        return price;
    }

    public List<Building> getBuildings() {
        return buildings;
    }

    public DevelopmentLevel getLevel() {
        return level;
    }

    public boolean getIsBuildable(){
        if(color.equals(Color.BLACK))
            return false;

        if(owner instanceof Bank)
            return false;

        if(!PropertyManager.getIstance().hasAllPropertiesOfColor(this,(Player) owner))
            return false;

        return  true;
    }
}