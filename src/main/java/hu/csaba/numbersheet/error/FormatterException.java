package hu.csaba.numbersheet.error;

public class FormatterException extends AppException {
    public FormatterException(String message,
                              String userMessage,
                              String errorCode) {
        super(message, userMessage, errorCode);
    }

    public FormatterException(String message,
                              String userMessage,
                              String errorCode,
                              Throwable cause) {
        super(message, userMessage, errorCode, cause);
    }


}
