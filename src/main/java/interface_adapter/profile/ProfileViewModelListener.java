package interface_adapter.profile;

/*
 * Adapted from the CSC207 ca-login example (LoggedIn* classes):
 * https://github.com/annie-yts/ca-login
 * Changes: renamed LoggedIn to Profile for Food Passport's profile screen.
 */

public interface ProfileViewModelListener {

    /**
     * The logged-in state was updated.
     */
    void stateChanged();

    /**
     * A password change attempt occurred.
     */
    void passwordChanged();
}