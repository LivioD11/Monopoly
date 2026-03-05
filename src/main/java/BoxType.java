enum BoxType {
    TOLL("Pedaggio"),
    START("Partenza");

    private final String name;

    BoxType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
