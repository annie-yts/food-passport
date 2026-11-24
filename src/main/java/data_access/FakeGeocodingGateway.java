package data_access;

import use_case.find_restaurants.Coordinates;
import use_case.find_restaurants.GatewayException;
import use_case.find_restaurants.GeocodingGateway;

import java.util.Optional;

public class FakeGeocodingGateway implements GeocodingGateway {
    @Override
    public Optional<Coordinates> geocode(String address) throws GatewayException {
        if (address == null || address.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(new Coordinates(43.6532, -79.3832)); // downtown Toronto
    }
}
