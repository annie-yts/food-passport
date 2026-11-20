package use_case.stamp_dish;

public class StampDishOutputData {
    private final String dishName;
    private final String country;
    private final boolean newCountry;
    private final int totalStamps;
    private final int totalCountries;

    public StampDishOutputData(String dishName, String country, boolean newCountry, int totalStamps, int totalCountries) {
        this.dishName = dishName;
        this.country = country;
        this.newCountry = newCountry;
        this.totalStamps = totalStamps;
        this.totalCountries = totalCountries;
    }

    public String getDishName() {
        return dishName;
    }

    public String getCountry() {
        return country;
    }

    public boolean isNewCountry() {
        return newCountry;
    }

    public int getTotalStamps() {
        return totalStamps;
    }

    public int getTotalCountries() {
        return totalCountries;
    }
}
