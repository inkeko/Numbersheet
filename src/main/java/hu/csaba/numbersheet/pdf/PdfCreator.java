package hu.csaba.numbersheet.pdf;

import hu.csaba.numbersheet.config.AppConfig;
import hu.csaba.numbersheet.error.PdfException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static util.funcional.FunctionalQueries.*;

public class PdfCreator {

    private final AppConfig config;

    public PdfCreator(AppConfig config) {
        this.config = config;
    }

    /**
     * A formázott oldalak alapján létrehozza a PDF dokumentumot.
     */
    public void createPdf(List<List<String>> pages) throws PdfException {

        String outputDir = config.getOutputDirectory();
        String fileName = config.getPdfFileName();
        int fontSize = config.getFontSize();

        // 1) Kimeneti mappa ellenőrzése
        File directory = new File(outputDir);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new PdfException(
                    "A kimeneti mappa nem hozható létre: " + outputDir,
                    "PDF mentési hiba",
                    "PDF-001"
            );
        }

        File outputFile = new File(directory, fileName);

        // 2) PDF létrehozása
        try (PDDocument document = new PDDocument()) {

            for (List<String> pageLines : pages) {

                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream content =
                             new PDPageContentStream(document, page)) {

                    content.setFont(
                            new PDType1Font(Standard14Fonts.FontName.HELVETICA),
                            fontSize
                    );

                    content.beginText();

                    float startX = 50;
                    float startY = page.getMediaBox().getHeight() - 50;
                    float lineSpacing = fontSize + 4;

                    content.newLineAtOffset(startX, startY);

                    for (String line : pageLines) {
                        content.showText(line);
                        content.newLineAtOffset(0, -lineSpacing);
                    }

                    content.endText();
                }
            }

            document.save(outputFile);

        } catch (IOException e) {
            throw new PdfException(
                    "Hiba történt a PDF létrehozása során: " + e.getMessage(),
                    "PDF generálási hiba",
                    "PDF-002",
                    e
            );
        }
    }

    // a pdf minden oldalán meg jeleni a fejléc, és a lábjegyzet közte meg atartalom

    //fejléc
    private void drawHeader(PDPageContentStream content, PDPage page) throws IOException {
        float pageWidth = page.getMediaBox().getWidth();
        float topY = page.getMediaBox().getHeight() - 40;

        // Cím
        content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 16);
        content.beginText();
        content.newLineAtOffset(50, topY);
        content.showText("NumberSheet Report");
        content.endText();

        // Dátum
        content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
        content.beginText();
        content.newLineAtOffset(50, topY - 20);
        content.showText("Generated: " + java.time.LocalDate.now());
        content.endText();

        // Vízszintes elválasztó vonal
        content.setLineWidth(0.5f);
        content.moveTo(40, topY - 30);
        content.lineTo(pageWidth - 40, topY - 30);
        content.stroke();
    }

    //lábléc
    private void drawFooter(PDPageContentStream content, PDPage page, int pageNumber) throws IOException {
        float pageWidth = page.getMediaBox().getWidth();

        // Vízszintes elválasztó vonal
        content.setLineWidth(0.5f);
        content.moveTo(40, 50);
        content.lineTo(pageWidth - 40, 50);
        content.stroke();

        // Oldalszám
        content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), 10);
        content.beginText();
        content.newLineAtOffset(50, 35);
        content.showText("Page " + pageNumber);
        content.endText();

        // Dátum
        content.beginText();
        content.newLineAtOffset(pageWidth - 150, 35);
        content.showText("Generated: " + java.time.LocalDate.now());
        content.endText();
    }

    //tartalom megjelenitése
    private int drawContent(
            PDPageContentStream content,
            PDPage page,
            List<String> lines,
            int startIndex,
            float startY
    ) throws IOException {

        float y = startY;
        float leftMargin = 50;
        float lineHeight = 15;

        int index = startIndex;

        while (index < lines.size()) {
            if (y < 70) break; // nincs több hely az oldalon

            String line = lines.get(index);

            // tördelés hosszú sorokra
            List<String> wrapped = wrapText(line, 90);

            for (String w : wrapped) {
                if (y < 70) break;
                writeLine(content, leftMargin, y, w);
                y -= lineHeight;
            }

            index++;
        }

        return index; // ennyi sort rajzoltunk ki
    }
    //segitmetődus 1  - sor kiirása

    private void writeLine(PDPageContentStream content, float x, float y, String text) throws IOException {
        content.beginText();
        content.newLineAtOffset(x, y);
        content.showText(text);
        content.endText();
    }

    //wraptext segédprogram
    private List<String> wrapText(String text, int maxCharsPerLine) {
        List<String> lines = new ArrayList<>();

        while (text.length() > maxCharsPerLine) {
            int breakIndex = text.lastIndexOf(" ", maxCharsPerLine);
            if (breakIndex == -1) {
                breakIndex = maxCharsPerLine;
            }

            lines.add(text.substring(0, breakIndex));
            text = text.substring(breakIndex).trim();
        }

        if (!text.isEmpty()) {
            lines.add(text);
        }

        return lines;
    }


    //először sorokká alakítjuk a tartalmat
    private List<String> buildContentLines(List<Integer> numbers) {
        List<String> lines = new ArrayList<>();

        lines.add("Lekérdezések eredményei:");
        lines.add("");

        // --- Szűrések ---
        lines.add("Páros számok: ");
        lines.add(filter(numbers, IS_EVEN).toString());
        lines.add(""); // üres sor

        lines.add("Páratlan számok: ");
        lines.add(filter(numbers, IS_ODD).toString());
        lines.add("");// üres sor


        lines.add("Nagyobb mint 10: " );
        lines.add(filter(numbers, greaterThan(10)).toString());
        lines.add(""); // üres sor

        lines.add("5 és 20 között: " + filter(numbers, between(5, 20)));
        lines.add("");

        // --- Statisztikák ---
        lines.add("Összeg: " + sum(numbers));
        lines.add("Átlag: " + average(numbers));
        lines.add("Minimum: " + min(numbers));
        lines.add("Maximum: " + max(numbers));
        lines.add("");

        // --- BigInteger ---
        lines.add("BigInteger szorzat: " + productBig(numbers));
        lines.add("");

        return lines;
    }

    // több oldalad pdf legyártása
    public void queriesCreatePdf(List<Integer> numbers, String outputPath) {
        try (PDDocument document = new PDDocument()) {

            float marginTop = 100;

            List<String> lines = buildContentLines(numbers);
            int index = 0;
            int pageNumber = 1;

            while (index < lines.size()) {

                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream content = new PDPageContentStream(document, page)) {

                    drawHeader(content, page);

                    float startY = page.getMediaBox().getHeight() - marginTop;

                    index = drawContent(content, page, lines, index, startY);

                    drawFooter(content, page, pageNumber);
                }

                pageNumber++;
            }

            document.save(outputPath);

        } catch (IOException e) {
            throw new RuntimeException("PDF generation failed", e);
        }

        System.out.println("PDF sikeresen mentve ide: " + outputPath);
    }

}