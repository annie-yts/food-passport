package Entities;

public class Dish {
    private final String id;
    private final String name;
    private final String country;
    private final String category;
    private final String thumbnailUrl;
    private final String instruction;
    private final boolean isManual;

    public Dish(String id, String name, String country,
                String category, String thumbnailUrl, String instruction, boolean isManual) {
        if (name == null) {
            throw new NullPointerException("The input dish's name cannot be empty.");
        }
        if (country == null) {
            throw new NullPointerException("The input dish's country cannot be empty.");
        }

        this.id = id;
        this.name = name;
        this.country = country;
        this.category = category;
        this.thumbnailUrl = thumbnailUrl;
        this.isManual = isManual;
        this.instruction = instruction;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public String getInstruction() {
        return instruction;
    }
    public boolean isManual() {
        return isManual;
    }

}
