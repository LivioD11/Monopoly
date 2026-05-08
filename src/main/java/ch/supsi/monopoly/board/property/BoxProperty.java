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
        super(name);
        this.buildings = new ArrayList<>();
        this.setDescription("Paga: "+getValue());
        this.price = generatePrice();
        this.level = DevelopmentLevel.EMPTY;
        PropertyManager.getInstance().addProperty(this);
        this.updateRepresentation();
    }

    protected BoxProperty(String name, Color color){
        this(name);
        this.color = color;
    }

    /**
     * Configura le opzioni del menu (Acquisto/Costruzione) in base allo stato della proprietà.
     */
    private void setupMenu(Player player) {
        if (getIsPurchasable()) {
            menu.addOption(new Option("Acquistare la proprietà", () -> {
                if (Action.confirmAction("Acquistare la proprietà")) {
                    this.buy(player);
                }
            }));
        }

        if (player.equals(owner) && PropertyManager.getInstance().hasAllPropertiesOfColor(this, player)) {
            menu.addOption(new Option("Costruire nella proprietà", () -> {
                if (Action.confirmAction("Costruire nella proprietà")) { // Corretto "priprietà"
                    this.build(player);
                }
            }));
        }
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
     */
    public boolean buy(Owner buyer) {
        if (!getIsPurchasable()) {
            return false;
        }

        if (price > buyer.getBalance()) {
            System.out.println(TextFormatter.color("Saldo insufficiente", Color.RED));
            return false;
        }

        buyer.payMoney(price);
        this.owner = buyer;

        System.out.println(String.format("%s " + TextFormatter.color("ha acquistato la proprietà", Color.YELLOW), buyer.toString()));
        return true;
    }

    /**
     * Aggiunge una casa o un hotel alla proprietà rispettando i limiti.
     */
    public void build(Player player) {
        if (!getIsBuildable()) return;

        if (canBuildHouse()) {
            executeBuild(player, new House(), DevelopmentLevel.HOUSES, "una casa");
        } else if (canBuildHotel()) {
            executeBuild(player, new Hotel(), DevelopmentLevel.HOTEL, "un albergo");
        }
    }

    private void executeBuild(Player player, Building building, DevelopmentLevel nextLevel, String buildingName) {
        if (player.getBalance() < building.getPrice()) {
            System.out.println(owner + TextFormatter.color(" saldo insufficiente", Color.RED));
            return;
        }

        if (nextLevel == DevelopmentLevel.HOTEL) {
            buildings.clear(); // Rimuove le case per far posto all'hotel
        }

        buildings.add(building);
        level = nextLevel;
        player.payMoney(building.getPrice());
        updateRepresentation();

        System.out.println(owner + TextFormatter.color(" ha costruito " + buildingName, Color.YELLOW));
    }

    public void bankGetbackProperty(){
        this.owner = Bank.getInstance();
    }

    public void tax(Player player){
        player.payMoney(this.getValue());
    }

    public void applyEffect(Player player) {
        if(!player.equals(owner))
            this.tax(player);
        this.interact(player);
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
        updateRepresentation();
    }

    private String setMessage(Player player) {
        Color balanceColor = (player.getBalance() >= getPrice()) ? Color.GREEN : Color.RED;
        StringBuilder propertyDesc = new StringBuilder(this.toString());

        if (canBuildHouse() && getIsBuildable()) {
            Building house = new House();
            Color color = (player.getBalance() >= house.getPrice()) ? Color.GREEN : Color.RED;
            propertyDesc.append("\n- Costruisci casa: ").append(TextFormatter.color(TextFormatter.formatCurrency(house.getPrice()), Color.YELLOW));
        }

        if (canBuildHotel()) {
            Building hotel = new Hotel();
            Color color = (player.getBalance() >= hotel.getPrice()) ? Color.GREEN : Color.RED;
            propertyDesc.append("\n- Costruisci hotel: ").append(TextFormatter.color(TextFormatter.formatCurrency(hotel.getPrice()), Color.YELLOW));
        }

        return String.format(""" 
                %s
                
                Saldo attuale: %s""",
                propertyDesc.toString(),
                TextFormatter.color(TextFormatter.formatCurrency(player.getBalance()), balanceColor));
    }

    @Override
    public String toString() {
        return String.format("[ %s ]\n- Tassa: %s\n- Prezzo: %s",
                this.name,
                TextFormatter.color(TextFormatter.formatCurrency(this.value),Color.YELLOW),
                TextFormatter.color(TextFormatter.formatCurrency(this.price),Color.YELLOW));
    }

    // Getters

    private boolean getHasEnoughMoney(Building building) {
        return owner.getBalance() >= building.getPrice();
    }

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

    @Override
    public int getHousesLimit() {
        return HOUSES_LIMIT;
    }

    @Override
    public int getHotelsLimit() {
        return HOTELS_LIMIT;
    }

    @Override
    public List<Building> getBuildings() {
        return this.buildings;
    }

    public DevelopmentLevel getLevel() {
        return level;
    }

    public boolean getIsBuildable(){
        if(color.equals(Color.BLACK))
            return false;

        if(getIsPurchasable())
            return false;

        if(!PropertyManager.getInstance().hasAllPropertiesOfColor(this,(Player) owner))
            return false;

        return  true;
    }
}