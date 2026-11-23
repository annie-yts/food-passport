package interface_adapter.add_stamp;

import use_case.search_dish.SearchDishOutputBoundary;
import use_case.search_dish.SearchDishOutputData;

import java.util.ArrayList;
import java.util.List;

/**
 * Turns search results into what the Add Stamp screen shows.
 */
public class SearchDishPresenter implements SearchDishOutputBoundary {
    private final AddStampViewModel viewModel;

    /**
     * Creates the presenter.
     *
     * @param viewModel the Add Stamp view model to update
     */
    public SearchDishPresenter(AddStampViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(SearchDishOutputData outputData) {
        AddStampState state = viewModel.getState();
        List<DishSuggestion> suggestions = new ArrayList<>();
        for (SearchDishOutputData.Suggestion s : outputData.getSuggestions()) {
            suggestions.add(new DishSuggestion(s.getDishId(), s.getDishName(), s.getCountry(),
                    s.getCategory(), s.getThumbnailUrl()));
        }
        state.setSuggestions(suggestions);
        state.setManualMode(false);
        state.setErrorMessage("");
        state.setSuccessMessage("");
        viewModel.notifyStateChanged();
    }

    @Override
    public void prepareNoResultsView(String query) {
        AddStampState state = viewModel.getState();
        state.setSuggestions(new ArrayList<>());
        state.setManualMode(true);
        state.setErrorMessage("No dishes found for '" + query + "'. Enter it manually.");
        state.setSuccessMessage("");
        viewModel.notifyStateChanged();
    }

    @Override
    public void prepareApiUnavailableView(String message) {
        AddStampState state = viewModel.getState();
        state.setSuggestions(new ArrayList<>());
        state.setManualMode(true);
        state.setErrorMessage(message);
        state.setSuccessMessage("");
        viewModel.notifyStateChanged();
    }
}