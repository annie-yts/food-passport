package use_case.signup;

/*
 * Copied from the CSC207 ca-login example (Fall 2025 lab version):
 * https://github.com/annie-yts/ca-login
 */

/**
 * Input Boundary for actions which are related to signing up.
 */
public interface SignupInputBoundary {

    /**
     * Executes the signup use case.
     * @param signupInputData the input data
     */
    void execute(SignupInputData signupInputData);

    /**
     * Executes the switch to login view use case.
     */
    void switchToLoginView();
}