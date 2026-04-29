package ch.supsi.monopoly;

public final class Dice {
    private static final int MIN = 1;
    private static final int MAX = 6;

    /**
     * Lancia un singolo dado a 6 facce.
     */
    public static int roll() {
        int randomNumber = (int) (Math.random() * MAX) + MIN;
        return randomNumber;
    }

    private Dice(){

    }

    public static String getFaceIcon(int value) {
        return switch (value) {
            case 1 -> "⚀";
            case 2 -> "⚁";
            case 3 -> "⚂";
            case 4 -> "⚃";
            case 5 -> "⚄";
            case 6 -> "⚅";
            default -> "?";
        };
    }
}