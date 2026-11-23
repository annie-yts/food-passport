package use_case.search_dish;

/**
 * Input boundary for searching TheMealDB for dishes by name.
 */
public interface SearchDishInputBoundary {

    /**
     * Searches for dishes whose name matches the query.
     *
     * @param query what the user typed in the search box
     */
    void execute(String query);
}