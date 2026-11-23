package use_case.find_restaurants;

import Entities.Restaurant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class FindRestaurantsInteractor implements FindRestaurantsInputBoundary {
    private final CountryMappingGateway mapping;
    private final GeocodingGateway geocoder;
    private final PlacesGateway places;
    private final FindRestaurantsOutputBoundary presenter;

    public FindRestaurantsInteractor(CountryMappingGateway mapping, GeocodingGateway geocoder, PlacesGateway places,
                                     FindRestaurantsOutputBoundary presenter) {
        this.mapping = mapping;
        this.geocoder = geocoder;
        this.places = places;
        this.presenter = presenter;
    }

    @Override
    public void execute(FindRestaurantsInputData in) {
        if (in.address() == null || in.address().isBlank()) {
            presenter.prepareFailView("Address cannot be empty.");
            return;
        }
        if (in.radiusKm() <= 0) {
            presenter.prepareFailView("Radius must be greater than 0.");
            return;
        }

        Set<String> countryTags = mapping.osmTagsFor(in.isoCode());
        if (countryTags.isEmpty()) {
            presenter.prepareFailView("Nearby search isn't available for this country.");
            return;
        }

        try {
            Optional<Coordinates> center = geocoder.geocode(in.address());
            if (center.isEmpty()) {
                presenter.prepareFailView("Address not found.");
                return;
            }

            List<Restaurant> found = places.findNearby(center.get(), in.radiusKm(), countryTags);
            List<FindRestaurantsOutputData.RestaurantResult> results = new ArrayList<>();
            for (Restaurant r : found) {
                List<String> matching = r.getCuisineTags().stream()
                        .filter(countryTags::contains)
                        .collect(Collectors.toList());
                double dist = r.distanceTo(center.get().latitude(), center.get().longitude());
                results.add(new FindRestaurantsOutputData.RestaurantResult(
                        r.getName(), r.getAddress(), matching, dist));
            }
            results.sort(Comparator.comparingDouble(
                    FindRestaurantsOutputData.RestaurantResult::distanceKm));

            if (results.isEmpty()) {
                presenter.prepareFailView("No matching restaurants found. Try increasing the radius.");
            } else {
                presenter.prepareSuccessView(new FindRestaurantsOutputData(results));
            }
        } catch (GatewayException e) {
            presenter.prepareFailView("Couldn't reach the map service. Please try again later.");
        }
    }
}
