package hu.csaba.numbersheet.pdf;

import hu.csaba.numbersheet.config.AppConfig;

import java.util.ArrayList;
import java.util.List;

public class PdfPageFormatter {

    private final AppConfig config;

    public PdfPageFormatter(AppConfig config) {
        this.config = config;
    }

    /**
     * A generált számokat oldalakra és sorokra bontja.
     * @param numbers A generált számok listája
     * @return Oldalak listája, ahol minden oldal sorok listáját tartalmazza
     */
    public List<List<String>> format(List<Integer> numbers) {

        int maxLines = config.getMaxLine();
        int maxChars = config.getMaxChars();

        List<List<String>> pages = new ArrayList<>();
        List<String> currentPage = new ArrayList<>();

        StringBuilder currentLine = new StringBuilder();

        for (Integer num : numbers) {

            String value = num.toString();

            // Ha a sor túl hosszú lenne, sortörés
            if (currentLine.length() + value.length() + 1 > maxChars) {
                currentPage.add(currentLine.toString());
                currentLine = new StringBuilder();
            }

            // Ha az oldal megtelt, új oldal
            if (currentPage.size() >= maxLines) {
                pages.add(currentPage);
                currentPage = new ArrayList<>();
            }

            // Hozzáfűzzük a számot a sorhoz
            if (currentLine.length() > 0) {
                currentLine.append(", ");
            }
            currentLine.append(value);
        }

        // Utolsó sor hozzáadása
        if (currentLine.length() > 0) {
            currentPage.add(currentLine.toString());
        }

        // Utolsó oldal hozzáadása
        if (!currentPage.isEmpty()) {
            pages.add(currentPage);
        }

        return pages;
    }
}