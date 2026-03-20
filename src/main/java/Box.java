public abstract class Box {
    private static final int TOLL_MIN = 50;
    private static final int TOLL_MAX = 150;
    //TODO: aggiungere il colore
    private String[] rappresentation;
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
        this.value = (int) (Math.random() * (TOLL_MAX - TOLL_MIN + 1)) +
         TOLL_MIN;

        this.rappresentation  = new String[]{
                "-".repeat(24),
                String.format("| %-21s|", this.name),
                this.value > 0 ? String.format("| %-21s|", this.value+"$") :
                        String.format("|%-22s|", ""),
                String.format("|%-22s|", ""),
                String.format("|%-22s|", ""),
                String.format("|%-22s|", ""),
                "-".repeat(24),
        };
    }

    public void changeTextDescription(String str) {
        this.rappresentation[2]  = String.format("| %-21s|", str);
    }

    public int getValue() {
        return value;
    }

    public abstract void applyEffect(Player player);

    public String draw(int index){
        return this.rappresentation[index];
    }

}
