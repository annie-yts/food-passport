package use_case.discover_dishes;

import entities.Dish;

public class DiscoverDishesInteractor implements DiscoverDishesInputBoundary{
    private final ApiAccessBoundary mealDBObject;
    private final DiscoverDishesOutputBoundary discoverDishesPresenter;

    public DiscoverDishesInteractor(ApiAccessBoundary mealDBObject,
                                    DiscoverDishesOutputBoundary discoverDishesPresenter) {
        this.mealDBObject = mealDBObject;
        this.discoverDishesPresenter = discoverDishesPresenter;
    }

    public void execute(DiscoverDishesInputData discoverDishesInputData) {
        String searchTarget = discoverDishesInputData.getSearchTarget();
        Boolean isSearchByDish = discoverDishesInputData.getSearchByDish();
        int page = discoverDishesInputData.getPage();
        try {
            Dish[] dishes = mealDBObject.getDish(searchTarget, isSearchByDish);
            int dishStartIndex =  6 * (page - 1); // 6 dishes per page;

            if (dishes.length == 0) {
                discoverDishesPresenter.prepareNoDishFoundView("No Dish Found!");
            }
            else if (dishStartIndex >= dishes.length) {
                int numPages = (dishes.length - 1) / 6 + 1;
                discoverDishesPresenter.prepareOutOfScopeView(
                        "Out of scope! The overall pages are ", numPages);
            }
            else {
                int dishIndex = 0;
                for (int i = dishStartIndex; (i < dishStartIndex + 6) && i < dishes.length; i++) {
                    String dishName = dishes[i].getName();
                    String countryCode = dishes[i].getCountryCode();
                    String dishUrl = dishes[i].getThumbnailUrl();
                    if (dishUrl == null) {
                        DiscoverDishesOutputData outputData = new DiscoverDishesOutputData(dishName,
                                countryCode,
                                dishIndex);
                        discoverDishesPresenter.prepareSuccessView(outputData);
                    } else {
                        DiscoverDishesOutputData outputData = new DiscoverDishesOutputData(dishName,
                            countryCode,
                            dishUrl,
                            dishIndex);
                        discoverDishesPresenter.prepareSuccessView(outputData);

                    }
                    dishIndex++;
                }
            }

        } catch (ApiException e) {
            discoverDishesPresenter.prepareNetworkErrorView(e);
        }
    }
}
