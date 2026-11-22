package use_case.signup;

/*
 * Copied from the CSC207 ca-login example (Fall 2025 lab version):
 * https://github.com/annie-yts/ca-login
 */

/**
 * Output Data for the Signup Use Case.
 */
public class SignupOutputData {

    private final String username;

    public SignupOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}