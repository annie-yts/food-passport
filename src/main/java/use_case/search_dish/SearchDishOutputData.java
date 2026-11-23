package use_case.search_dish;

import java.util.List;

/**
 * The dishes found by a search, ready for the presenter.
 */
public class SearchDishOutputData {

    /**
     * One search result. A null country means the user must pick one.
     */
    public static class Suggestion {
        private final String dishId;
        private final String dishName;
        private final String country;
        private final String category;
        private final String thumbnailUrl;

        /**
         * Creates a suggestion.
         *
         * @param dishId TheMealDB id
         * @param dishName the dish's name
         * @param country the country, or null if unknown
         * @param category TheMealDB category
         * @param thumbnailUrl link to the dish's photo
         */
        public Suggestion(String dishId, String dishName, String country,
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