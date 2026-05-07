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
import ch.supsi.monopoly.cli.Display;
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
    private Building tmpBuilding;
    private DevelopmentLevel level;

    /**
     * Costruttore della proprietà. Genera un prezzo casuale e inizializza gli edifici.
     * @param name Nome della casella.
     */
    public BoxProperty(String name) {
        super("(P) "+name);
        this.buildings = new ArrayList<>();
        this.setDescription("Paga: "+getValue());
        this.price = generatePrice();
        this.level = DevelopmentLevel.EMPTY;
        PropertyManager.getIstance().addProperty(this);
        this.updateRepresentation();
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
        this.representation  = Display.boxPropertyRepresentation(this);
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

        if(price>buyer.getBalance()){
            System.out.println(TextFormatter.color("Saldo insufficiente",Color.RED));
            return false;
        }

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

        // CASA
        if (canBuildHouse()) {

            Building house = new House();

            if (!hasEnoughMoney(house)) {
                System.out.println(
                        owner +
                                TextFormatter.color(" saldo insufficiente", Color.RED)
                );
                return;
            }

            buildings.add(house);

            level = DevelopmentLevel.HOUSES;

            updateRepresentation();

            System.out.println(
                    owner +
                            TextFormatter.color(" ha costruito una casa", Color.YELLOW)
            );

            return;
        }

        // HOTEL
        if (canBuildHotel()) {

            Building hotel = new Hotel();

            if (!hasEnoughMoney(hotel)) {
                System.out.println(
                        owner +
                                TextFormatter.color(" saldo insufficiente", Color.RED)
                );
                return;
            }

            buildings.clear();
            buildings.add(hotel);

            level = DevelopmentLevel.HOTEL;

            updateRepresentation();

            System.out.println(
                    owner +
                            TextFormatter.color(" ha costruito un albergo", Color.YELLOW)
            );
        }
    }

    private boolean hasEnoughMoney(Building building) {
        return owner.getBalance() >= building.getPrice();
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

        this.menu = new Menu(setMessage(player),false,true);
        this.setupMenu(player);
        this.menu.setReset(() -> {
            menu.setDescription(setMessage(player));
            this.setupMenu(player);
        });
        menu.displayAndSelect();
    }

    private String setMessage(Player player){
        Color balanceColor = (player.getBalance() >= getPrice()) ? Color.GREEN : Color.RED;
        String property = this.toString();

        if(canBuildHouse() && getIsBuildable()){
            tmpBuilding = new House();
            balanceColor = (player.getBalance() >= tmpBuilding.getPrice()) ? Color.GREEN : Color.RED;
            property += "\n- Costruisci casa: "+TextFormatter.color(TextFormatter.formatCurrency(tmpBuilding.getPrice()),Color.YELLOW);
        }

        if (canBuildHotel()){
            tmpBuilding = new Hotel();
            balanceColor = (player.getBalance() >= tmpBuilding.getPrice()) ? Color.GREEN : Color.RED;
            property += "\n- Costruisci hotel: "+TextFormatter.color(TextFormatter.formatCurrency(tmpBuilding.getPrice()),Color.YELLOW);
        }


        String message = String.format(""" 
                %s
                
                Saldo attuale: %s""",
                property,
                TextFormatter.color(TextFormatter.formatCurrency(player.getBalance()),balanceColor));

        return message;
    }

    @Override
    public String toString() {
        return String.format("[ %s ]\n- Tassa: %s\n- Prezzo: %s",
                this.name,
                TextFormatter.color(TextFormatter.formatCurrency(this.value),Color.YELLOW),
                TextFormatter.color(TextFormatter.formatCurrency(this.price),Color.YELLOW));
    }

    private int countHouses() {
        int count = 0;

        for (Building b : buildings) {
            if (b instanceof House) {
                count++;
            }
        }

        return count;
    }

    private int countHotels() {
        int count = 0;

        for (Building b : buildings) {
            if (b instanceof Hotel) {
                count++;
            }
        }

        return count;
    }

    private boolean canBuildHouse() {

        boolean validLevel =
                level == DevelopmentLevel.EMPTY
                        || level == DevelopmentLevel.HOUSES;

        boolean underLimit = countHouses() < HOUSES_LIMIT;

        boolean noHotel = countHotels() == 0;

        return validLevel && underLimit && noHotel;
    }

    private boolean canBuildHotel() {

        boolean validLevel =
                level == DevelopmentLevel.HOUSES
                        || level == DevelopmentLevel.HOTEL;

        boolean enoughHouses =
                countHouses() == HOUSES_LIMIT;

        boolean underLimit =
                countHotels() < HOTELS_LIMIT;

        return validLevel && enoughHouses && underLimit;
    }

    // Getters

    @Override
    public int getValue(){
        int value = this.value;
        if(buildings==null)
            return value;
        for (Building building: buildings)
            value += building.getValue();
        return value;
    }

    public Owner getOwner(){
        return  this.owner;
    }

    public boolean getIsPurchasable(){
        return (owner instanceof Bank);
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