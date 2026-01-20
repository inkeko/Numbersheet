package hu.csaba.numbersheet.error;

public class NumberGenerationException extends AppException{
    public NumberGenerationException(String message) {
        super(message, "Számgenerálási hiba történt.");
    }

    public NumberGenerationException(String message, String userMessage) {
        super(message, userMessage);
    }

    public NumberGenerationException(String message, String userMessage, String errorCode) {
        super(message, userMessage, errorCode);
    }

    public NumberGenerationException(String message, String userMessage, String errorCode, Throwable cause) {
        super(message, userMessage, errorCode, cause);
    }


}
