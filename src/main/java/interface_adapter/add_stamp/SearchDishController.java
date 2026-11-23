package interface_adapter.add_stamp;

import use_case.search_dish.SearchDishInputBoundary;

/**
 * Takes what the user typed in the search box and passes it to the Search Dish use case.
 */
public class SearchDishController {
    private final SearchDishInputBoundary interactor;

    /**
     * Creates the controller.
     *
     * @param interactor the Search Dish use case
     */
    public SearchDishController(SearchDishInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Asks the use case to search for dishes by name.
     *
     * @param query what the user typed in the search box
     */
    public void search(String query) {
        interactor.execute(query);
    }
}