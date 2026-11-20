package use_case.discover_dishes;

import entities.Dish;

public interface ApiAccessBoundary {
    String[] getCountry();

    Dish[] getDish(String target, Boolean isSearchByDish);
}
