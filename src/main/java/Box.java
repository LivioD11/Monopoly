import cli.TextColorizer;

public abstract class Box {
    protected static final int TOLL_MIN = 50;
    protected static final int TOLL_MAX = 150;
    private String[] representation;
    protected final int value;
    protected String name;
    protected String description;

    // START: quando ci passi sopra prendi 100CHF  !!!
    // PARCHEGGIO: ci passi sopra e non fa nulla
    // TASSA DI LUSSO: ci finisci sopra e paghi 200CHF
    // TASSA SUL PATRIMONIO: ci finisci sopra e paghi il 10% del tuo patrimonio
    // PROPRIETA': dove paghi cifre random
    // STAZIONE: è al centro di ogni lato



    public Box(int value, String name) {
        this.value = value;
        this.name = name;
        this.description = "Paga "+this.value;
        this.updateRepresentation();

    }

    protected void updateRepresentation(){
        this.representation  = new String[]{
                "-".repeat(24),
                "|"+TextColorizer.padAnsi(this.name,22)+"|",
                this.value > 0 ? String.format("|%-22s|", this.description) :
                String.format("|%-22s|", ""),
                String.format("|%-22s|", ""),
                String.format("|%-22s|", ""),
                String.format("|%-22s|", ""),
                "-".repeat(24),
        };
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (String line : representation) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    public String draw(int index){
        return this.representation[index];
    }

    // SETTERS

    public void setDescription(String description){
        this.description = description;
        this.updateRepresentation();
    }

    // GTTERS

    public int getValue() {
        return value;
    }
}
