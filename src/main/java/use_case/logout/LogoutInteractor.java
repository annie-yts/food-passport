package use_case.logout;

/*
 * Adapted from the CSC207 ca-login example:
 * https://github.com/annie-yts/ca-login
 * Changes: made fields final.
 */

/**
 * The Logout Interactor.
 */
public class LogoutInteractor implements LogoutInputBoundary {
    private final LogoutUserDataAccessInterface userDataAccessObject;
    private final LogoutOutputBoundary logoutPresenter;

    public LogoutInteractor(LogoutUserDataAccessInterface userDataAccessInterface,
                            LogoutOutputBoundary logoutOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.logoutPresenter = logoutOutputBoundary;
    }

    @Override
    public void execute() {
        // get username
        final String username = userDataAccessObject.getCurrentUsername();

        // set username to null
        userDataAccessObject.setCurrentUsername(null);

        // build the output data with the username that was logged out
        final LogoutOutputData logoutOutputData = new LogoutOutputData(username, false);

        // tell the presenter to prepare the success view
        logoutPresenter.prepareSuccessView(logoutOutputData);
    }
}
