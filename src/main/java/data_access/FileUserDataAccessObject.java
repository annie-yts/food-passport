package data_access;

import Entities.User;
import Entities.UserFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.login.LoginUserDataAccessInterface;
import use_case.logout.LogoutUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

/*
 * Adapted from the CSC207 ca-login example:
 * https://github.com/annie-yts/ca-login
 * Changes: uses Entities.User and Entities.UserFactory; saves users as JSON
 * (userId, username, password) instead of CSV; creates the parent folder
 * (e.g. data/) if it doesn't exist; change password support to be added with that use case.
 */

/**
 * DAO for user data implemented using a JSON file to persist the data.
 */
public class FileUserDataAccessObject implements SignupUserDataAccessInterface,
        LoginUserDataAccessInterface,
        LogoutUserDataAccessInterface {

    private static final String USER_ID = "userId";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    private final File jsonFile;
    private final Map<String, User> accounts = new HashMap<>();

    private String currentUsername;

    /**
     * Construct this DAO for saving to and reading from a local JSON file.
     * @param jsonPath the path of the file to save to
     * @param userFactory factory for creating user objects
     * @throws RuntimeException if there is an IOException when accessing the file
     */
    public FileUserDataAccessObject(String jsonPath, UserFactory userFactory) {
        jsonFile = new File(jsonPath);

        if (jsonFile.length() == 0) {
            save();
        }
        else {
            try {
                final String content = Files.readString(jsonFile.toPath(), StandardCharsets.UTF_8);
                final JSONArray users = new JSONArray(content);

                for (int i = 0; i < users.length(); i++) {
                    final JSONObject json = users.getJSONObject(i);
                    final User user = userFactory.create(
                            json.getString(USER_ID),
                            json.getString(USERNAME),
                            json.getString(PASSWORD));
                    accounts.put(user.getUserName(), user);
                }
            }
            catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void save() {
        final File parent = jsonFile.getParentFile();
        if (parent != null) {
            parent.mkdirs();
        }

        final JSONArray users = new JSONArray();
        for (User user : accounts.values()) {
            final JSONObject json = new JSONObject();
            json.put(USER_ID, user.getUserId());
            json.put(USERNAME, user.getUserName());
            json.put(PASSWORD, user.getPassword());
            users.put(json);
        }

        try {
            Files.writeString(jsonFile.toPath(), users.toString(2), StandardCharsets.UTF_8);
        }
        catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void save(User user) {
        accounts.put(user.getUserName(), user);
        this.save();
    }

    @Override
    public User get(String username) {
        return accounts.get(username);
    }

    @Override
    public void setCurrentUsername(String name) {
        currentUsername = name;
    }

    @Override
    public String getCurrentUsername() {
        return currentUsername;
    }

    @Override
    public boolean existsByName(String identifier) {
        return accounts.containsKey(identifier);
    }
}