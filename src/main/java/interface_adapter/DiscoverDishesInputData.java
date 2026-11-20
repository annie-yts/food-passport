package interface_adapter;

public class DiscoverDishesInputData {
    private final String searchTarget;
    private final Boolean isSearchByDish;
    private final int page;

    public DiscoverDishesInputData (String searchTarget, Boolean isSearchByDish, int page){
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
