package ch.supsi.monopoly.utilities;

public enum MenuOption {
    VIEW_BALANCE(1, "Visualizza saldo"),
    ROLL_DICE(2, "Lancia i dadi e muovi"),
    UNKNOWN(0, "Opzione non valida");

    private final int ID;
    private final String DESCRIPTION;

    MenuOption(int id, String description) {
        this.ID = id;
        this.DESCRIPTION = description;
    }

    public static MenuOption fromInt(int value) {
        for (MenuOption option : values()) {
            if (option.ID == value) return option;
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return ID + ". " + DESCRIPTION;
    }
}