package use_case.find_restaurants;

import entities.Restaurant;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FindRestaurantsInteractorTest {

    private static class RecordingPresenter implements FindRestaurantsOutputBoundary {
        FindRestaurantsOutputData success;
        String error;

        @Override
        public void prepareSuccessView(FindRestaurantsOutputData outputData) {
            success = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            error = errorMessage;
        }
    }

    private static final CountryMappingGateway JAPAN = iso -> Set.of("japanese");
    private static final GeocodingGateway FOUND = address -> Optional.of(new Coordinates(43.65, -79.38));

    private static Restaurant restaurant(String name, double lat, double lon) {
        return new Restaurant(name, lat, lon, List.of("japanese"), "1 Test St");
    }

    @Test
    void blankAddressFails() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new FindRestaurantsInteractor(JAPAN, FOUND, (c, r, t) -> List.of(), presenter)
                .execute(new FindRestaurantsInputData("JP", " ", 2));
        assertEquals("Address cannot be empty.", presenter.error);
    }

    @Test
    void badRadiusFails() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final FindRestaurantsInteractor interactor =
                new FindRestaurantsInteractor(JAPAN, FOUND, (c, r, t) -> List.of(), presenter);
        interactor.execute(new FindRestaurantsInputData("JP", "x", 0));
        assertEquals("Radius must be greater than 0.", presenter.error);
        interactor.execute(new FindRestaurantsInputData("JP", "x", 51));
        assertEquals("Radius cannot be more than 50km.", presenter.error);
    }

    @Test
    void unsupportedCountryFails() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new FindRestaurantsInteractor(iso -> Set.of(), FOUND, (c, r, t) -> List.of(), presenter)
                .execute(new FindRestaurantsInputData("ZZ", "x", 2));
        assertEquals("Nearby search isn't available for this country.", presenter.error);
    }

    @Test
    void addressNotFoundFails() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new FindRestaurantsInteractor(JAPAN, address -> Optional.empty(), (c, r, t) -> List.of(), presenter)
                .execute(new FindRestaurantsInputData("JP", "nowhere", 2));
        assertEquals("Address not found.", presenter.error);
    }

    @Test
    void gatewayExceptionFails() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final PlacesGateway broken = (c, r, t) -> {
            throw new GatewayException("boom", null);
        };
        new FindRestaurantsInteractor(JAPAN, FOUND, broken, presenter)
                .execute(new FindRestaurantsInputData("JP", "x", 2));
        assertEquals("Couldn't reach the map service. Please try again later.", presenter.error);
    }

    @Test
    void noResultsFails() {
        final RecordingPresenter presenter = new RecordingPresenter();
        new FindRestaurantsInteractor(JAPAN, FOUND, (c, r, t) -> List.of(), presenter)
                .execute(new FindRestaurantsInputData("JP", "x", 2));
        assertEquals("No matching restaurants found. Try increasing the radius.", presenter.error);
    }

    @Test
    void successSortsByDistance() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final PlacesGateway places = (c, r, t) -> List.of(
                restaurant("Far", 43.70, -79.38),
                restaurant("Near", 43.651, -79.38));
        new FindRestaurantsInteractor(JAPAN, FOUND, places, presenter)
                .execute(new FindRestaurantsInputData("JP", "x", 10));

        assertNull(presenter.error);
        final List<FindRestaurantsOutputData.RestaurantResult> results = presenter.success.results();
        assertEquals(2, results.size());
        assertEquals("Near", results.get(0).name());
        assertEquals("Far", results.get(1).name());
        assertTrue(results.get(0).distanceKm() < results.get(1).distanceKm());
        assertEquals(List.of("japanese"), results.get(0).matchingTags());
    }
}
