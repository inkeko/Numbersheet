package hu.csaba.numbersheet.generator;

import hu.csaba.numbersheet.config.AppConfig;
import hu.csaba.numbersheet.error.NumberGenerationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NumberGenerator {

    private final AppConfig config;
    private final Random random = new Random();

    public NumberGenerator(AppConfig config) {
        this.config = config;
    }

    public List<Integer> generate()  throws NumberGenerationException
    {

        int min = config.getNumbersMin();
        int max = config.getNumbersMax();
        int count = config.getNumbersCount();

        // 1) Tartomány ellenőrzése
        if (min >= max) {
            throw new NumberGenerationException(
                    "A minimum érték nagyobb vagy egyenlő a maximum értéknél.",
                    "Hibás számgenerálási tartomány.",
                    "GEN-001"
            );
        }

        if (count <= 0) {
            throw new NumberGenerationException(
                    "A generálandó mennyiség <= 0.",
                    "A generálandó számok mennyisége hibás.",
                    "GEN-002"
            );
        }

        // 2) Számok generálása kevert eloszlással
        List<Integer> numbers = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {

            double mix = random.nextDouble();
            int value;

            if (mix < 0.7) {
                // 70% Gauss eloszlás
                double g = random.nextGaussian();     // -∞..+∞, de 99% -3..+3 között
                double n = (g + 3) / 6;               // 0..1 közé normalizálva
                n = Math.max(0, Math.min(1, n));      // biztosan 0..1 között marad
                value = (int) (min + n * (max - min));
            } else {
                // 30% uniform eloszlás
                value = random.nextInt(max - min + 1) + min;
            }

            numbers.add(value);
        }

        return numbers;
    }
}