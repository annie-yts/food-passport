package use_case.logout;

/*
 * Copied from the CSC207 ca-login example (Fall 2025 lab version):
 * https://github.com/annie-yts/ca-login
 */

/**
 * Output Data for the Logout Use Case.
 */
public class LogoutOutputData {

    private final String username;

    public LogoutOutputData(String username, boolean b) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}