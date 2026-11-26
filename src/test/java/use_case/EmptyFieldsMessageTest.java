package use_case;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for EmptyFieldsMessage, which builds the "... cannot be empty." popup text.
 */
class EmptyFieldsMessageTest {

    @Test
    void noEmptyFieldsGivesNull() {
        assertNull(EmptyFieldsMessage.of(List.of()));
    }

    @Test
    void oneEmptyField() {
        assertEquals("Username cannot be empty.",
                EmptyFieldsMessage.of(List.of("Username")));
    }

    @Test
    void twoEmptyFieldsJoinedWithAnd() {
        assertEquals("Username and Password cannot be empty.",
                EmptyFieldsMessage.of(List.of("Username", "Password")));
    }

    @Test
    void threeEmptyFieldsUseCommaThenAnd() {
        assertEquals("Username, Password and Confirm Password cannot be empty.",
                EmptyFieldsMessage.of(List.of("Username", "Password", "Confirm Password")));
    }
}