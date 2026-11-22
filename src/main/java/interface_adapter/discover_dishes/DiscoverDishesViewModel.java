/**
 * The View Model for DiscoverDishesView
 */
package interface_adapter.discover_dishes;

import use_case.discover_dishes.ApiException;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class DiscoverDishesViewModel {
    public static final String TITLE_LABEL = "Discover Dishes";
    public static final String PREV_PAGE_LABEL = "Prev";
    public static final String NEXT_PAGE_LABEL = "Next";
    public static final String BUCKET_LIST_LABEL = "View my bucket list";
    public static final String NEARBY_RESTAURANT_LABEL = "Find restaurants nearby";
    public static final String COUNTRY_LABEL = "Search by country";
    public static final String SEARCH_BUTTON_LABEL = "Search";
    public static final String DISH_INPUT_LABEL = "or dish:";
    public static final String APPLY_LABEL = "apply";

    private final List<DiscoverDishesViewModelListener> listeners = new ArrayList<>();


    private DiscoverDishesState state = new DiscoverDishesState();

    public DiscoverDishesState getState() {
        return this.state;
    }

    public void setState(DiscoverDishesState state) {
        this.state = state;
    }

    public void addListener(DiscoverDishesViewModelListener listener) {
        listeners.add(listener);
    }


    public void notifyStateChanged(String dishName, String country, String imageUrl, int dishIndex) {
        for (DiscoverDishesViewModelListener listener : listeners) {
            listener.stateChanged(dishName, country, imageUrl, dishIndex);
        }
    }

    public void notifyStateChanged(String dishName, String country, int dishIndex) {
        for (DiscoverDishesViewModelListener listener : listeners) {
            listener.stateChanged(dishName, country, dishIndex);
        }
    }

    public void notifyStateChanged(String info) {
        for (DiscoverDishesViewModelListener listener : listeners) {
            listener.stateChanged(info);
        }
    }

    public void notifyStateChanged(String info, int pages) {
        for (DiscoverDishesViewModelListener listener : listeners) {
            listener.stateChanged(info, pages);
        }
    }

    public void notifyStateChanged(ApiException e) {
        for (DiscoverDishesViewModelListener listener : listeners) {
            listener.stateChanged(e);
        }
    }


}
