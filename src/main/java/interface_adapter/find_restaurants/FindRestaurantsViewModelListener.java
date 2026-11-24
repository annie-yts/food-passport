package interface_adapter.find_restaurants;

/**
 * Notified when the Find Restaurants screen's state changes.
 */
public interface FindRestaurantsViewModelListener {

    /**
     * Called after the state changes, so the view can redraw.
     */
    void stateChanged();
}
