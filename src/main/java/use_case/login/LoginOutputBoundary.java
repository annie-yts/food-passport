package use_case.login;

/*
 * Copied from the CSC207 ca-login example (Fall 2025 lab version):
 * https://github.com/annie-yts/ca-login
 */

/**
 * The output boundary for the Login Use Case.
 */
public interface LoginOutputBoundary {
    /**
     * Prepares the success view for the Login Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(LoginOutputData outputData);

    /**
     * Prepares the failure view for the Login Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}