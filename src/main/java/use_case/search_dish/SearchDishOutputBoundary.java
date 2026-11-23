package use_case.search_dish;

/**
 * Output boundary for the Search Dish use case.
 */
public interface SearchDishOutputBoundary {

    /**
     * Shows the matching dishes.
     *
     * @param outputData the suggestions to show
     */
    void prepareSuccessView(SearchDishOutputData outputData);

    /**
     * Tells the user nothing matched, so they can enter the dish manually.
     *
     * @param query what the user searched for
     */
    void prepareNoResultsView(String query);

    /**
     * Tells the user the API couldn't be reached, so they can enter the dish manually.
     *
     * @param message the message to show
     */
    void prepareApiUnavailableView(String message);
}