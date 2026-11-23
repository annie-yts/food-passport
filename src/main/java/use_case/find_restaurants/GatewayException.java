package use_case.find_restaurants;

public class GatewayException extends Exception {
    public GatewayException(String message, Throwable cause) {
        super(message, cause);
    }
}
