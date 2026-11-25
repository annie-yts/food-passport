package interface_adapter.find_restaurants;

import use_case.find_restaurants.FindRestaurantsOutputBoundary;
import use_case.find_restaurants.FindRestaurantsOutputData;

import java.util.ArrayList;
import java.util.List;

public class FindRestaurantsPresenter implements FindRestaurantsOutputBoundary {
    private final FindRestaurantsViewModel viewModel;

    public FindRestaurantsPresenter(FindRestaurantsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(FindRestaurantsOutputData outputData) {
        List<String> lines = new ArrayList<>();
        for (FindRestaurantsOutputData.RestaurantResult r : outputData.results()) {
            lines.add(String.format("%s - %.1f km - %s",
                    r.name(), r.distanceKm(), r.address()));
        }
        FindRestaurantsState state = viewModel.getState();
        state.setResultLines(lines);
        state.setErrorMessage(lines.isEmpty() ? "No restaurants found nearby." : "");
        viewModel.notifyStateChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        FindRestaurantsState state = viewModel.getState();
        state.setResultLines(new ArrayList<>());
        state.setErrorMessage(errorMessage);
        viewModel.notifyStateChanged();
    }
}
