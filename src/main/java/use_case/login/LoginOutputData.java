package use_case.login;

/*
 * Copied from the CSC207 ca-login example (Fall 2025 lab version):
 * https://github.com/annie-yts/ca-login
 */

/**
 * Output Data for the Login Use Case.
 */
public class LoginOutputData {

    private final String username;

    public LoginOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}