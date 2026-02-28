public class Box {
    private static final int MIN_TOLL = Config.getInt("box.toll.min", 0);
    private static final int MAX_TOLL = Config.getInt("box.toll.max", 0);
    private String name;
    private int toll;

    public Box(String name){
        this.name = name;
        this.initialize();
    }

    private void initialize(){

    }

}
