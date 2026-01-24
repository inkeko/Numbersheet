package hu.csaba.numbersheet.config;

import hu.csaba.numbersheet.error.ConfigException;

import util.validation.ValidationUtils;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final String CONFIG_FILE = "config.properties";

    public static AppConfig load() throws ConfigException {

        Properties props = new Properties();

        // 1) Load config.properties
        // 1) config.properties betöltése
        try (InputStream input = ConfigLoader.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {

            if (input == null) {
                throw new ConfigException(
                        "config.properties not found in resources.",
                        "A config.properties fájl nem található a resources mappában.",
                        "CFG-001"
                );
            }

            props.load(input);

        } catch (IOException e) {
            throw new ConfigException(
                    "Failed to read config.properties: " + e.getMessage(),
                    "Nem sikerült beolvasni a config.properties fájlt.",
                    "CFG-002",
                    e
            );
        }

        // 2) Read values
        // 2) Értékek kiolvasása
        String outputDirectory = props.getProperty("output.directory");
        String pdfFileName = props.getProperty("pdf.filename");
        String numbersCountStr = props.getProperty("numbers.count");
        String numbersMinStr = props.getProperty("numbers.min");
        String numbersMaxStr = props.getProperty("numbers.max");
        String maxLinesStr = props.getProperty("page.maxLines");
        String maxCharsStr = props.getProperty("page.maxChars");
        String fontName = props.getProperty("font.name");
        String fontSizeStr = props.getProperty("font.size");

        // 3) Validate text fields using StringUtils
        // 3) Szöveges mezők validálása StringUtils segítségével
        // 3) Validálás ValidationUtils segítségével

        if (!ValidationUtils.isNotBlank(outputDirectory)) {
            throw new ConfigException(
                    "output.directory is missing.",
                    "A kimeneti könyvtár nincs beállítva.",
                    "CFG-003"
            );
        }

        if (!ValidationUtils.isNotBlank(pdfFileName)) {
            throw new ConfigException(
                    "pdf.filename is missing.",
                    "A PDF fájl neve nincs beállítva.",
                    "CFG-004"
            );
        }

        if (!ValidationUtils.isNotBlank(fontName)) {
            throw new ConfigException(
                    "font.name is missing.",
                    "A betűtípus nincs beállítva.",
                    "CFG-008"
            );
        }



        // 4) Validate numeric fields using StringUtils.isNumeric
        // 4) Szám mezők validálása StringUtils.isNumeric segítségével
        // 4) Szám mezők validálása

        if (!ValidationUtils.isInteger(numbersCountStr)
                || !ValidationUtils.isInteger(numbersMinStr)
                || !ValidationUtils.isInteger(numbersMaxStr)
                || !ValidationUtils.isInteger(maxLinesStr)
                || !ValidationUtils.isInteger(maxCharsStr)
                || !ValidationUtils.isInteger(fontSizeStr)) {

            throw new ConfigException(
                    "Invalid numeric values in config file.",
                    "A konfigurációs fájl hibás számértékeket tartalmaz.",
                    "CFG-005"
            );
        }




        // 5) Convert to integers
        // 5) Átalakítás egész számokká
        int numbersCount = Integer.parseInt(numbersCountStr.trim());
        int numbersMin = Integer.parseInt(numbersMinStr.trim());
        int numbersMax = Integer.parseInt(numbersMaxStr.trim());
        int maxLines = Integer.parseInt(maxLinesStr.trim());
        int maxChars = Integer.parseInt(maxCharsStr.trim());
        int fontSize = Integer.parseInt(fontSizeStr.trim());

        // 6) Logical validations
        // 6) Logikai validációk
        // 6) Logikai validációk ValidationUtils segítségével

        if (!ValidationUtils.isPositive(numbersCount)) {
            throw new ConfigException(
                    "numbers.count <= 0",
                    "A generálandó számok mennyisége hibás.",
                    "CFG-007"
            );
        }

        if (!ValidationUtils.isPositive(fontSize)) {
            throw new ConfigException(
                    "font.size <= 0",
                    "A betűméret hibás.",
                    "CFG-009"
            );
        }

        if (!ValidationUtils.isPositive(maxLines)) {
            throw new ConfigException(
                    "page.maxLines <= 0",
                    "A maxLines értéke hibás.",
                    "CFG-010"
            );
        }

        if (!ValidationUtils.isPositive(maxChars)) {
            throw new ConfigException(
                    "page.maxChars <= 0",
                    "A maxChars értéke hibás.",
                    "CFG-011"
            );
        }

        if (!ValidationUtils.isInRange(numbersMin, Integer.MIN_VALUE, numbersMax - 1)) {
            throw new ConfigException(
                    "numbers.min >= numbers.max",
                    "A számgenerálási tartomány hibás.",
                    "CFG-006"
            );
        }
        // 7) Create AppConfig
        // 7) AppConfig létrehozása
        return new AppConfig(
                outputDirectory,
                pdfFileName,
                numbersCount,
                numbersMin,
                numbersMax,
                maxLines,
                maxChars,
                fontName,
                fontSize
        );
    }
}