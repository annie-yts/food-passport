package interface_adapter.add_stamp;

import use_case.stamp_dish.StampDishOutputBoundary;
import use_case.stamp_dish.StampDishOutputData;

/**
 * Turns the result of saving a stamp into what the Add Stamp screen shows.
 */
public class StampDishPresenter implements StampDishOutputBoundary {
    private final AddStampViewModel viewModel;

    /**
     * Creates the presenter.
     *
     * @param viewModel the Add Stamp view model to update
     */
    public StampDishPresenter(AddStampViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(StampDishOutputData outputData) {
        AddStampState state = viewModel.getState();

        String message;
        if (outputData.isNewCountry()) {
            message = "New country: " + outputData.getCountry() + "!";
        } else {
            message = "Stamped " + outputData.getDishName() + ".";
        }

        state.setSuccessMessage(message);
        state.setErrorMessage("");
        state.setPassportSummary(outputData.getTotalStamps(), outputData.getTotalCountries());
        state.setClearForm(true);

        viewModel.notifyStateChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        AddStampState state = viewModel.getState();
        state.setErrorMessage(errorMessage);
        state.setSuccessMessage("");
        viewModel.notifyStateChanged();
    }
}
