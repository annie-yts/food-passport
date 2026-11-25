package interface_adapter.find_restaurants;

import java.util.ArrayList;
import java.util.List;

/**
 * The view model for the Find Restaurants screen.
 */

public class FindRestaurantsViewModel {
    public static final String VIEW_NAME = "find restaurants";
    public static final String TITLE_LABEL = "Find Restaurants Nearby";
    public static final String COUNTRY_LABEL = "Country code";
    public static final String ADDRESS_LABEL = "Address";
    public static final String RADIUS_LABEL = "Radius (km)";
    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String BACK_BUTTON_LABEL = "Back to Home";

    private final List<FindRestaurantsViewModelListener> listeners = new ArrayList<>();
    private FindRestaurantsState state = new FindRestaurantsState();

    public FindRestaurantsState getState() {
        return state;
    }

    public void setState(FindRestaurantsState state) {
        this.state = state;
    }

    /**
     * Registers a listener to be told when the state changes.
     *
     * @param listener the listener, usually the view
     */
    public void addListener(FindRestaurantsViewModelListener listener) {
        listeners.add(listener);
    }

    /**
     * Tells every listener that the state has changed.
     */
    public void notifyStateChanged() {
        for (FindRestaurantsViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}
