package interface_adapter.login;

/*
 * Copied from the CSC207 ca-login example (Fall 2025 lab version):
 * https://github.com/annie-yts/ca-login
 */

public interface LoginViewModelListener {

    /**
     * The login state was updated.
     */
    void stateChanged();

}