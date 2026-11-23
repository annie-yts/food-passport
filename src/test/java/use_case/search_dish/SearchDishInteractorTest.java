package use_case.search_dish;

import Entities.Dish;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import use_case.discover_dishes.ApiAccessBoundary;
import use_case.discover_dishes.ApiException;
import use_case.stamp_dish.CountryListDataAccessInterface;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchDishInteractorTest {

    /** Pretends to be TheMealDB. Returns whatever dishes the test sets, or throws. */
    static class FakeApi implements ApiAccessBoundary {
        Dish[] dishesToReturn = new Dish[0];
        boolean shouldFail = false;
        String lastQuery;
        Boolean lastSearchByDish;
        int calls = 0;

        @Override
        public String[] getCountry() {
            return new String[0];
        }

        @Override
        public Dish[] getDish(String target, Boolean isSearchByDish) {
            calls++;
            lastQuery = target;
            lastSearchByDish = isSearchByDish;
            if (shouldFail) {
                throw new ApiException("network down", null);
            }
            return dishesToReturn;
        }
    }

    /** Only knows two countries. */
    static class FakeCountryList implements CountryListDataAccessInterface {
        @Override
        public List<String> getAllCountries() {
            return List.of("Thailand", "Spain");
        }
    }

    /** Remembers which view the interactor asked for. */
    static class FakePresenter implements SearchDishOutputBoundary {
        SearchDishOutputData lastSuccess;
        String noResultsQuery;
        String apiUnavailableMessage;

        @Override
        public void prepareSuccessView(SearchDishOutputData outputData) {
            lastSuccess = outputData;
        }

        @Override
        public void prepareNoResultsView(String query) {
            noResultsQuery = query;
        }

        @Override
        public void prepareApiUnavailableView(String message) {
            apiUnavailableMessage = message;
        }
    }

    private FakeApi api;
    private FakePresenter presenter;
    private SearchDishInteractor interactor;

    @BeforeEach
    void setUp() {
        api = new FakeApi();
        presenter = new FakePresenter();
        interactor = new SearchDishInteractor(api, new FakeCountryList(), presenter);
    }

    /** Makes a dish the way Andy's client would. */
    private Dish dish(String id, String name, String country) {
        return new Dish(id, name, country, "Seafood", "http://img", "", false);
    }

    @Test
    void knownCountryIsKept() {
        api.dishesToReturn = new Dish[] {dish("53191", "Pad Thai", "Thailand")};

        interactor.execute("pad");

        SearchDishOutputData.Suggestion s = presenter.lastSuccess.getSuggestions().get(0);
        assertEquals("53191", s.getDishId());
        assertEquals("Pad Thai", s.getDishName());
        assertEquals("Thailand", s.getCountry());
    }

    @Test
    void unknownCountryBecomesNull() {
        api.dishesToReturn = new Dish[] {dish("1", "Mystery Stew", "Atlantis")};

        interactor.execute("stew");

        assertNull(presenter.lastSuccess.getSuggestions().get(0).getCountry());
    }

    @Test
    void mixOfKnownAndUnknownCountries() {
        api.dishesToReturn = new Dish[] {
                dish("1", "Pad Thai", "Thailand"),
                dish("2", "Mystery Stew", "Atlantis"),
                dish("3", "Padron peppers", "Spain")
        };

        interactor.execute("pad");

        List<SearchDishOutputData.Suggestion> results = presenter.lastSuccess.getSuggestions();
        assertEquals(3, results.size());
        assertEquals("Thailand", results.get(0).getCountry());
        assertNull(results.get(1).getCountry());
        assertEquals("Spain", results.get(2).getCountry());
    }

    @Test
    void noResultsShowsNoResultsView() {
        api.dishesToReturn = new Dish[0];

        interactor.execute("zzzz");

        assertEquals("zzzz", presenter.noResultsQuery);
        assertNull(presenter.lastSuccess);
    }

    @Test
    void apiFailureShowsUnavailableView() {
        api.shouldFail = true;

        interactor.execute("pad");

        assertNotNull(presenter.apiUnavailableMessage);
        assertNull(presenter.lastSuccess);
    }

    @Test
    void blankQueryDoesNotCallApi() {
        interactor.execute("   ");

        assertEquals(0, api.calls);
        assertNull(presenter.lastSuccess);
    }

    @Test
    void queryIsTrimmedAndSearchesByDish() {
        api.dishesToReturn = new Dish[] {dish("53191", "Pad Thai", "Thailand")};

        interactor.execute("  pad  ");

        assertEquals("pad", api.lastQuery);
        assertTrue(api.lastSearchByDish);
    }
}