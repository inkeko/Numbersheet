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
import java.util.List;

public class PdfCreator {

    private final AppConfig config;

    public PdfCreator(AppConfig config) {
        this.config = config;
    }

    /**
     * A formázott oldalak alapján létrehozza a PDF dokumentumot.
     */
    public void createPdf(List<List<String>> pages) {

        String outputDir = config.getOutputDirectory();
        String fileName = config.getPdfFileName();
        int fontSize = config.getFontSize();

        File directory = new File(outputDir);
        if (!directory.exists() && !directory.mkdirs()) {
            throw new PdfException(
                    "A kimeneti mappa nem hozható létre: " + outputDir,
                    "PDF mentési hiba",
                    "PDF-001"
            );
        }

        File outputFile = new File(directory, fileName);

        try (PDDocument document = new PDDocument()) {

            for (List<String> pageLines : pages) {

                PDPage page = new PDPage();
                document.addPage(page);

                try (PDPageContentStream content = new PDPageContentStream(document, page)) {

                    content.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA), fontSize);
                    content.beginText();

                    // Kiindulási pozíció (PDF koordinátarendszer: bal alsó sarok = 0,0)
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
                    "PDF-002"
            );
        }
    }
}
