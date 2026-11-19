package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.profile.ProfileState;
import interface_adapter.profile.ProfileViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

/*
 * Adapted from the CSC207 ca-login example:
 * https://github.com/annie-yts/ca-login
 * Changes: switches to the Profile view instead of LoggedIn.
 */

/**
 * The Presenter for the Login Use Case.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final ProfileViewModel profileViewModel;
    private final ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          ProfileViewModel profileViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.profileViewModel = profileViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, update the ProfileViewModel's state
        final ProfileState profileState = profileViewModel.getState();
        profileState.setUsername(response.getUsername());
        this.profileViewModel.notifyStateChanged();

        // and clear everything from the LoginViewModel's state
        this.loginViewModel.setState(new LoginState());
        this.loginViewModel.notifyStateChanged();

        // switch to the profile view
        this.viewManagerModel.setState(ProfileViewModel.VIEW_NAME);
        this.viewManagerModel.notifyViewChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(error);
        loginViewModel.notifyStateChanged();
    }
}