package use_case.discover_dishes;

import entities.Dish;

public interface ApiAccessBoundary {
    Dish[] getDish(String target, Boolean isSearchByDish);
}
