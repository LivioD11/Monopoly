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
import ch.supsi.monopoly.utilities.ScannerUtilities;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;

public class BoxProperty extends Box implements Taxable, Purchasable, Buildable {
    private static final int HOUSES_LIMIT = Config.getInt("box.property.houses.limit",0);
    private static final int HOTELS_LIMIT = Config.getInt("box.property.hotels.limit",0);
    private static final int MIN_PRICE = Config.getInt("box.property.price.min",0);
    private static final int MAX_PRICE = Config.getInt("box.property.price.max",0);
    private Menu menu;
    private int price;
    private List<Building> buildings;
    private DevelopmentLevel level;

    public BoxProperty(String name) {
        super(name);
        this.buildings = new ArrayList<>();
        this.price = generatePrice();
        this.level = DevelopmentLevel.EMPTY;
    }

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

        if(!getHasAPlayerOwner())
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
        if(!getHasAPlayerOwner())
            output = owner.toString();
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

    public boolean buy(Owner buyer){
        // La proprietà è già acquistata.
        if(!(owner instanceof Bank))
            return false;

        // Il budget è insufficiente.
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

    public void build() {
        if (level == DevelopmentLevel.EMPTY || level == DevelopmentLevel.HOUSES) {
            if (countHouses(buildings) < HOUSES_LIMIT) {
                buildings.add(new House());
                level = DevelopmentLevel.HOUSES;
                this.updateRepresentation();
                return;
            }
        }

        if (countHouses(buildings) == HOUSES_LIMIT && countHotels(buildings) < HOTELS_LIMIT) {
            buildings.clear(); // opzionale: se le case vengono sostituite
            buildings.add(new Hotel());
            level = DevelopmentLevel.HOTEL;
            this.updateRepresentation();
            return;
        }
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

    // GETTERS

    public Owner getOwner(){
        return  this.owner;
    }

    public boolean getHasAPlayerOwner(){
        if(owner instanceof Bank)
            return false;
        return true;
    }
}
