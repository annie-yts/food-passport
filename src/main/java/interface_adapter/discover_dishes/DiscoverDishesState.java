package interface_adapter.discover_dishes;

import javax.swing.*;

/**
 * The state for the DiscoverDishesViewModel.
 */
public class DiscoverDishesState {
    private String searchTarget = ""; // initialize as "" to prevent null
    private Boolean isSearchByDish = false; // initialize as false to prevent null
    private int page = 1;
    private final JPanel[] currentDishPanel = new JPanel[6];

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

    public JPanel[] getCurrentDish() {
        return currentDishPanel;
    }

    public void setCurrentDish(int index, JPanel currentDish) {
        this.currentDishPanel[index] = currentDish;
    }

    public void setCurrentDish(int index) {
        this.currentDishPanel[index] = null;
    }

    @Override
    public String toString() {
        return "SignupState{"
                + "searchTarget='" + searchTarget + '\''
                + ", page='" + page + '\''
                + '}';
    }
}
