package use_case.discover_dishes;

public class DiscoverDishesOutputData {
    private int indexOfDish;
    private String dishName;
    private String country;
    private String imageUrl = null;

    public DiscoverDishesOutputData(String dishName, String country, int indexOfDish) {
        this.dishName = dishName;
        this.country = country;
        this.indexOfDish = indexOfDish;
    }

    public DiscoverDishesOutputData(String dishName, String country, String imageUrl, int indexOfDish) {
        this.dishName = dishName;
        this.country = country;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
