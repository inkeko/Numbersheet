package hu.csaba.numbersheet.error;

public class PdfException extends AppException{
    public PdfException(String message) {
        super(message, "PDF generálási hiba történt.");
    }

    public PdfException(String message, String userMessage) {
        super(message, userMessage);
    }

    public PdfException(String message, String userMessage, String errorCode) {
        super(message, userMessage, errorCode);
    }

    public PdfException(String message, String userMessage, String errorCode, Throwable cause) {
        super(message, userMessage, errorCode, cause);
    }
}
