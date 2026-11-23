package interface_adapter.home;

import java.util.ArrayList;
import java.util.List;

/**
 * The View Model for the Home View, the hub shown after logging in.
 */
public class HomeViewModel {

    public static final String VIEW_NAME = "home";

    private final List<HomeViewModelListener> listeners = new ArrayList<>();

    private HomeState state = new HomeState();

    public HomeState getState() {
        return state;
    }

    public void setState(HomeState state) {
        this.state = state;
    }

    public void addListener(HomeViewModelListener listener) {
        listeners.add(listener);
    }

    public void notifyStateChanged() {
        for (HomeViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}