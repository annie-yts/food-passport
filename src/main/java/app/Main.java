package app;

import javax.swing.*;

/*
 * Adapted from the CSC207 ca-login example:
 * https://github.com/annie-yts/ca-login
 * Changes: adds the Profile view instead of LoggedIn; change password to be added later.
 */

/**
 * The entry point of the Food Passport application.
 */
public class Main {
    public static void main(String[] args) {
        final AppBuilder appBuilder = new AppBuilder();
        final JFrame application = appBuilder
                .addLoginView()
                .addSignupView()
                .addProfileView()
                .addSignupUseCase()
                .addLoginUseCase()
                .addLogoutUseCase()
                .build();
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
