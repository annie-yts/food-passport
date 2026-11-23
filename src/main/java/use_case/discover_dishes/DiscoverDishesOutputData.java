package use_case.discover_dishes;

public class DiscoverDishesOutputData {
    private int indexOfDish;
    private String dishName;
    private String countryCode;
    private String imageUrl = null;

    public DiscoverDishesOutputData(String dishName, String countryCode, int indexOfDish) {
        this.dishName = dishName;
        this.countryCode = countryCode;
        this.indexOfDish = indexOfDish;
    }

    public DiscoverDishesOutputData(String dishName, String countryCode, String imageUrl, int indexOfDish) {
        this.dishName = dishName;
        this.countryCode = countryCode;
        this.imageUrl = imageUrl;
        this.indexOfDish = indexOfDish;
    }

    public int getIndexOfDish() {
        return indexOfDish;
    }

    public void setIndexOfDish(int indexOfDish) {
        this.indexOfDish = indexOfDish;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
