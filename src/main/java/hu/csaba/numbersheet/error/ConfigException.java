package hu.csaba.numbersheet.error;

public class ConfigException extends AppException{

    public ConfigException(String message) {
        super(message, "Konfigurációs hiba történt.");
    }

    public ConfigException(String message, String userMessage) {
        super(message, userMessage);
    }

    public ConfigException(String message, String userMessage, String errorCode) {
        super(message, userMessage, errorCode);
    }

    public ConfigException(String message, String userMessage, String errorCode, Throwable cause) {
        super(message, userMessage, errorCode, cause);
    }


}
