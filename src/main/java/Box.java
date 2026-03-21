import cli.TextColorizer;

public abstract class Box {
    private static final int TOLL_MIN = 50;
    private static final int TOLL_MAX = 150;
    //TODO: aggiungere il colore
    private String[] representation;
    private final int value;
    public String name;

    // START: quando ci passi sopra prendi 100CHF  !!!
    // PARCHEGGIO: ci passi sopra e non fa nulla
    // TASSA DI LUSSO: ci finisci sopra e paghi 200CHF
    // TASSA SUL PATRIMONIO: ci finisci sopra e paghi il 10% del tuo patrimonio
    // PROPRIETA': dove paghi cifre random
    // STAZIONE: è al centro di ogni lato



    public Box(int value, String nome) {
        this.value = value;
        this.name = nome;
        this.represents();

    }

    protected void represents(){
        this.representation  = new String[]{
                "-".repeat(24),
                "|"+TextColorizer.padAnsi(this.name,22)+"|",
                this.value > 0 ? String.format("| %-21s|", this.value+"$") :
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

    public int getValue() {
        return value;
    }

    public abstract void applyEffect(Player player);

    public String draw(int index){
        return this.representation[index];
    }
}
