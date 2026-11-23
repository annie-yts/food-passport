package use_case.login;

/*
 * Adapted from the CSC207 ca-login example:
 * https://github.com/annie-yts/ca-login
 * Changes: added switchToSignupView().
 */

/**
 * Input Boundary for actions which are related to logging in.
 */
public interface LoginInputBoundary {

    /**
     * Executes the login use case.
     * @param loginInputData the input data
     */
    void execute(LoginInputData loginInputData);

    /**
     * Executes the switch to sign up view use case.
     */
    void switchToSignupView();
}