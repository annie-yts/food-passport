package interface_adapter;

/**
 * The state for the DiscoverDishesViewModel.
 */
public class DiscoverDishesState {
    private String searchTarget;
    private int page;

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

    @Override
    public String toString() {
        return "SignupState{"
                + "searchTarget='" + searchTarget + '\''
                + ", page='" + page + '\''
                + '}';
    }
}
