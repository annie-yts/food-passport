package use_case.stamp_dish;

import java.util.Map;

/**
 * Gives access to the countries a dish can be stamped under.
 */
public interface CountryListDataAccessInterface {

    /**
     * Returns every known country, keyed by ISO alpha-3 code.
     *
     * @return a map from ISO code (e.g. "THA") to display name (e.g. "Thailand")
     */
    Map<String, String> getCountryNamesByCode();
}