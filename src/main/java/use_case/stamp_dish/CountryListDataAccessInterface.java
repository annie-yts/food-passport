package use_case.stamp_dish;

import java.util.List;

/**
 * Gives access to the list of countries a dish can be stamped under.
 */
public interface CountryListDataAccessInterface {

    /**
     * Returns every country name the app knows about.
     *
     * @return an unmodifiable list of country names
     */
    List<String> getAllCountries();
}
