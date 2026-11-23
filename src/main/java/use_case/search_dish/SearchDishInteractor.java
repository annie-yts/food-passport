package use_case.search_dish;

import Entities.Dish;
import use_case.discover_dishes.ApiAccessBoundary;
import use_case.discover_dishes.ApiException;
import use_case.stamp_dish.CountryListDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Interactor for the Search Dish use case. Searches TheMealDB by dish name
 * and marks results whose country isn't in our list so the user can pick one.
 */
public class SearchDishInteractor implements SearchDishInputBoundary {
    private final ApiAccessBoundary api;
    private final CountryListDataAccessInterface countryList;
    private final SearchDishOutputBoundary presenter;

    /**
     * Creates the interactor.
     *
     * @param api searches TheMealDB
     * @param countryList the list of valid countries
     * @param presenter receives the search results
     */
    public SearchDishInteractor(ApiAccessBoundary api, CountryListDataAccessInterface countryList,
                                SearchDishOutputBoundary presenter) {
        this.api = api;
        this.countryList = countryList;
        this.presenter = presenter;
    }

    @Override
    public void execute(String query) {
        if  (query == null || query.isBlank()) {
            presenter.prepareNoResultsView(query);
            return;
        }
        Dish[] dishes;
        try {
            dishes = api.getDish(query.trim(), true);
        } catch (ApiException e) {
            presenter.prepareApiUnavailableView("Couldn't reach TheMealDB. Enter the dish manually.");
            return;
        }
        if (dishes.length == 0) {
            presenter.prepareNoResultsView(query);
            return;
        }
        List<SearchDishOutputData.Suggestion> suggestions = new ArrayList<>();
        for (Dish dish : dishes) {
            String country;
            if (countryList.getAllCountries().contains(dish.getCountry())) {
                country = dish.getCountry();
            } else {
                country = null;
            }
            suggestions.add(new SearchDishOutputData.Suggestion(dish.getId(), dish.getName(), country,
                    dish.getCategory(), dish.getThumbnailUrl()));
        }
        presenter.prepareSuccessView(new SearchDishOutputData(suggestions));
    }
}
