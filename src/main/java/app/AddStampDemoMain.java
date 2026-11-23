package app;

import data_access.FileCountryListDataAccessObject;
import data_access.InMemoryPassportDataAccessObject;
import data_access.TheMealDBClient;
import interface_adapter.add_stamp.AddStampState;
import interface_adapter.add_stamp.AddStampViewModel;
import interface_adapter.add_stamp.SearchDishController;
import interface_adapter.add_stamp.SearchDishPresenter;
import interface_adapter.add_stamp.StampDishController;
import interface_adapter.add_stamp.StampDishPresenter;
import use_case.search_dish.SearchDishInteractor;
import use_case.stamp_dish.StampDishInteractor;
import view.AddStampView;

import javax.swing.JFrame;

/**
 * Temporary entry point that runs Use Case 2 (Stamp a Dish) on its own,
 * until it is added to AppBuilder after the team merge.
 */
public class AddStampDemoMain {

    /**
     * Builds every piece of Use Case 2, connects them, and opens the window.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        FileCountryListDataAccessObject countryList = new FileCountryListDataAccessObject();
        InMemoryPassportDataAccessObject passportDataAccess = new InMemoryPassportDataAccessObject();
        TheMealDBClient api = new TheMealDBClient();

        AddStampViewModel viewModel = new AddStampViewModel();
        AddStampState state = viewModel.getState();
        state.setUserId("demo-user");
        state.setCountries(countryList.getAllCountries());
        state.setPassportSummary("Passport: 0 stamps · 0 countries");

        SearchDishPresenter searchPresenter = new SearchDishPresenter(viewModel);
        StampDishPresenter stampPresenter = new StampDishPresenter(viewModel);

        SearchDishInteractor searchInteractor = new SearchDishInteractor(api, countryList, searchPresenter);
        StampDishInteractor stampInteractor =
                new StampDishInteractor(passportDataAccess, countryList, stampPresenter);

        SearchDishController searchController = new SearchDishController(searchInteractor);
        StampDishController stampController = new StampDishController(stampInteractor);

        AddStampView view = new AddStampView(viewModel, searchController, stampController);

        JFrame frame = new JFrame("Food Passport");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}