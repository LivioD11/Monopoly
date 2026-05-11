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
                    this.buy();
                }
            }));
        }

        if (player.equals(owner) && PropertyManager.getInstance().hasAllPropertiesOfColor(this, player)) {
            menu.addOption(new Option("Costruire nella proprietà", () -> {
                if (Action.confirmAction("Costruire nella proprietà")) { // Corretto "priprietà"
                    this.build();
                }
            }));
        }
    }

    @Override
    public void updateRepresentation(){
        this.representation  = Display.boxPropertyRepresentation(this);
    }

    private static int generatePrice() {
        return (int) (Math.random() * (MAX_PRICE - MIN_PRICE + 1)) + MIN_PRICE;
    }

    public void bankGetbackProperty(){
        this.buildings.clear();
        this.owner = Bank.getInstance();
    }

    public void applyEffect(Player player) {
        this.interact(player);
        this.tax();
    }

    @Override
    public void interact(Player player) {
        this.interactedPlayer = player;

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
                (%d / %d) %s 
                
                Saldo attuale: %s""",
                PropertyManager.getInstance().getNumberOfPropertiesByColorAndOwner(this.getColor(),player),
                PropertyManager.getInstance().getNumberOfPropertiesByColor(this.getColor()),
                propertyDesc.toString(),
                TextFormatter.color(TextFormatter.formatCurrency(player.getBalance()), balanceColor));
    }

    @Override
    public void setLevel(DevelopmentLevel level){
        this.level = level;
    }

    @Override
    public String toString() {
        return String.format("[ %s ]\n- Tassa: %s\n- Prezzo: %s\n- Proprietario: %s\n- Costruzioni: %s",
                TextFormatter.color(this.name,this.getColor()),
                TextFormatter.color(TextFormatter.formatCurrency(this.value),Color.YELLOW),
                TextFormatter.color(TextFormatter.formatCurrency(this.price),Color.YELLOW),
                this.getOwner().toString(),
                Display.drawBuildings(this.getLevel(),this.getBuildings()));

    }

    // Getters

    public void setOwner(Owner owner){
        this.owner = owner;
    }

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