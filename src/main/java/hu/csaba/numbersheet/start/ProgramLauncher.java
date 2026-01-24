package hu.csaba.numbersheet.start;

import hu.csaba.numbersheet.config.AppConfig;
import hu.csaba.numbersheet.config.ConfigLoader;
import hu.csaba.numbersheet.error.AppException;
import hu.csaba.numbersheet.error.ErrorHandler;
import hu.csaba.numbersheet.generator.NumberGenerator;
import hu.csaba.numbersheet.pdf.PdfCreator;
import hu.csaba.numbersheet.pdf.PdfPageFormatter;

// azért kell az import static... hog yig ytudjunk hivatkozni : filter(numbers, IS_EVEN)
import static util.funcional.FunctionalQueries.*;

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

            //Lekérdezések

            System.out.println("Generated numbers: " + numbers);
            System.out.println("--------------------------------------------------");

// Predicate alapú szűrések
            System.out.println("Even(páros) numbers: " + filter(numbers, IS_EVEN));
            System.out.println("Odd(páratlan) numbers: " + filter(numbers, IS_ODD));
            System.out.println("Greater( nagyobb mint 10) than 10: " + filter(numbers, greaterThan(10)));
            System.out.println("Between(között) 5 and 20: " + filter(numbers, between(5, 20)));

            System.out.println("--------------------------------------------------");

// Function alapú átalakítások
            System.out.println("Squares(nyégyzet): " + map(numbers, SQUARE));
            System.out.println("Doubles: " + map(numbers, DOUBLE));
            System.out.println("Add 5 to each(agyunkhozzá 5-öt ,mindhez): " + map(numbers, add(5)));

            System.out.println("--------------------------------------------------");

// Reduce műveletek
            System.out.println("Sum (reduce)(öszeg csökkent): " + reduce(numbers, 0, (a, b) -> a + b));
            System.out.println("Product (reduce): " + reduce(numbers, 1, (a, b) -> a * b));
            System.out.println("Product (BigInteger)nagy számokat kezeli): " + productBig(numbers));


            System.out.println("--------------------------------------------------");

// Top N, distinct, rendezések
            System.out.println("Top 5: " + topN(numbers, 5));
            System.out.println("Distinct: " + distinct(numbers));
            System.out.println("Sorted ASC: " + sortedAsc(numbers));
            System.out.println("Sorted DESC: " + sortedDesc(numbers));

            System.out.println("--------------------------------------------------");

// GroupingBy és PartitioningBy
            System.out.println("Group by even/odd(Csoportosítás páros/páratlan szerint): " +
                    groupBy(numbers, n -> n % 2 == 0 ? "even" : "odd"));

            System.out.println("Partition by > 10: " +
                    partitionBy(numbers, greaterThan(10)));

            System.out.println("--------------------------------------------------");

// Statisztikai lekérdezések
            System.out.println("Min: " + min(numbers));
            System.out.println("Max: " + max(numbers));
            System.out.println("Sum: " + sum(numbers));
            System.out.println("Average: " + average(numbers));

        } catch (AppException e) {
            // Minden saját kivétel ide fut be
            ErrorHandler.handle(e);
        }
    }
}