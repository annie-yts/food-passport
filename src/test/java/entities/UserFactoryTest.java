package entities;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for UserFactory: new users get a fresh ID, loaded users keep theirs.
 */
class UserFactoryTest {

    private final UserFactory factory = new UserFactory();

    @Test
    void createGivesNewUserAnId() {
        final User user = factory.create("arnab", "pw");

        assertNotNull(user.getUserId());
        assertFalse(user.getUserId().isEmpty());
        assertEquals("arnab", user.getUserName());
        assertEquals("pw", user.getPassword());
    }

    @Test
    void eachNewUserGetsADifferentId() {
        final User first = factory.create("arnab", "pw");
        final User second = factory.create("arnab", "pw");

        assertNotEquals(first.getUserId(), second.getUserId());
    }

    @Test
    void createWithIdKeepsTheGivenId() {
        final User user = factory.create("saved-id", "arnab", "pw");

        assertEquals("saved-id", user.getUserId());
        assertEquals("arnab", user.getUserName());
        assertEquals("pw", user.getPassword());
    }
}