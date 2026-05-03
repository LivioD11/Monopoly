package ch.supsi.monopoly.board.property;

public enum PropertyOption {
    BUY(1,"Compra la proprietà"),
    BUILD(2, "Costruisci nella proprietà"),
    EXIT(3, "Esci"),
    UNKNOWN(0, "Opzione non valida");

    private final int ID;
    private final String DESCRIPTION;

    PropertyOption(int id, String description) {
        this.ID = id;
        this.DESCRIPTION = description;
    }

    public static PropertyOption fromInt(int value) {
        for (PropertyOption option : values()) {
            if (option.ID == value) return option;
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return ID + ". " + DESCRIPTION;
    }
}
