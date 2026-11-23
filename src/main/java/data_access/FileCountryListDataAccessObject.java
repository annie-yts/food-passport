package data_access;

import use_case.stamp_dish.CountryListDataAccessInterface;

import java.util.Map;

/**
 * Gives the Stamp Dish use cases the country list from the shared country_mapping.json,
 * by reading it through {@link CountryMapping}.
 */
public class FileCountryListDataAccessObject implements CountryListDataAccessInterface {
    private final CountryMapping countryMapping;

    /**
     * Creates the data access object.
     *
     * @param countryMapping the shared country mapping
     */
    public FileCountryListDataAccessObject(CountryMapping countryMapping) {
        this.countryMapping = countryMapping;
    }

    @Override
    public Map<String, String> getCountryNamesByCode() {
        return countryMapping.getCountryNamesByCode();
    }
}