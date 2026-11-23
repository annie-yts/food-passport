package interface_adapter.add_stamp;

/**
 * Notified when the Add Stamp screen's state changes.
 */
public interface AddStampViewModelListener {

    /**
     * Called after the state changes, so the view can redraw.
     */
    void stateChanged();
}