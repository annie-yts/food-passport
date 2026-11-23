package Entities;
import java.util.UUID;

/*
 * Adapted from the CSC207 ca-login example:
 * https://github.com/annie-yts/ca-login
 * Changes: generates a UUID user ID at sign up; added create(userId, userName, password) for loading saved users.
 */

public class UserFactory {

    public User create(String userName, String password){
        String userId = UUID.randomUUID().toString();
        return new User(userId, userName, password);
    }

    public User create(String userId, String userName, String password){
        return new User(userId, userName, password);
    }

}
