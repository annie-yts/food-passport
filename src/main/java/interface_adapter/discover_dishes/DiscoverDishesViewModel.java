/**
 * The View Model for DiscoverDishesView
 */
package interface_adapter.discover_dishes;

import java.util.ArrayList;
import java.util.List;

public class DiscoverDishesViewModel {
    public static final String TITLE_LABEL = "Discover Dishes";
    public static final String PREV_PAGE_LABEL = "Prev";
    public static final String NEXT_PAGE_LABEL = "Next";
    public static final String BUCKET_LIST_LABEL = "View my bucket list";
    public static final String NEARBY_RESTAURANT_LABEL = "Find restaurants nearby";
    public static final String COUNTRY_LABEL = "Search by country";
    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String DISH_INPUT_LABEL = "or dish:";

    private final List<DiscoverDishesViewModelListener> listeners = new ArrayList<>();

    private DiscoverDishesState state = new DiscoverDishesState();

    public DiscoverDishesState getState() {
        return this.state;
    }

    public void setState(DiscoverDishesState state) {
        this.state = state;
    }

    public void addListener(DiscoverDishesViewModelListener listener) {
        listeners.add(listener);
    }

    public void notifyStateChanged() {
        for (DiscoverDishesViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }


}
