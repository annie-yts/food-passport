package use_case.logout;

/*
 * Copied from the CSC207 ca-login example (Fall 2025 lab version):
 * https://github.com/annie-yts/ca-login
 */

/**
 * Input Boundary for the logout use case.
 */
public interface LogoutInputBoundary {

    /**
     * Executes the Logout use case. After this executes,
     * there will be no logged-in user.
     */
    void execute();
}