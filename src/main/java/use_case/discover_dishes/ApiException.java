package use_case.discover_dishes;

public class ApiException extends RuntimeException {
    public ApiException(String message, Throwable exception) {
        super(message, exception);
    }

}
