package entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the User entity's rules: a username and password can never be empty.
 */
class UserTest {

    @Test
    void constructorStoresValues() {
        final User user = new User("id-1", "arnab", "pw");

        assertEquals("id-1", user.getUserId());
        assertEquals("arnab", user.getUserName());
        assertEquals("pw", user.getPassword());
    }

    @Test
    void emptyUsernameIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new User("id-1", "", "pw"));
    }

    @Test
    void emptyPasswordIsRejected() {
        assertThrows(IllegalArgumentException.class, () -> new User("id-1", "arnab", ""));
    }

    @Test
    void setUserNameChangesNameButKeepsId() {
        final User user = new User("id-1", "arnab", "pw");

        user.setUserName("arnab2");

        assertEquals("arnab2", user.getUserName());
        assertEquals("id-1", user.getUserId());
    }

    @Test
    void setUserNameRejectsEmpty() {
        final User user = new User("id-1", "arnab", "pw");

        assertThrows(IllegalArgumentException.class, () -> user.setUserName(""));
        assertEquals("arnab", user.getUserName());
    }

    @Test
    void setPasswordChangesPassword() {
        final User user = new User("id-1", "arnab", "pw");

        user.setPassword("newpw");

        assertEquals("newpw", user.getPassword());
    }

    @Test
    void setPasswordRejectsEmpty() {
        final User user = new User("id-1", "arnab", "pw");

        assertThrows(IllegalArgumentException.class, () -> user.setPassword(""));
        assertEquals("pw", user.getPassword());
    }
}