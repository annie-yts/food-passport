package Entities;

/**
 * Factory for creating Dish objects.
 */
public class DishFactory {
    public Dish create(String id,
                       String name,
                       String country,
                       String category,
                       String thumbnailUrl,
                       String instruction,
                       Boolean isManual) {
        return new Dish(id, name, country, category, thumbnailUrl, instruction, isManual);
    }
}


