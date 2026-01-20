package hu.csaba.numbersheet.start;

import hu.csaba.numbersheet.config.AppConfig;
import hu.csaba.numbersheet.config.ConfigLoader;
import hu.csaba.numbersheet.error.AppException;
import hu.csaba.numbersheet.error.ErrorHandler;
import hu.csaba.numbersheet.generator.NumberGenerator;
import hu.csaba.numbersheet.pdf.PdfCreator;
import hu.csaba.numbersheet.pdf.PdfPageFormatter;

import java.util.List;

public class ProgramLauncher {

    public static void start() {

        try {
            // 1) Konfiguráció betöltése
            AppConfig config = ConfigLoader.load();


            // 2) Itt jön majd a számgenerálás modul
            NumberGenerator generator = new NumberGenerator(config);
            List<Integer> numbers = generator.generate();

            // 3) Oldalak és sorok formázása
            PdfPageFormatter formatter = new PdfPageFormatter(config);
            List<List<String>> pages = formatter.format(numbers);


            // 4) Itt jön majd a PDF generálás modul

            PdfCreator pdfCreator = new PdfCreator(config);
            pdfCreator.createPdf(pages);
            System.out.println("A program sikeresen lefutott.");

        } catch (AppException e) {
            // Minden saját kivétel ide fut be
            ErrorHandler.handle(e);
        }
    }
}