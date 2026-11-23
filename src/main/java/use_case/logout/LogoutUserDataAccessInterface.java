package use_case.logout;

/*
 * Copied from the CSC207 ca-login example (Fall 2025 lab version):
 * https://github.com/annie-yts/ca-login
 */

/**
 * DAO interface for the Logout Use Case.
 */
public interface LogoutUserDataAccessInterface {

    /**
     * Returns the username of the current user of the application.
     * @return the username of the current user
     */
    String getCurrentUsername();

    /**
     * Sets the username indicating who is the current user of the application.
     * @param username the new current username
     */
    void setCurrentUsername(String username);
}