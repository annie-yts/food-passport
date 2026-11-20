package interface_adapter;

import use_case.DiscoverDishesInputBoundary;

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
