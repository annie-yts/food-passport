package interface_adapter.find_restaurants;

import use_case.find_restaurants.FindRestaurantsInputBoundary;
import use_case.find_restaurants.FindRestaurantsInputData;

public class FindRestaurantsController {
    private final FindRestaurantsInputBoundary interactor;

    public FindRestaurantsController(FindRestaurantsInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String isoCode, String address, double radiusKm) {
        interactor.execute(new FindRestaurantsInputData(isoCode, address, radiusKm));
    }
}
