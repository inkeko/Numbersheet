package hu.csaba.numbersheet.util.strings;

import java.util.List;
import java.util.Objects;

public final class StringUtils {
    // Private constructor to prevent instantiation
    // Privát konstruktor, hogy az osztályt ne lehessen példányosítani
    private StringUtils() {
        throw new AssertionError("Utility class cannot be instantiated.");
    }

    /**
     * Checks if a string is null, empty, or contains only whitespace.
     */
    /**
     * Megvizsgálja, hogy a szöveg null, üres vagy csak whitespace karaktereket tartalmaz.
     *
     * @param s a vizsgálandó szöveg
     * @return true, ha a szöveg üres vagy whitespace; különben false
     */


    public static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }

    /**
     * Trims a string safely. Null becomes empty string.
     */
    /**
     * Biztonságos trim művelet.
     * Ha a szöveg null, akkor üres stringet ad vissza.
     *
     * @param s a szöveg
     * @return a levágott szöveg vagy üres string
     */

    public static String safeTrim(String s) {
        return s == null ? "" : s.trim();
    }

    /**
     * Returns true if the string contains only digits.
     */
    /**
     * Megvizsgálja, hogy a szöveg csak számjegyeket tartalmaz-e.
     * Whitespace-t levágja, null vagy üres esetben false.
     *
     * @param s a vizsgálandó szöveg
     * @return true, ha csak számjegyekből áll; különben false
     */


    public static boolean isNumeric(String s) {
        if (isBlank(s)) {
            return false;
        }
        for (char c : s.trim().toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Converts null to empty string.
     */
    /**
     * Null értéket üres stringgé alakít.
     *
     * @param s a szöveg
     * @return üres string, ha null; különben az eredeti szöveg
     */


    public static String nullToEmpty(String s) {
        return s == null ? "" : s;
    }

    /**
     * Repeats a string N times.
     */
    /**
     * Egy szöveget ismétel meg N alkalommal.
     *
     * @param s a szöveg
     * @param times hányszor ismétlődjön
     * @return az ismételt szöveg
     */


    public static String repeat(String s, int times) {
        if (times < 0) {
            throw new IllegalArgumentException("Repeat count cannot be negative.");
        }
        if (s == null || s.isEmpty() || times == 0) {
            return "";
        }
        return s.repeat(times);
    }

    /**
     * Joins a list of strings with a delimiter.
     */
    /**
     * Összefűz egy lista elemeit a megadott elválasztóval.
     *
     * @param items a szövegek listája
     * @param delimiter az elválasztó
     * @return az összefűzött szöveg
     */


    public static String join(List<String> items, String delimiter) {
        if (items == null || items.isEmpty()) {
            return "";
        }
        Objects.requireNonNull(delimiter, "Delimiter cannot be null.");
        return String.join(delimiter, items);
    }

}
