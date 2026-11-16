package Entities;

public class User {
    private String userId;
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

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    // Haven't created functions for passwordHash yet, since I'm not sure abt the logic of login
}
