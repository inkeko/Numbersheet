package hu.csaba.numbersheet.error;

public class AppException extends RuntimeException {

    private final String userMessage;
    private final String errorCode;

    public AppException(String message) {
        super(message);
        this.userMessage = "Ismeretlen hiba történt.";
        this.errorCode = null;
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
        this.userMessage = "Ismeretlen hiba történt.";
        this.errorCode = null;
    }

    public AppException(String message, String userMessage) {
        super(message);
        this.userMessage = userMessage;
        this.errorCode = null;
    }

    public AppException(String message, String userMessage, String errorCode) {
        super(message);
        this.userMessage = userMessage;
        this.errorCode = errorCode;
    }

    public AppException(String message, String userMessage, String errorCode, Throwable cause) {
        super(message, cause);
        this.userMessage = userMessage;
        this.errorCode = errorCode;
    }

    public String getUserMessage() {
        return userMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }
}