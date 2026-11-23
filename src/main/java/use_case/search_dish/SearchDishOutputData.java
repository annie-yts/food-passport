package use_case.search_dish;

import java.util.List;

/**
 * The dishes found by a search, ready for the presenter.
 */
public class SearchDishOutputData {

    /**
     * One search result. A null country code means the user must pick a country.
     */
    public static class Suggestion {
        private final String dishId;
        private final String dishName;
        private final String countryCode;
        private final String countryName;
        private final String category;
        private final String thumbnailUrl;

        /**
         * Creates a suggestion.
         *
         * @param dishId TheMealDB id
         * @param dishName the dish's name
         * @param countryCode ISO alpha-3 code (e.g. "THA"), or null if unknown
         * @param countryName display name (e.g. "Thailand"), or null if unknown
         * @param category TheMealDB category
         * @param thumbnailUrl link to the dish's photo
         */
        public Suggestion(String dishId, String dishName, String countryCode, String countryName,
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

    private final List<Suggestion> suggestions;

    /**
     * Creates the output data.
     *
     * @param suggestions the dishes found
     */
    public SearchDishOutputData(List<Suggestion> suggestions) {
        this.suggestions = List.copyOf(suggestions);
    }

    public List<Suggestion> getSuggestions() {
        return suggestions;
    }
}