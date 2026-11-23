package data_access;

import Entities.Passport;
import use_case.stamp_dish.StampDishDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Keeps passports in memory. Data is lost when the app closes.
 * Temporary until a file-based passport DAO is written.
 */
public class InMemoryPassportDataAccessObject implements StampDishDataAccessInterface {
    private final Map<String, Passport> passports = new HashMap<>();

    @Override
    public Passport getPassport(String userId) {
        return passports.computeIfAbsent(userId, Passport::new);
    }

    @Override
    public void savePassport(Passport passport) {
        passports.put(passport.getUserId(), passport);
    }
}