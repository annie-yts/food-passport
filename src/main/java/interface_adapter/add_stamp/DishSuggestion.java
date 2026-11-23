package interface_adapter.add_stamp;

/**
 * One dish in the Add Stamp search results, as the screen sees it.
 * A null country means the user must pick one from the dropdown.
 */
public class DishSuggestion {
    private final String dishId;
    private final String dishName;
    private final String country;
    private final String category;
    private final String thumbnailUrl;

    /**
     * Creates a suggestion for the screen.
     *
     * @param dishId TheMealDB id
     * @param dishName the dish's name
     * @param country the country, or null if unknown
     * @param category TheMealDB category
     * @param thumbnailUrl link to the dish's photo
     */
    public DishSuggestion(String dishId, String dishName, String country,
                          String category, String thumbnailUrl) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.country = country;
        this.category = category;
        this.thumbnailUrl = thumbnailUrl;
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
}