package use_case;

import java.util.List;

/**
 * Builds a message naming every empty field, e.g.
 * "Username, Password and Confirm Password cannot be empty."
 */
public final class EmptyFieldsMessage {

    private EmptyFieldsMessage() {
    }

    /**
     * @param emptyFields names of the empty fields, in screen order
     * @return the message, or null if no fields are empty
     */
    public static String of(List<String> emptyFields) {
        final int n = emptyFields.size();
        if (n == 0) {
            return null;
        }
        if (n == 1) {
            return emptyFields.get(0) + " cannot be empty.";
        }
        final String allButLast = String.join(", ", emptyFields.subList(0, n - 1));
        return allButLast + " and " + emptyFields.get(n - 1) + " cannot be empty.";
    }
}