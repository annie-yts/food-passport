package use_case.signup;

import entities.User;
import entities.UserFactory;

import use_case.EmptyFieldsMessage;

import java.util.ArrayList;
import java.util.List;

/*
 * Adapted from the CSC207 ca-login example:
 * https://github.com/annie-yts/ca-login
 * Changes: uses entities.User and entities.UserFactory (getUserName instead of getName);
 * passport creation to be added;
 * checks all empty fields first and names each one; "Username is already taken."
 */

/**
 * The Signup Interactor.
 */
public class SignupInteractor implements SignupInputBoundary {
    private final SignupUserDataAccessInterface userDataAccessObject;
    private final SignupOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        final String username = signupInputData.getUsername();
        final String password = signupInputData.getPassword();
        final String repeatPassword = signupInputData.getRepeatPassword();

        final List<String> emptyFields = new ArrayList<>();
        if ("".equals(username)) {
            emptyFields.add("Username");
        }
        if ("".equals(password)) {
            emptyFields.add("Password");
        }
        if ("".equals(repeatPassword)) {
            emptyFields.add("Confirm Password");
        }

        if (!emptyFields.isEmpty()) {
            userPresenter.prepareFailView(EmptyFieldsMessage.of(emptyFields));
        }
        else if (!password.equals(repeatPassword)) {
            userPresenter.prepareFailView("Passwords don't match.");
        }
        else if (userDataAccessObject.existsByName(username)) {
            userPresenter.prepareFailView("Username is already taken.");
        }
        else {
            final User user = userFactory.create(username, password);
            userDataAccessObject.save(user);

            // TODO: create an empty Passport for this user once PassportDataAccessInterface exists

            final SignupOutputData signupOutputData = new SignupOutputData(user.getUserName());
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}