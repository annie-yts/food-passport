package interface_adapter.add_stamp;

import java.util.ArrayList;
import java.util.List;

/**
 * The view model for the Add Stamp screen.
 */
public class AddStampViewModel {
    public static final String VIEW_NAME = "add stamp";
    public static final String TITLE_LABEL = "Add Stamp";
    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String MANUAL_LABEL = "Enter manually";
    public static final String DISH_NAME_LABEL = "Dish name";
    public static final String COUNTRY_LABEL = "Country";
    public static final String DATE_LABEL = "Date (YYYY-MM-DD)";
    public static final String PLACE_LABEL = "Place";
    public static final String RATING_LABEL = "Rating";
    public static final String NOTES_LABEL = "Notes";
    public static final String RESTAURANT_LABEL = "Restaurant name";
    public static final String SAVE_BUTTON_LABEL = "Save stamp";

    private final List<AddStampViewModelListener> listeners = new ArrayList<>();
    private AddStampState state = new AddStampState();

    public AddStampState getState() {
        return state;
    }

    public void setState(AddStampState state) {
        this.state = state;
    }

    /**
     * Registers a listener to be told when the state changes.
     *
     * @param listener the listener, usually the view
     */
    public void addListener(AddStampViewModelListener listener) {
        listeners.add(listener);
    }

    /**
     * Tells every listener that the state has changed.
     */
    public void notifyStateChanged() {
        for (AddStampViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}
