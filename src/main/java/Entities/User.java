package Entities;

/*
 * Adapted from the CSC207 ca-login example:
 * https://github.com/annie-yts/ca-login
 * Changes: added userId, setters for change username/password.
 */

/**
 * A simple entity representing a user. Users have a user ID, a username and a password.
 */
public class User {

    private final String userId;
    private String userName;
    private String password;

    /**
     * Creates a new user with the given ID, non-empty name and non-empty password.
     * @param userId the unique user ID
     * @param userName the username
     * @param password the password
     * @throws IllegalArgumentException if the password or name are empty
     */
    public User(String userId, String userName, String password) {
        if ("".equals(userName)) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if ("".equals(password)) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.userId = userId;
        this.userName = userName;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        if ("".equals(userName)) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if ("".equals(password)) {
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.password = password;
    }
}
