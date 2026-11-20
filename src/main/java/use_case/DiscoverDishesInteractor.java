package use_case;

import entities.Dish;
import interface_adapter.DiscoverDishesInputData;

public class DiscoverDishesInteractor implements DiscoverDishesInputBoundary{
    private ApiAccessBoundary mealDBObject;

    public DiscoverDishesInteractor(ApiAccessBoundary mealDBObject) {
        this.mealDBObject = mealDBObject;
    }

    public void execute(DiscoverDishesInputData discoverDishesInputData) {
        String searchTarget = discoverDishesInputData.getSearchTarget();
        Boolean isSearchByDish = discoverDishesInputData.getSearchByDish();
        int page = discoverDishesInputData.getPage();
        Dish[] dishes = mealDBObject.getDish(searchTarget, isSearchByDish);
        for (Dish dish: dishes) {
            System.out.println(dish.getName());
        }
        if (dishes.length == 0) {
            System.out.println("No dish found!");
        }
    }
}
