package interface_adapter.profile;

import java.util.ArrayList;
import java.util.List;

/*
 * Adapted from the CSC207 ca-login example (LoggedIn* classes):
 * https://github.com/annie-yts/ca-login
 * Changes: renamed LoggedIn to Profile for Food Passport's profile screen.
 */

/**
 * The View Model for the Logged In View.
 */
public class ProfileViewModel {

    private final List<ProfileViewModelListener> listeners = new ArrayList<>();

    public static final String VIEW_NAME = "profile";

    private ProfileState state = new ProfileState();

    public ProfileState getState() {
        return this.state;
    }

    public void setState(ProfileState state) {
        this.state = state;
    }

    public void addListener(ProfileViewModelListener listener) {
        listeners.add(listener);
    }

    public void notifyStateChanged() {
        for (ProfileViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }

    public void notifyPasswordChangeUpdate() {
        for (ProfileViewModelListener listener : listeners) {
            listener.passwordChanged();
        }
    }
}