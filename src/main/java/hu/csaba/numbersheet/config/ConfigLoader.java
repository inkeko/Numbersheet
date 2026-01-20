package hu.csaba.numbersheet.config;

import hu.csaba.numbersheet.error.ConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final String CONFIG_FILE = "config.properties";

    public static AppConfig load() throws ConfigException {

        Properties props = new Properties();

        // 1) config.properties betöltése
        try (InputStream input = ConfigLoader.class.getClassLoader()
                .getResourceAsStream(CONFIG_FILE)) {

            if (input == null) {
                throw new ConfigException(
                        "A config.properties fájl nem található a resources mappában.",
                        "A konfigurációs fájl hiányzik.",
                        "CFG-001"
                );
            }

            props.load(input);

        } catch (IOException e) {
            throw new ConfigException(
                    "Nem sikerült beolvasni a config.properties fájlt: " + e.getMessage(),
                    "A konfigurációs fájl olvasása sikertelen.",
                    "CFG-002",
                    e
            );
        }

        // 2) Kiolvassuk az értékeket
        String outputDirectory = props.getProperty("output.directory");
        String pdfFileName = props.getProperty("pdf.filename");
        String numbersCountStr = props.getProperty("numbers.count");
        String numbersMinStr = props.getProperty("numbers.min");
        String numbersMaxStr = props.getProperty("numbers.max");
        String maxLinesStr = props.getProperty("page.maxLines");
        String maxCharsStr = props.getProperty("page.maxChars");
        String fontName = props.getProperty("font.name");
        String fontSizeStr = props.getProperty("font.size");

        // 3) Validálás
        if (outputDirectory == null || outputDirectory.isBlank()) {
            throw new ConfigException(
                    "output.directory nincs megadva.",
                    "A kimeneti könyvtár nincs beállítva.",
                    "CFG-003"
            );
        }

        if (pdfFileName == null || pdfFileName.isBlank()) {
            throw new ConfigException(
                    "pdf.filename nincs megadva.",
                    "A PDF fájl neve nincs beállítva.",
                    "CFG-004"
            );
        }

        if (fontName == null || fontName.isBlank()) {
            throw new ConfigException(
                    "font.name nincs megadva.",
                    "A betűtípus nincs beállítva.",
                    "CFG-008"
            );
        }

        int numbersCount;
        int numbersMin;
        int numbersMax;
        int maxLines;
        int maxChars;
        int fontSize;

        try {
            numbersCount = Integer.parseInt(numbersCountStr);
            numbersMin = Integer.parseInt(numbersMinStr);
            numbersMax = Integer.parseInt(numbersMaxStr);
            maxLines = Integer.parseInt(maxLinesStr);
            maxChars = Integer.parseInt(maxCharsStr);
            fontSize = Integer.parseInt(fontSizeStr);

        } catch (NumberFormatException e) {
            throw new ConfigException(
                    "A konfigurációs fájl hibás számértékeket tartalmaz.",
                    "A számgenerálási paraméterek nem érvényes egész számok.",
                    "CFG-005",
                    e
            );
        }

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

        // 4) AppConfig létrehozása
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