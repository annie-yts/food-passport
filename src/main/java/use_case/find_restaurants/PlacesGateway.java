package use_case.find_restaurants;

import Entities.Restaurant;
import java.util.List;
import java.util.Set;

public interface PlacesGateway {
    List<Restaurant> findNearby(Coordinates center, double radiusKm, Set<String> cuisineTags)
        throws GatewayException;
}
