package use_case;

import entities.Dish;

public interface ApiAccessBoundary {
    String[] getCountry();

    Dish[] getDish(String target, Boolean isSearchByDish);
}
