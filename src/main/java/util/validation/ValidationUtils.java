package util.validation;

import util.strings.StringUtils;

/**
 * General-purpose validation utilities.
 * Általános validációs segédmetódusok.
 *
 * All methods are static, and the class cannot be instantiated.
 * Minden metódus statikus, és az osztály nem példányosítható.
 */
public final class ValidationUtils {

    // Private constructor to prevent instantiation.
    // Privát konstruktor, hogy az osztályt ne lehessen példányosítani.
    private ValidationUtils() {
        throw new AssertionError("Utility class cannot be instantiated. / Utility osztály, nem példányosítható.");
    }

    /**
     * Checks if a string is not null and not blank.
     * Ellenőrzi, hogy a string nem null és nem üres.
     *
     * @param value the string to check
     *              az ellenőrizendő string
     * @return true if valid, false otherwise
     *         true ha érvényes, különben false
     */
    public static boolean isNotBlank(String value) {
        return !StringUtils.isBlank(value);
    }

    /**
     * Checks if a string is a valid integer number.
     * Ellenőrzi, hogy a string érvényes egész szám-e.
     *
     * @param value the string to check
     *              az ellenőrizendő string
     * @return true if numeric, false otherwise
     *         true ha szám, különben false
     */
    public static boolean isInteger(String value) {
        return StringUtils.isNumeric(value);
    }

    /**
     * Checks if a number is positive (> 0).
     * Ellenőrzi, hogy a szám pozitív-e (> 0).
     *
     * @param value the number to check
     *              az ellenőrizendő szám
     * @return true if positive
     *         true ha pozitív
     */
    public static boolean isPositive(int value) {
        return value > 0;
    }

    /**
     * Checks if a number is within a given range.
     * Ellenőrzi, hogy a szám a megadott tartományban van-e.
     *
     * @param value the number to check
     *              az ellenőrizendő szám
     * @param min minimum allowed value
     *            minimum érték
     * @param max maximum allowed value
     *            maximum érték
     * @return true if value is between min and max
     *         true ha az érték a tartományban van
     */
    public static boolean isInRange(int value, int min, int max) {
        return value >= min && value <= max;
    }
}
