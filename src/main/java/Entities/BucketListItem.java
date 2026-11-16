package Entities;

import java.time.LocalDate;

public class BucketListItem {
    private final String id;
    private final Dish dish;
    private final LocalDate addedOn;
    private boolean completed = false;

    public BucketListItem(String id, Dish dish, LocalDate addedOn) {
        this.id = id;
        this.dish = dish;
        this.addedOn = addedOn;
    }

    public String getId(){
        return this.id;
    }

    public Dish getDish(){
        return this.dish;
    }

    public LocalDate getAddedOn(){
        return this.addedOn;
    }

    public void markCompleted(){
        this.completed = true;
    }

    public boolean isCompleted(){
        return this.completed;
    }
}
