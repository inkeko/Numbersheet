package hu.csaba.numbersheet.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandler.class);

    public static void handle(AppException e) {

        // 1) Logolás a hibatípus alapján
        if (e instanceof ConfigException) {
            logger.error("Konfigurációs hiba: {} | Kód: {}", e.getMessage(), e.getErrorCode(), e);
            printUserMessage(e);
            shutdown();
            return;
        }

        if (e instanceof PdfException) {
            logger.error("PDF generálási hiba: {} | Kód: {}", e.getMessage(), e.getErrorCode(), e);
            printUserMessage(e);
            shutdown();
            return;
        }

        if (e instanceof NumberGenerationException) {
            logger.warn("Számgenerálási hiba: {} | Kód: {}", e.getMessage(), e.getErrorCode(), e);
            printUserMessage(e);
            // Ez NEM feltétlen kritikus → nem állítjuk le a programot
            return;
        }

        // 2) Általános AppException
        logger.error("Ismeretlen alkalmazáshiba: {} | Kód: {}", e.getMessage(), e.getErrorCode(), e);
        printUserMessage(e);
        shutdown();
    }

    private static void printUserMessage(AppException e) {
        System.out.println();
        System.out.println("⚠ Hiba történt:");
        System.out.println("   " + e.getUserMessage());
        if (e.getErrorCode() != null) {
            System.out.println("   Hibakód: " + e.getErrorCode());
        }
        System.out.println();
    }

    private static void shutdown() {
        System.out.println("A program leáll.");
        System.exit(1);
    }
}
