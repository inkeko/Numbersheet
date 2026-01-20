package hu.csaba.numbersheet.generator;

import hu.csaba.numbersheet.config.AppConfig;
import util.number.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates random numbers based on configuration.
 * Konfiguráció alapján véletlenszámokat generál.
 */
public class NumberGenerator {

    private final AppConfig config;

    /**
     * Constructor that receives the application configuration.
     * Konstruktor, amely megkapja az alkalmazás konfigurációját.
     */
    public NumberGenerator(AppConfig config) {
        this.config = config;
    }

    /**
     * Generates a list of random integers using NumberUtils.
     * Véletlenszámok listáját generálja a NumberUtils segítségével.
     *
     * @return list of generated numbers
     *         a generált számok listája
     */
    public List<Integer> generate() {

        List<Integer> numbers = new ArrayList<>();

        for (int i = 0; i < config.getNumbersCount(); i++) {

            // Generate random number using NumberUtils
            // Véletlenszám generálása NumberUtils segítségével
            int value = NumberUtils.randomInRange(
                    config.getNumbersMin(),
                    config.getNumbersMax()
            );

            // Optional safety clamp (keeps value inside range)
            // Opcionális clamp (biztosítja, hogy a szám a tartományban maradjon)
            value = NumberUtils.clamp(
                    value,
                    config.getNumbersMin(),
                    config.getNumbersMax()
            );

            numbers.add(value);
        }

        return numbers;
    }
}