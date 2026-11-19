package interface_adapter.signup;

/*
 * Copied from the CSC207 ca-login example (Fall 2025 lab version):
 * https://github.com/annie-yts/ca-login
 */

public interface SignupViewModelListener {

    /**
     * The signup state was updated.
     */
    void stateChanged();

}