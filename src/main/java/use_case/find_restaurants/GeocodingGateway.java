package use_case.find_restaurants;

import java.util.Optional;

public interface GeocodingGateway {
    /** Returns empty if the address can't be found. */
    Optional<Coordinates> geocode(String address) throws GatewayException;
}
