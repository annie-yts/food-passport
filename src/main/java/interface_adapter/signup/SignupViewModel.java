package interface_adapter.signup;

import java.util.ArrayList;
import java.util.List;

/*
 * Adapted from the CSC207 ca-login example:
 * https://github.com/annie-yts/ca-login
 * Changes: Capitalised button labels; field labels Username, Password, Confirm Password.
 */

/**
 * The ViewModel for the Signup View.
 */
public class SignupViewModel {

    public static final String TITLE_LABEL = "Sign Up View";
    public static final String USERNAME_LABEL = "Username";
    public static final String PASSWORD_LABEL = "Password";
    public static final String REPEAT_PASSWORD_LABEL = "Confirm Password";

    public static final String SIGNUP_BUTTON_LABEL = "Sign Up";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public static final String TO_LOGIN_BUTTON_LABEL = "Go to Log In";

    private final List<SignupViewModelListener> listeners = new ArrayList<>();

    public static final String VIEW_NAME = "sign up";

    private SignupState state = new SignupState();

    public SignupState getState() {
        return this.state;
    }

    public void setState(SignupState state) {
        this.state = state;
    }

    public void addListener(SignupViewModelListener listener) {
        listeners.add(listener);
    }

    public void notifyStateChanged() {
        for (SignupViewModelListener listener : listeners) {
            listener.stateChanged();
        }
    }
}