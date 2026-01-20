package hu.csaba.numbersheet.config;

import hu.csaba.numbersheet.error.ConfigException;
import hu.csaba.numbersheet.util.strings.StringUtils;


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
        if (StringUtils.isBlank(outputDirectory)) {
            throw new ConfigException(
                    "output.directory is missing.",
                    "A kimeneti könyvtár nincs beállítva.",
                    "CFG-003"
            );
        }

        if (StringUtils.isBlank(pdfFileName)) {
            throw new ConfigException(
                    "pdf.filename is missing.",
                    "A PDF fájl neve nincs beállítva.",
                    "CFG-004"
            );
        }

        if (StringUtils.isBlank(fontName)) {
            throw new ConfigException(
                    "font.name is missing.",
                    "A betűtípus nincs beállítva.",
                    "CFG-008"
            );
        }

        // 4) Validate numeric fields using StringUtils.isNumeric
        // 4) Szám mezők validálása StringUtils.isNumeric segítségével
        if (!StringUtils.isNumeric(numbersCountStr)
                || !StringUtils.isNumeric(numbersMinStr)
                || !StringUtils.isNumeric(numbersMaxStr)
                || !StringUtils.isNumeric(maxLinesStr)
                || !StringUtils.isNumeric(maxCharsStr)
                || !StringUtils.isNumeric(fontSizeStr)) {

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
        if (numbersMin >= numbersMax) {
            throw new ConfigException(
                    "numbers.min >= numbers.max",
                    "A számgenerálási tartomány hibás.",
                    "CFG-006"
            );
        }

        if (numbersCount <= 0) {
            throw new ConfigException(
                    "numbers.count <= 0",
                    "A generálandó számok mennyisége hibás.",
                    "CFG-007"
            );
        }

        if (fontSize <= 0) {
            throw new ConfigException(
                    "font.size <= 0",
                    "A betűméret hibás.",
                    "CFG-009"
            );
        }

        if (maxLines <= 0) {
            throw new ConfigException(
                    "page.maxLines <= 0",
                    "A maxLines értéke hibás.",
                    "CFG-010"
            );
        }

        if (maxChars <= 0) {
            throw new ConfigException(
                    "page.maxChars <= 0",
                    "A maxChars értéke hibás.",
                    "CFG-011"
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