package interface_adapter.add_stamp;

import entities.PlaceType;
import use_case.stamp_dish.StampDishInputBoundary;
import use_case.stamp_dish.StampDishInputData;

/**
 * Takes what the user entered in the Add Stamp form and passes it to the use case.
 */
public class StampDishController {
    private final StampDishInputBoundary interactor;

    /**
     * Creates the controller.
     *
     * @param interactor the Stamp Dish use case
     */
    public StampDishController(StampDishInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Asks the use case to save a stamp.
     *
     * @param userId the logged-in user
     * @param dishId TheMealDB id, or null if typed manually
     * @param dishName the dish's name
     * @param country the dish's country
     * @param category TheMealDB category, or null
     * @param thumbnailUrl link to the dish's photo, or null
     * @param dateText the date as typed, e.g. 2026-11-22
     * @param placeType where it was eaten
     * @param rating 1 to 5
     * @param notes any notes
     * @param restaurantName the restaurant, if eaten at one
     */
    public void stamp(String userId, String dishId, String dishName, String country,
                      String category, String thumbnailUrl, String dateText,
                      PlaceType placeType, int rating, String notes, String restaurantName) {
        StampDishInputData inputData = new StampDishInputData(userId, dishId, dishName, country, category, thumbnailUrl,
                dateText, placeType, rating, notes, restaurantName);
        interactor.execute(inputData);
    }
}