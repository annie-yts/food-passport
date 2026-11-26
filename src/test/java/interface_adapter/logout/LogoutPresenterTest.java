package interface_adapter.logout;

import interface_adapter.ViewManagerModel;
import interface_adapter.home.HomeViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.profile.ProfileViewModel;
import org.junit.jupiter.api.Test;
import use_case.logout.LogoutOutputData;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for LogoutPresenter: logging out clears every screen and returns to Log In.
 */
class LogoutPresenterTest {

    @Test
    void logoutClearsEveryScreenAndShowsLogIn() {
        final ViewManagerModel viewManagerModel = new ViewManagerModel();
        final HomeViewModel homeViewModel = new HomeViewModel();
        final ProfileViewModel profileViewModel = new ProfileViewModel();
        final LoginViewModel loginViewModel = new LoginViewModel();
        homeViewModel.getState().setUsername("arnab");
        profileViewModel.getState().setUsername("arnab");
        loginViewModel.getState().setUsername("arnab");
        loginViewModel.getState().setPassword("pw");
        viewManagerModel.setState(HomeViewModel.VIEW_NAME);

        new LogoutPresenter(viewManagerModel, homeViewModel, profileViewModel, loginViewModel)
                .prepareSuccessView(new LogoutOutputData("arnab", false));

        assertEquals(LoginViewModel.VIEW_NAME, viewManagerModel.getCurrentViewName());
        assertEquals("", homeViewModel.getState().getUsername());
        assertEquals("", profileViewModel.getState().getUsername());
        assertEquals("", loginViewModel.getState().getUsername());
        assertEquals("", loginViewModel.getState().getPassword());
    }
}