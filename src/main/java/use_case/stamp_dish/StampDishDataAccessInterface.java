package use_case.stamp_dish;

import Entities.Passport;

/**
 * Loads and saves passports for the Stamp Dish use case.
 */
public interface StampDishDataAccessInterface {

    /**
     * Returns the passport for the given user.
     *
     * @param userId the user whose passport to load
     * @return their passport, or a new empty passport if they have none yet
     */
    Passport getPassport(String userId);

    /**
     * Saves the given passport, replacing any earlier version.
     *
     * @param passport the passport to save
     */
    void savePassport(Passport passport);
}