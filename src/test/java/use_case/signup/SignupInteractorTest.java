package use_case.signup;

import data_access.InMemoryUserDataAccessObject;
import entities.UserFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Sign Up use case: the main flow and every alternative flow in the README.
 */
class SignupInteractorTest {

    /** A fake presenter that records what the interactor told it. */
    private static class FakePresenter implements SignupOutputBoundary {
        private String successUsername;
        private String error;
        private boolean switchedToLogin;

        @Override
        public void prepareSuccessView(SignupOutputData outputData) {
            successUsername = outputData.getUsername();
        }

        @Override
        public void prepareFailView(String errorMessage) {
            error = errorMessage;
        }

        @Override
        public void switchToLoginView() {
            switchedToLogin = true;
        }
    }

    private final InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();
    private final FakePresenter presenter = new FakePresenter();
    private final SignupInteractor interactor =
            new SignupInteractor(userRepository, presenter, new UserFactory());

    @Test
    void successSavesUserAndShowsSuccess() {
        interactor.execute(new SignupInputData("arnab", "pw", "pw"));

        assertEquals("arnab", presenter.successUsername);
        assertNull(presenter.error);
        assertTrue(userRepository.existsByName("arnab"));
        assertEquals("pw", userRepository.get("arnab").getPassword());
        assertNotNull(userRepository.get("arnab").getUserId());
    }

    @Test
    void allFieldsEmptyNamesAllThree() {
        interactor.execute(new SignupInputData("", "", ""));

        assertEquals("Username, Password and Confirm Password cannot be empty.", presenter.error);
        assertNull(presenter.successUsername);
    }

    @Test
    void usernameAndPasswordEmptyNamesBoth() {
        interactor.execute(new SignupInputData("", "", "pw"));

        assertEquals("Username and Password cannot be empty.", presenter.error);
    }

    @Test
    void onlyConfirmPasswordEmpty() {
        interactor.execute(new SignupInputData("arnab", "pw", ""));

        assertEquals("Confirm Password cannot be empty.", presenter.error);
        assertFalse(userRepository.existsByName("arnab"));
    }

    @Test
    void passwordsDontMatch() {
        interactor.execute(new SignupInputData("arnab", "pw", "different"));

        assertEquals("Passwords don't match.", presenter.error);
        assertFalse(userRepository.existsByName("arnab"));
    }

    @Test
    void usernameAlreadyTaken() {
        userRepository.save(new UserFactory().create("arnab", "old"));

        interactor.execute(new SignupInputData("arnab", "pw", "pw"));

        assertEquals("Username is already taken.", presenter.error);
        assertEquals("old", userRepository.get("arnab").getPassword());
    }

    @Test
    void switchToLoginViewTellsPresenter() {
        interactor.switchToLoginView();

        assertTrue(presenter.switchedToLogin);
    }
}