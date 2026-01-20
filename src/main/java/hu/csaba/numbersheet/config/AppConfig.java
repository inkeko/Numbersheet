package hu.csaba.numbersheet.config;

public class AppConfig {

    private final String outputDirectory;
    private final String pdfFileName;
    private final int numbersCount;
    private final int numbersMin;
    private final int numbersMax;
    private final int maxLine;
    private final int maxChars;
    private final String fontName;
    private final int fontSize;

    public AppConfig(String outputDirectory,
                     String pdfFileName,
                     int numbersCount,
                     int numbersMin,
                     int numbersMax,
                     int maxLine,
                     int maxChars,
                     String fontName,
                    int fontSize) {

        this.outputDirectory = outputDirectory;
        this.pdfFileName = pdfFileName;
        this.numbersCount = numbersCount;
        this.numbersMin = numbersMin;
        this.numbersMax = numbersMax;
        this.maxLine = maxLine;
        this.maxChars = maxChars;
        this.fontName = fontName;
        this.fontSize = fontSize;
    }

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public String getPdfFileName() {
        return pdfFileName;
    }

    public int getNumbersCount() {
        return numbersCount;
    }

    public int getNumbersMin() {
        return numbersMin;
    }

    public int getNumbersMax() {
        return numbersMax;
    }

    public int getMaxLine() {
        return maxLine;
    }

    public int getMaxChars() {
        return maxChars;
    }

    public String getFontName() {
        return fontName;
    }

    public int getFontSize() {
        return fontSize;
    }
}
