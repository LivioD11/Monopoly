package ch.supsi.monopoly.board.property;

public abstract class Building {
    protected int price;

    public Building(int price){

    }

    public int getPrice(){
        return this.price;
    }
}
