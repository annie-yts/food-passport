package entities;

public class User {
    private final String userId;
    private String userName;
    private String passwordHash;

    public User(String userId, String userName, String passwordHash) {
        this.userId = userId;
        this.userName = userName;
        this.passwordHash = passwordHash;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}
