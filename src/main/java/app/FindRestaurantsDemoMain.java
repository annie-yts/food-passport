package app;

import data_access.FakeCountryMappingGateway;
import data_access.FakeGeocodingGateway;
import data_access.FakePlacesGateway;
import interface_adapter.find_restaurants.FindRestaurantsController;
import interface_adapter.find_restaurants.FindRestaurantsPresenter;
import interface_adapter.find_restaurants.FindRestaurantsViewModel;
import use_case.find_restaurants.FindRestaurantsInteractor;
import view.FindRestaurantsView;

import javax.swing.JFrame;

/**
 * Temporary entry point that runs Use Case 5 (Find Restaurants Nearby) on its own,
 * until it is added to AppBuilder.
 */
public class FindRestaurantsDemoMain {

    /**
     * Builds every piece of Use Case 5, connects them, and opens the window.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        final FindRestaurantsViewModel viewModel = new FindRestaurantsViewModel();
        final FindRestaurantsPresenter presenter = new FindRestaurantsPresenter(viewModel);

        final FindRestaurantsInteractor interactor = new FindRestaurantsInteractor(
                new FakeCountryMappingGateway(),
                new FakeGeocodingGateway(),
                new FakePlacesGateway(),
                presenter);

        final FindRestaurantsController controller = new FindRestaurantsController(interactor);
        final FindRestaurantsView view = new FindRestaurantsView(viewModel, controller);

        final JFrame frame = new JFrame(FindRestaurantsViewModel.TITLE_LABEL);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(view);
        frame.setSize(800, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
