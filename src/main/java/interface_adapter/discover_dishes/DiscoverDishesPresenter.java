package interface_adapter.discover_dishes;

import use_case.discover_dishes.ApiException;
import use_case.discover_dishes.DiscoverDishesOutputBoundary;
import use_case.discover_dishes.DiscoverDishesOutputData;

public class DiscoverDishesPresenter implements DiscoverDishesOutputBoundary {

    private final DiscoverDishesViewModel discoverDishesViewModel;

    public DiscoverDishesPresenter (DiscoverDishesViewModel discoverDishesViewModel) {
        this.discoverDishesViewModel = discoverDishesViewModel;
    }

    @Override
    public void prepareNoDishFoundView(String info) {
        discoverDishesViewModel.notifyStateChanged(info);

    }

    @Override
    public void prepareOutOfScopeView(String info, int pages) {
        discoverDishesViewModel.notifyStateChanged(info, pages);
    }

    @Override
    public void prepareSuccessView(DiscoverDishesOutputData outputData) {
        String dishName = outputData.getDishName();
        String dishCountry = outputData.getCountry();
        String dishUrl = outputData.getImageUrl();
        int dishIndex = outputData.getIndexOfDish();
        if (dishUrl == null) {
            discoverDishesViewModel.notifyStateChanged(dishName, dishCountry, dishIndex);
        } else {
            discoverDishesViewModel.notifyStateChanged(dishName, dishCountry, dishUrl, dishIndex);
        }
    }

    @Override
    public void prepareNetworkErrorView(ApiException e) {
        discoverDishesViewModel.notifyStateChanged(e);
    }
}
