package use_case.find_restaurants;

import java.util.List;

public class FindRestaurantsOutputData {
    private final List<RestaurantResult> results;

    public FindRestaurantsOutputData(List<RestaurantResult> results) {
        this.results = List.copyOf(results);
    }

    public List<RestaurantResult> results() { return results; }

    public static class RestaurantResult {
        private final String name;
        private final String address;
        private final List<String> matchingTags;
        private final double distanceKm;

        public RestaurantResult(String name, String address, List<String> matchingTags, double distanceKm) {
            this.name = name;
            this.address = address;
            this.matchingTags = List.copyOf(matchingTags);
            this.distanceKm = distanceKm;
        }

        public String name() { return name; }

        public String address() { return address; }

        public List<String> matchingTags() { return matchingTags; }

        public double distanceKm() { return distanceKm; }
    }
}
