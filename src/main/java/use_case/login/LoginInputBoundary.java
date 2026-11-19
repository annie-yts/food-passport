package use_case.login;

/*
 * Copied from the CSC207 ca-login example (Fall 2025 lab version):
 * https://github.com/annie-yts/ca-login
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
}