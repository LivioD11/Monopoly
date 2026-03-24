package com.monopoly;

public enum MenuOption {
    VIEW_BALANCE(1, "Visualizza saldo"),
    ROLL_DICE(2, "Lancia i dadi e muovi"),
    UNKNOWN(0, "Opzione non valida");

    private final int id;
    private final String description;

    MenuOption(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public static MenuOption fromInt(int value) {
        for (MenuOption option : values()) {
            if (option.id == value) return option;
        }
        return UNKNOWN;
    }

    @Override
    public String toString() {
        return id + ". " + description;
    }
}