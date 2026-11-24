package data_access;

import entities.Restaurant;
import use_case.find_restaurants.Coordinates;
import use_case.find_restaurants.GatewayException;
import use_case.find_restaurants.PlacesGateway;

import java.util.List;
import java.util.Set;

public class FakePlacesGateway implements PlacesGateway {
    @Override
    public List<Restaurant> findNearby(Coordinates center, double radiusKm, Set<String> cuisineTags)
        throws GatewayException {
        return List.of(
                new Restaurant("Test Sushi", 43.6540, -79.3800, List.of("japanese"), "1 Example St"),
                new Restaurant("Test Ramen", 43.6600, -79.3900, List.of("japanese"), "2 Example Ave"));
    }
}
