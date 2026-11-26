package use_case.logout;

import data_access.InMemoryUserDataAccessObject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the Log Out use case.
 */
class LogoutInteractorTest {

    /** A fake presenter that records what the interactor told it. */
    private static class FakePresenter implements LogoutOutputBoundary {
        private String loggedOutUsername;

        @Override
        public void prepareSuccessView(LogoutOutputData outputData) {
            loggedOutUsername = outputData.getUsername();
        }
    }

    @Test
    void logoutClearsCurrentUserAndShowsSuccess() {
        final InMemoryUserDataAccessObject userRepository = new InMemoryUserDataAccessObject();
        userRepository.setCurrentUsername("arnab");
        final FakePresenter presenter = new FakePresenter();

        new LogoutInteractor(userRepository, presenter).execute();

        assertEquals("arnab", presenter.loggedOutUsername);
        assertNull(userRepository.getCurrentUsername());
    }
}