package interface_adapter.discover_dishes;

/**
 * The state for the DiscoverDishesViewModel.
 */
public class DiscoverDishesState {
    private String searchTarget = ""; // initialize as "" to prevent null
    private Boolean isSearchByDish = false; // initialize as false to prevent null
    private int page = 1;

    public String getSearchTarget() {
        return searchTarget;
    }

    public void setSearchTarget(String searchTarget) {
        this.searchTarget = searchTarget;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public Boolean getSearchByDish() {
        return isSearchByDish;
    }

    public void setSearchByDish(Boolean searchByDish) {
        isSearchByDish = searchByDish;
    }

    @Override
    public String toString() {
        return "SignupState{"
                + "searchTarget='" + searchTarget + '\''
                + ", page='" + page + '\''
                + '}';
    }
}
