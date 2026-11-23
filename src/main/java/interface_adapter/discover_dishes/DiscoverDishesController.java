package interface_adapter.discover_dishes;

import use_case.discover_dishes.DiscoverDishesInputBoundary;
import use_case.discover_dishes.DiscoverDishesInputData;

public class DiscoverDishesController {

    private final DiscoverDishesInputBoundary discoverDishesInteractor;

    public DiscoverDishesController(DiscoverDishesInputBoundary discoverDishesInteractor) {
        this.discoverDishesInteractor = discoverDishesInteractor;
    }

    public void execute(String searchTarget, Boolean isSearchByDish, int page) {
        final DiscoverDishesInputData discoverDishesInputData =
                new DiscoverDishesInputData(searchTarget, isSearchByDish, page);
        discoverDishesInteractor.execute(discoverDishesInputData);
    }

}
