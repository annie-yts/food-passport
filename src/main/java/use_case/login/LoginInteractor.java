package use_case.login;

import Entities.User;

import use_case.EmptyFieldsMessage;

import java.util.ArrayList;
import java.util.List;

/*
 * Adapted from the CSC207 ca-login example:
 * https://github.com/annie-yts/ca-login
 * Changes: uses Entities.User (getUserName instead of getName);
 * one generic error message for a wrong username or password;
 * added switchToSignupView(); names each empty field before checking the account.
 */

/**
 * The Login Interactor.
 */
public class LoginInteractor implements LoginInputBoundary {
    private final LoginUserDataAccessInterface userDataAccessObject;
    private final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        final String username = loginInputData.getUsername();
        final String password = loginInputData.getPassword();

        final List<String> emptyFields = new ArrayList<>();
        if ("".equals(username)) {
            emptyFields.add("Username");
        }
        if ("".equals(password)) {
            emptyFields.add("Password");
        }

        if (!emptyFields.isEmpty()) {
            loginPresenter.prepareFailView(EmptyFieldsMessage.of(emptyFields));
        }
        else if (!userDataAccessObject.existsByName(username)
                || !password.equals(userDataAccessObject.get(username).getPassword())) {
            loginPresenter.prepareFailView("Incorrect username or password.");
        }
        else {
            final User user = userDataAccessObject.get(username);
            userDataAccessObject.setCurrentUsername(username);

            final LoginOutputData loginOutputData = new LoginOutputData(user.getUserName());
            loginPresenter.prepareSuccessView(loginOutputData);
        }
    }

    @Override
    public void switchToSignupView() {
        loginPresenter.switchToSignupView();
    }
}