package ch.supsi.monopoly.cli;

import ch.supsi.monopoly.Bank;
import ch.supsi.monopoly.Owner;
import ch.supsi.monopoly.board.property.BoxProperty;
import ch.supsi.monopoly.board.property.Building;
import ch.supsi.monopoly.board.property.DevelopmentLevel;

import java.util.List;

public final class Display {
    private static final int BOX_WIDTH = 24;
    private static final int CONTENT_WIDTH = BOX_WIDTH - 2;
    private static final String HORIZONTAL_BORDER = "-".repeat(BOX_WIDTH);

    private Display() {}

    public static String[] boxRepresentation(String name, String description, int value, Owner owner, Color color) {
        String coloredName = TextFormatter.color(name, color);
        String ownerInfo = (owner != null && !(owner instanceof Bank)) ? owner.toString() : "";
        String descInfo = (value > 0) ? description : "";

        return buildBox(
                TextFormatter.padAnsi(coloredName, CONTENT_WIDTH),
                descInfo,
                ownerInfo,
                "",
                ""
        );
    }

    public static String[] boxPropertyRepresentation(BoxProperty property) {
        String coloredName = TextFormatter.color(property.getName(), property.getColor());
        String ownerOrPrice = drawOwner(property.getOwner(), property.getPrice());
        String buildings = drawBuildings(property.getLevel(), property.getBuildings());

        return buildBox(
                TextFormatter.padAnsi(coloredName, CONTENT_WIDTH),
                property.getValue() > 0 ? property.getDescription() : "",
                ownerOrPrice,
                TextFormatter.padAnsiAndEmoji(buildings, CONTENT_WIDTH),
                ""
        );
    }

    /**
     * Metodo helper per comporre la struttura visiva della casella.
     * Evita di riscrivere i bordi e i formati in ogni metodo pubblico.
     */
    private static String[] buildBox(String... lines) {
        String[] box = new String[7];
        box[0] = HORIZONTAL_BORDER;

        // Riempie le righe centrali (dalla 1 alla 5)
        for (int i = 0; i < 5; i++) {
            String content = (i < lines.length) ? lines[i] : "";
            // Se la riga è già formattata con ANSI/Emoji non usiamo String.format standard
            // perché sballerebbe i calcoli degli spazi (usiamo i formatter esistenti)
            if (content.contains("\u001B") || content.contains("\uD83C")) {
                box[i + 1] = "|" + content + "|";
            } else {
                box[i + 1] = String.format("|%-22s|", content);
            }
        }

        box[6] = HORIZONTAL_BORDER;
        return box;
    }

    private static String drawOwner(Owner owner, int price) {
        if (owner == null || owner instanceof Bank) {
            return "Prezzo: " + TextFormatter.formatCurrency(price);
        }
        return owner.getName();
    }

    private static String drawBuildings(DevelopmentLevel level, List<Building> buildings) {
        if (level == null) return "";

        if (level == DevelopmentLevel.HOUSES) {
            return "\u2302".repeat(buildings.size()) + " (" + buildings.size() + " case)";
        }

        if (level == DevelopmentLevel.HOTEL) {
            return "\u2617 (hotel)";
        }

        return "";
    }
}