package interface_adapter.add_stamp;

/**
 * One dish in the Add Stamp search results, as the screen sees it.
 * A null country code means the user must pick a country from the dropdown.
 */
public class DishSuggestion {
    private final String dishId;
    private final String dishName;
    private final String countryCode;
    private final String countryName;
    private final String category;
    private final String thumbnailUrl;

    /**
     * Creates a suggestion for the screen.
     *
     * @param dishId TheMealDB id
     * @param dishName the dish's name
     * @param countryCode ISO alpha-3 code, or null if unknown
     * @param countryName display name, or null if unknown
     * @param category TheMealDB category
     * @param thumbnailUrl link to the dish's photo
     */
    public DishSuggestion(String dishId, String dishName, String countryCode, String countryName,
                          String category, String thumbnailUrl) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.countryCode = countryCode;
        this.countryName = countryName;
        this.category = category;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getDishId() {
        return dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCategory() {
        return category;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}