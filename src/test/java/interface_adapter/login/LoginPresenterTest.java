package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.home.HomeViewModel;
import interface_adapter.profile.ProfileViewModel;
import interface_adapter.signup.SignupViewModel;
import org.junit.jupiter.api.Test;
import use_case.login.LoginOutputData;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for LoginPresenter: which screen is shown and what each screen's state holds.
 */
class LoginPresenterTest {

    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final HomeViewModel homeViewModel = new HomeViewModel();
    private final ProfileViewModel profileViewModel = new ProfileViewModel();
    private final LoginViewModel loginViewModel = new LoginViewModel();
    private final LoginPresenter presenter =
            new LoginPresenter(viewManagerModel, homeViewModel, profileViewModel, loginViewModel);

    @Test
    void successGoesToHome() {
        presenter.prepareSuccessView(new LoginOutputData("arnab"));

        assertEquals(HomeViewModel.VIEW_NAME, viewManagerModel.getCurrentViewName());
    }

    @Test
    void successShowsUsernameOnHomeAndProfile() {
        presenter.prepareSuccessView(new LoginOutputData("arnab"));

        assertEquals("arnab", homeViewModel.getState().getUsername());
        assertEquals("arnab", profileViewModel.getState().getUsername());
    }

    @Test
    void successClearsTheLogInScreen() {
        loginViewModel.getState().setUsername("arnab");
        loginViewModel.getState().setPassword("pw");

        presenter.prepareSuccessView(new LoginOutputData("arnab"));

        assertEquals("", loginViewModel.getState().getUsername());
        assertEquals("", loginViewModel.getState().getPassword());
    }

    @Test
    void failShowsErrorAndStaysOnLogIn() {
        viewManagerModel.setState(LoginViewModel.VIEW_NAME);

        presenter.prepareFailView("Incorrect password.");

        assertEquals("Incorrect password.", loginViewModel.getState().getLoginError());
        assertEquals(LoginViewModel.VIEW_NAME, viewManagerModel.getCurrentViewName());
    }

    @Test
    void switchToSignupViewShowsSignUp() {
        presenter.switchToSignupView();

        assertEquals(SignupViewModel.VIEW_NAME, viewManagerModel.getCurrentViewName());
    }
}