package Entities;

public class Dish {
    private String id;
    private String name;
    private String countryCode;
    private String category;
    private String thumbnailUrl;
    private boolean isManual;

    public Dish(String id, String name, String countryCode,
                String category, String thumbnailUrl, boolean isManual) {
        /*if (name == null) {
            throw new NullPointerException("The input dish's name cannot be empty.");
        }
        if (countryCode == null) {
            throw new NullPointerException("The input dish's countryCode cannot be empty.");
        }

         */
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
        this.category = category;
        this.thumbnailUrl = thumbnailUrl;
        this.isManual = isManual;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public boolean isManual() {
        return isManual;
    }

    public void setManual(boolean manual) {
        isManual = manual;
    }


}
