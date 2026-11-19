package data_access;

public class ApiException extends RuntimeException {
    public ApiException(String message, Throwable exception) {
        super(message, exception);
    }

}
