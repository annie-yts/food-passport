package Entities;

import java.time.LocalDate;

public class DishStamp {
    private final String id;
    private final Dish dish;
    private final LocalDate date;
    private final PlaceType placeType;
    private final int rating;
    private final String notes;
    private final String restaurantName;

    /**
     * Constructs a DishStamp.
     *
     * @param id the unique ID of the stamp
     * @param dish the dish that was eaten
     * @param date the date when the dish was eaten
     * @param placeType the type of place where the dish was eaten
     * @param rating the rating of the dish (1-5)
     * @param notes additional notes
     * @param restaurantName the restaurant name (optional)
     */
    public DishStamp(String id, Dish dish, LocalDate date,
                     PlaceType placeType, int rating,
                     String notes, String restaurantName) {

        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("ID cannot be empty");
        }
        if (dish == null) {
            throw new IllegalArgumentException("Dish cannot be null");
        }
        if (date == null || date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Invalid date");
        }
        if (placeType == null) {
            throw new IllegalArgumentException("Place type cannot be null");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }

        this.id = id;
        this.dish = dish;
        this.date = date;
        this.placeType = placeType;
        this.rating = rating;
        this.notes = notes;
        this.restaurantName = restaurantName;
    }

    public String getId() {
        return id;
    }

    public Dish getDish() {
        return dish;
    }

    public LocalDate getDate() {
        return date;
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


    public String getCountryCode() {
        return dish.getCountryCode();
    }
}
