package use_case.login;

import data_access.InMemoryUserDataAccessObject;
import entities.UserFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Log In use case: the main flow and every alternative flow in the README.
 */
class LoginInteractorTest {

    /** A fake presenter that records what the interactor told it. */
    private static class FakePresenter implements LoginOutputBoundary {
        private String successUsername;
        private String error;
        private boolean switchedToSignup;

        @Override
        public void prepareSuccessView(LoginOutputData outputData) {
            successUsername = outputData.getUsername();
        }

        @Override
        public void prepareFailView(String errorMessage) {
            error = errorMessage;
        }

        @Override
        public void switchToSignupView() {
            switchedToSignup = true;
        }
    }

    private final InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();
    private final FakePresenter presenter = new FakePresenter();
    private final LoginInteractor interactor = new LoginInteractor(userRepository, presenter);

    LoginInteractorTest() {
        userRepository.save(new UserFactory().create("arnab", "pw"));
    }

    @Test
    void successLogsInAndRemembersCurrentUser() {
        interactor.execute(new LoginInputData("arnab", "pw"));

        assertEquals("arnab", presenter.successUsername);
        assertNull(presenter.error);
        assertEquals("arnab", userRepository.getCurrentUsername());
    }

    @Test
    void bothFieldsEmpty() {
        interactor.execute(new LoginInputData("", ""));

        assertEquals("Username and Password cannot be empty.", presenter.error);
        assertNull(presenter.successUsername);
    }

    @Test
    void usernameEmpty() {
        interactor.execute(new LoginInputData("", "pw"));

        assertEquals("Username cannot be empty.", presenter.error);
    }

    @Test
    void passwordEmpty() {
        interactor.execute(new LoginInputData("arnab", ""));

        assertEquals("Password cannot be empty.", presenter.error);
    }

    @Test
    void unknownUsername() {
        interactor.execute(new LoginInputData("nobody", "pw"));

        assertEquals("Username does not exist.", presenter.error);
        assertNull(userRepository.getCurrentUsername());
    }

    @Test
    void wrongPassword() {
        interactor.execute(new LoginInputData("arnab", "wrong"));

        assertEquals("Incorrect password.", presenter.error);
        assertNull(userRepository.getCurrentUsername());
    }

    @Test
    void switchToSignupViewTellsPresenter() {
        interactor.switchToSignupView();

        assertTrue(presenter.switchedToSignup);
    }
}