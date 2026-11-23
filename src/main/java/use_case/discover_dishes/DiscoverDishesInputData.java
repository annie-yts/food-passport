package use_case.discover_dishes;

public class DiscoverDishesInputData {
    // A dish name for a name search, or an ISO alpha-3 country code for a country search.
    private final String searchTarget;
    private final Boolean isSearchByDish;
    private final int page;

    public DiscoverDishesInputData(String searchTarget, Boolean isSearchByDish, int page) {
        this.searchTarget = searchTarget;
        this.isSearchByDish = isSearchByDish;
        this.page = page;
    }

    public String getSearchTarget() {
        return searchTarget;
    }


    public int getPage() {
        return page;
    }

    public Boolean getSearchByDish() {
        return isSearchByDish;
    }

}
