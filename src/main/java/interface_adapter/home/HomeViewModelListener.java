package interface_adapter.home;

/**
 * Listens for changes to the Home screen's state.
 */
public interface HomeViewModelListener {

    /**
     * The home state was updated.
     */
    void stateChanged();
}