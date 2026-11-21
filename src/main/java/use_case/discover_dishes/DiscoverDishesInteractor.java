package use_case.discover_dishes;

import entities.Dish;
import interface_adapter.discover_dishes.DiscoverDishesInputData;

public class DiscoverDishesInteractor implements DiscoverDishesInputBoundary{
    private final ApiAccessBoundary mealDBObject;

    public DiscoverDishesInteractor(ApiAccessBoundary mealDBObject) {
        this.mealDBObject = mealDBObject;
    }

    public void execute(DiscoverDishesInputData discoverDishesInputData) {
        String searchTarget = discoverDishesInputData.getSearchTarget();
        Boolean isSearchByDish = discoverDishesInputData.getSearchByDish();
        int page = discoverDishesInputData.getPage();
        try {
        Dish[] dishes = mealDBObject.getDish(searchTarget, isSearchByDish);
        for (Dish dish: dishes) {
            System.out.println(dish.getName());
        }
        if (dishes.length == 0) {
            System.out.println("No dish found!");
        }
        } catch (ApiException e) {
            System.out.println("Network error. Please try again later.");
        }
    }
}
