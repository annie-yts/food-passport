package interface_adapter.discover_dishes;

import use_case.discover_dishes.ApiException;

public interface DiscoverDishesViewModelListener {

    void stateChanged(String dishName, String countryCode, int dishIndex);
    void stateChanged(String dishName, String countryCode, String imageUrl, int dishIndex);
    void stateChanged(String info);
    void stateChanged(String info, int pages);
    void stateChanged(ApiException e);
}
