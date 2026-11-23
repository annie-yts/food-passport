package use_case.stamp_dish;

import entities.PlaceType;

public class StampDishInputData {
    private final String userId;
    private final String dishId;
    private final String dishName;
    private final String country;
    private final String category;
    private final String thumbnailUrl;
    private final String dateText;
    private final PlaceType placeType;
    private final int rating;
    private final String notes;
    private final String restaurantName;

    public StampDishInputData(String userId, String dishId, String dishName, String country, String category, String thumbnailUrl, String dateText, PlaceType placeType, int rating, String notes, String restaurantName) {
        this.userId = userId;
        this.dishId = dishId;
        this.dishName = dishName;
        this.country = country;
        this.category = category;
        this.thumbnailUrl = thumbnailUrl;
        this.dateText = dateText;
        this.placeType = placeType;
        this.rating = rating;
        this.notes = notes;
        this.restaurantName = restaurantName;
    }

    public String getUserId() {
        return userId;
    }

    public String getDishId() {
        return dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public String getCountry() {
        return country;
    }

    public String getCategory() {
        return category;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getDateText() {
        return dateText;
    }

    public PlaceType getPlaceType() {
        return placeType;
    }

    public int getRating() {
        return rating;
    }

    public String getNotes() {
        return notes;
    }

    public String getRestaurantName() {
        return restaurantName;
    }
}
