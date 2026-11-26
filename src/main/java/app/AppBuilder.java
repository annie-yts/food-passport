package app;

import entities.Passport;
import entities.User;
import entities.UserFactory;
import data_access.CountryMapping;
import data_access.FileCountryListDataAccessObject;
import data_access.FileUserDataAccessObject;
import data_access.InMemoryPassportDataAccessObject;
import data_access.TheMealDBClient;
import interface_adapter.ViewManagerModel;
import interface_adapter.add_stamp.AddStampState;
import interface_adapter.add_stamp.AddStampViewModel;
import interface_adapter.add_stamp.SearchDishController;
import interface_adapter.add_stamp.SearchDishPresenter;
import interface_adapter.add_stamp.StampDishController;
import interface_adapter.add_stamp.StampDishPresenter;
import interface_adapter.discover_dishes.DiscoverDishesController;
import interface_adapter.discover_dishes.DiscoverDishesPresenter;
import interface_adapter.discover_dishes.DiscoverDishesViewModel;
import interface_adapter.home.HomeViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logout.LogoutController;
import interface_adapter.logout.LogoutPresenter;
import interface_adapter.profile.ProfileViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.discover_dishes.ApiAccessBoundary;
import use_case.discover_dishes.DiscoverDishesInputBoundary;
import use_case.discover_dishes.DiscoverDishesInteractor;
import use_case.discover_dishes.DiscoverDishesOutputBoundary;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.logout.LogoutInputBoundary;
import use_case.logout.LogoutInteractor;
import use_case.logout.LogoutOutputBoundary;
import use_case.search_dish.SearchDishInteractor;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.stamp_dish.CountryListDataAccessInterface;
import use_case.stamp_dish.StampDishDataAccessInterface;
import use_case.stamp_dish.StampDishInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;

/*
 * Adapted from the CSC207 ca-login example:
 * https://github.com/annie-yts/ca-login
 * Changes: uses entities.UserFactory; LoggedIn view replaced by ProfileView;
 * users saved to data/users.json; change password to be added with that use case;
 * opens on the Log In screen; margin around every screen; Home screen after log in.
 */

/**
 * Builds the application by creating each view and wiring up its use cases.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    final UserFactory userFactory = new UserFactory();
    final ViewManagerModel viewManagerModel = new ViewManagerModel();
    ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // DAO version using local file storage
    final FileUserDataAccessObject userDataAccessObject =
            new FileUserDataAccessObject("data/users.json", userFactory);

    private SignupView signupView;
    private SignupViewModel signupViewModel;
    private LoginViewModel loginViewModel;
    private HomeViewModel homeViewModel;
    private HomeView homeView;
    private ProfileViewModel profileViewModel;
    private ProfileView profileView;
    private LoginView loginView;

    private final CountryMapping countryMapping = new CountryMapping();
    private final ApiAccessBoundary mealDbClient = new TheMealDBClient(countryMapping);
    private final StampDishDataAccessInterface passports = new InMemoryPassportDataAccessObject();
    private final AddStampViewModel addStampViewModel = new AddStampViewModel();
    private DiscoverDishesViewModel discoverDishesViewModel;
    private DiscoverDishesView discoverDishesView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        cardPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
    }

    public AppBuilder addSignupView() {
        signupViewModel = new SignupViewModel();
        signupView = new SignupView(signupViewModel);
        cardPanel.add(signupView, SignupViewModel.VIEW_NAME);
        return this;
    }

    public AppBuilder addLoginView() {
        loginViewModel = new LoginViewModel();
        loginView = new LoginView(loginViewModel);
        cardPanel.add(loginView, LoginViewModel.VIEW_NAME);
        return this;
    }

    public AppBuilder addHomeView() {
        homeViewModel = new HomeViewModel();
        homeView = new HomeView(homeViewModel);

        homeView.setOnAddStamp(this::openAddStamp);
        homeView.setOnWorldMap(() -> JOptionPane.showMessageDialog(homeView, "World Map is coming soon."));
        homeView.setOnDiscover(() -> switchTo(DiscoverDishesViewModel.VIEW_NAME));
        homeView.setOnProfile(() -> switchTo(ProfileViewModel.VIEW_NAME));

        cardPanel.add(homeView, HomeViewModel.VIEW_NAME);
        return this;
    }

    public AppBuilder addProfileView() {
        profileViewModel = new ProfileViewModel();
        profileView = new ProfileView(profileViewModel);
        profileView.setOnBack(() -> switchTo(HomeViewModel.VIEW_NAME));
        cardPanel.add(profileView, ProfileViewModel.VIEW_NAME);
        return this;
    }

    public AppBuilder addStampView() {
        final CountryListDataAccessInterface countries = new FileCountryListDataAccessObject(countryMapping);
        final AddStampView view = createStampView(addStampViewModel, mealDbClient, countries, passports);
        view.addBackToHomeButton(() -> switchTo(HomeViewModel.VIEW_NAME));
        cardPanel.add(view, AddStampViewModel.VIEW_NAME);
        return this;
    }

    static AddStampView createStampView(AddStampViewModel model, ApiAccessBoundary api,
                                        CountryListDataAccessInterface countries, StampDishDataAccessInterface passports) {
        final SearchDishInteractor search = new SearchDishInteractor(api, countries, new SearchDishPresenter(model));
        final StampDishInteractor stamp = new StampDishInteractor(passports, countries, new StampDishPresenter(model));
        return new AddStampView(model, new SearchDishController(search), new StampDishController(stamp));
    }

    private void openAddStamp() {
        final User user = userDataAccessObject.get(userDataAccessObject.getCurrentUsername());
        if (user == null) {
            switchTo(LoginViewModel.VIEW_NAME);
            return;
        }
        if (!user.getUserId().equals(addStampViewModel.getState().getUserId())) {
            final Passport passport = passports.getPassport(user.getUserId());
            final AddStampState state = new AddStampState();
            state.setUserId(user.getUserId());
            state.setCountries(countryMapping.getCountryNamesByCode());
            state.setPassportSummary(passport.getStamps().size(), passport.countriesTried().size());
            state.setClearForm(true);
            addStampViewModel.setState(state);
            addStampViewModel.notifyStateChanged();
        }
        switchTo(AddStampViewModel.VIEW_NAME);
    }

    /** Shows the screen with the given view name. */
    private void switchTo(String viewName) {
        viewManagerModel.setState(viewName);
        viewManagerModel.notifyViewChanged();
    }

    public AppBuilder addSignupUseCase() {
        final SignupOutputBoundary signupOutputBoundary = new SignupPresenter(viewManagerModel,
                signupViewModel, loginViewModel);
        final SignupInputBoundary userSignupInteractor = new SignupInteractor(
                userDataAccessObject, signupOutputBoundary, userFactory);

        final SignupController controller = new SignupController(userSignupInteractor);
        signupView.setSignupController(controller);
        return this;
    }

    public AppBuilder addLoginUseCase() {
        final LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, homeViewModel,
                profileViewModel, loginViewModel);
        final LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary);

        final LoginController loginController = new LoginController(loginInteractor);
        loginView.setLoginController(loginController);
        return this;
    }

    /**
     * Adds the Logout Use Case to the application.
     * @return this builder
     */
    public AppBuilder addLogoutUseCase() {
        final LogoutOutputBoundary logoutOutputBoundary = new LogoutPresenter(viewManagerModel, homeViewModel,
                profileViewModel, loginViewModel);

        final LogoutInputBoundary logoutInteractor =
                new LogoutInteractor(userDataAccessObject, logoutOutputBoundary);

        final LogoutController logoutController = new LogoutController(logoutInteractor);
        homeView.setLogoutController(logoutController);
        return this;
    }

    public AppBuilder addDiscoverDishesView() {
        discoverDishesViewModel = new DiscoverDishesViewModel();
        discoverDishesView = new DiscoverDishesView(discoverDishesViewModel, countryMapping.getCountryNamesByCode());
        discoverDishesView.addBackToProfileButton(() -> switchTo(HomeViewModel.VIEW_NAME));
        cardPanel.add(discoverDishesView, DiscoverDishesViewModel.VIEW_NAME);
        return this;
    }

    public AppBuilder addDiscoverDishesUseCase() {
        final DiscoverDishesOutputBoundary presenter =
                new DiscoverDishesPresenter(discoverDishesViewModel);
        final DiscoverDishesInputBoundary interactor =
                new DiscoverDishesInteractor(mealDbClient, presenter);
        final DiscoverDishesController controller = new DiscoverDishesController(interactor);
        discoverDishesView.setDiscoverDishesController(controller);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Food Passport");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(LoginViewModel.VIEW_NAME);
        viewManagerModel.notifyViewChanged();

        return application;
    }
}
