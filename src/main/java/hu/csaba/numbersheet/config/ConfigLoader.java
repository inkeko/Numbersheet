package hu.csaba.numbersheet.config;

import hu.csaba.numbersheet.error.ConfigException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private static final String CONFIG_FILE = "config.properties";

    public static AppConfig load() {

        Properties props = new Properties();

        // 1) Betöltjük a config.properties fájlt
        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {

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
                    "Nem sikerült beolvasni a config.properties fájlt.",
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
        String numbersMaxLines = props.getProperty("page.maxLines");
        String numbersMaxChars = props.getProperty("page.maxChars");
        String fontName = props.getProperty("font.name");
        String fontSize = props.getProperty("font.size");

        // 3) Validáljuk az értékeket
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

        int numbersCount;
        int numbersMin;
        int numbersMax;
        int numbersmaxLines;
        int numbersmaxChars;
        int numbersFontSize;

        try {
            numbersCount = Integer.parseInt(numbersCountStr);
            numbersMin = Integer.parseInt(numbersMinStr);
            numbersMax = Integer.parseInt(numbersMaxStr);
            numbersmaxLines= Integer.parseInt(numbersMaxLines);
            numbersmaxChars = Integer.parseInt(numbersMaxChars);
            numbersFontSize = Integer.parseInt(fontSize);
        } catch (NumberFormatException e) {
            throw new ConfigException(
                    "A számgenerálási paraméterek nem érvényes egész számok.",
                    "A konfigurációs fájl hibás számértékeket tartalmaz.",
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

        if (fontName == null || fontName.isBlank()) {
            throw new ConfigException(
                    "font.name nincs megadva.",
                    "A betűtípus nincs beállítva.",
                    "CFG-008"
            );
        }
         if(numbersFontSize < 0 ){
             throw new ConfigException(
                     "font.size nincs megadva.",
                     "A betűtípus nincs beállítva.",
                     "CFG-009"
             );
         }
        if(numbersmaxLines < 0 ){
            throw new ConfigException(
                    "maxLines nem jo az értéke.",
                    "A maxLine értéke  nincs beállítva.",
                    "CFG-010"
            );
        }
        if(numbersmaxChars < 0 ){
            throw new ConfigException(
                    "maxChar értéke nincs megadva.",
                    "A maxCharértéke  nincs beállítva.",
                    "CFG-011"
            );
        }

        // 4) Létrehozzuk az AppConfig objektumot
        return new AppConfig(
                outputDirectory,
                pdfFileName,
                numbersCount,
                numbersMin,
                numbersMax,
                numbersmaxLines,
                numbersmaxChars,
                fontName,
                numbersFontSize
        );
    }
}
