package interface_adapter.signup;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import org.junit.jupiter.api.Test;
import use_case.signup.SignupOutputData;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for SignupPresenter: which screen is shown and what each screen's state holds.
 */
class SignupPresenterTest {

    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final SignupViewModel signupViewModel = new SignupViewModel();
    private final LoginViewModel loginViewModel = new LoginViewModel();
    private final SignupPresenter presenter =
            new SignupPresenter(viewManagerModel, signupViewModel, loginViewModel);

    @Test
    void successGoesToLogInWithUsernameFilledIn() {
        presenter.prepareSuccessView(new SignupOutputData("arnab"));

        assertEquals(LoginViewModel.VIEW_NAME, viewManagerModel.getCurrentViewName());
        assertEquals("arnab", loginViewModel.getState().getUsername());
    }

    @Test
    void successClearsTheSignUpScreen() {
        signupViewModel.getState().setUsername("arnab");
        signupViewModel.getState().setPassword("pw");

        presenter.prepareSuccessView(new SignupOutputData("arnab"));

        assertEquals("", signupViewModel.getState().getUsername());
        assertEquals("", signupViewModel.getState().getPassword());
    }

    @Test
    void failShowsErrorAndStaysOnSignUp() {
        viewManagerModel.setState(SignupViewModel.VIEW_NAME);

        presenter.prepareFailView("Passwords don't match.");

        assertEquals("Passwords don't match.", signupViewModel.getState().getUsernameError());
        assertEquals(SignupViewModel.VIEW_NAME, viewManagerModel.getCurrentViewName());
    }

    @Test
    void switchToLoginViewShowsLogIn() {
        presenter.switchToLoginView();

        assertEquals(LoginViewModel.VIEW_NAME, viewManagerModel.getCurrentViewName());
    }
}