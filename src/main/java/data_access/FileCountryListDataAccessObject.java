package data_access;

import use_case.stamp_dish.CountryListDataAccessInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Loads the list of country names from the bundled countries.txt file.
 */
public class FileCountryListDataAccessObject implements CountryListDataAccessInterface {
    private final List<String> countries;

    /**
     * Reads countries.txt once when the object is created.
     *
     * @throws IllegalStateException if the file can't be read
     */
    public FileCountryListDataAccessObject() {
        try {
            List<String> lines = Files.readAllLines(Path.of("src/main/resources/countries.txt"));
            countries = List.copyOf(lines);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read countries.txt", e);
        }
    }

    @Override
    public List<String> getAllCountries() {
        return countries;
    }
}
