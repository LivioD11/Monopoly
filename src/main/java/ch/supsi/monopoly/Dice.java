package ch.supsi.monopoly;

public final class Dice {
    private static final int MIN = 1;
    private static final int MAX = 6;
    public record RollResult(int total, boolean allSame) {}

    /**
     * Lancia un singolo dado a 6 facce.
     */
    public static int roll() {
        int randomNumber = (int) (Math.random() * MAX) + MIN;
        return randomNumber;
    }

    /**
     * Lancia n dadi, calcola la somma totale e verifica se sono tutti uguali.
     * * @param n Il numero di dadi da lanciare.
     * @return Un oggetto RollResult contenente il totale e il flag allSame.
     */
    public static RollResult rollMultiple(int n) {
        if (n <= 0) return new RollResult(0, true);

        int firstRoll = roll();
        int total = firstRoll;
        boolean allSame = true;

        for (int i = 1; i < n; i++) {
            int currentRoll = roll();
            total += currentRoll;

            // Se anche solo un dado è diverso dal primo, allSame diventa false
            if (currentRoll != firstRoll) {
                allSame = false;
            }
        }

        return new RollResult(total, allSame);
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