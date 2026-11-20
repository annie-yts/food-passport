package interface_adapter;

import entities.Dish;

public interface ApiAccessBoundary {
    String[] getCountry();

    Dish[] getDish(String target, Boolean isSearchByDish);
}
