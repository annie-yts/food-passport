package interface_adapter.profile;

/*
 * Adapted from the CSC207 ca-login example (LoggedIn* classes):
 * https://github.com/annie-yts/ca-login
 * Changes: renamed LoggedIn to Profile for Food Passport's profile screen.
 */

/**
 * The State information representing the logged-in user.
 */
public class ProfileState {
    private String username = "";

    private String password = "";
    private String passwordError;

    public ProfileState(ProfileState copy) {
        username = copy.username;
        password = copy.password;
        passwordError = copy.passwordError;
    }

    // Because of the previous copy constructor, the default constructor must be explicit.
    public ProfileState() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPasswordError(String passwordError) {
        this.passwordError = passwordError;
    }

    public String getPasswordError() {
        return passwordError;
    }
}