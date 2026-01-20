package util.number;

import java.util.*;

/**
 * General-purpose numeric utility methods.
 * Általános számkezelő segédmetódusok.
 *
 * All methods are static, and the class cannot be instantiated.
 * Minden metódus statikus, és az osztály nem példányosítható.
 */
public final class NumberUtils {

    // Private constructor to prevent instantiation.
    // Privát konstruktor, hogy az osztályt ne lehessen példányosítani.
    private NumberUtils() {
        throw new AssertionError("Utility class cannot be instantiated. / Utility osztály, nem példányosítható.");
    }

    /**
     * Clamps a value between a minimum and maximum.
     * Egy értéket a megadott minimum és maximum közé szorít.
     *
     * @param value the input value
     *              a bemeneti érték
     * @param min minimum allowed value
     *            a minimum érték
     * @param max maximum allowed value
     *            a maximum érték
     * @return clamped value
     *         a korlátozott érték
     */
    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    /**
     * Generates a random integer between min and max (inclusive).
     * Véletlenszámot generál a megadott tartományban (beleértve a széleket).
     *
     * @param min lower bound
     *            alsó határ
     * @param max upper bound
     *            felső határ
     * @return random integer
     *         véletlenszám
     */
    public static int randomInRange(int min, int max) {
        if (min > max) {
            throw new IllegalArgumentException(
                    "Min cannot be greater than max. / A minimum nem lehet nagyobb a maximumnál."
            );
        }
        return new Random().nextInt((max - min) + 1) + min;
    }

    /**
     * Calculates the average of a list of integers.
     * Kiszámítja egy egész számokat tartalmazó lista átlagát.
     *
     * @param numbers list of integers
     *                a számok listája
     * @return average value
     *         az átlag értéke
     */
    public static double average(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException(
                    "List cannot be null or empty. / A lista nem lehet null vagy üres."
            );
        }
        return numbers.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    /**
     * Calculates the median of a list of integers.
     * Kiszámítja egy egész számokat tartalmazó lista mediánját.
     *
     * @param numbers list of integers
     *                a számok listája
     * @return median value
     *         a medián értéke
     */
    public static double median(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException(
                    "List cannot be null or empty. / A lista nem lehet null vagy üres."
            );
        }

        List<Integer> sorted = new ArrayList<>(numbers);
        Collections.sort(sorted);

        int size = sorted.size();
        if (size % 2 == 1) {
            return sorted.get(size / 2);
        } else {
            return (sorted.get(size / 2 - 1) + sorted.get(size / 2)) / 2.0;
        }
    }

    /**
     * Calculates the mode (most frequent value) of a list of integers.
     * Kiszámítja egy egész számokat tartalmazó lista móduszát (leggyakoribb érték).
     *
     * @param numbers list of integers
     *                a számok listája
     * @return mode value
     *         a módusz értéke
     */
    public static int mode(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException(
                    "List cannot be null or empty. / A lista nem lehet null vagy üres."
            );
        }

        Map<Integer, Integer> freq = new HashMap<>();
        for (int n : numbers) {
            freq.put(n, freq.getOrDefault(n, 0) + 1);
        }

        return freq.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .get()
                .getKey();
    }
}