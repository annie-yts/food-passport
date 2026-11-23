package use_case.discover_dishes;

import Entities.Dish;

public interface ApiAccessBoundary {
    Dish[] getDish(String target, Boolean isSearchByDish);
}
