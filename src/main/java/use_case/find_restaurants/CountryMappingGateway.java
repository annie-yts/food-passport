package use_case.find_restaurants;

import java.util.Set;

/** TEMPORARY / PLACEHOLDER
 * DELETE LATER WHEN THE REAL VERSION IS SET */
public interface CountryMappingGateway {
    Set<String> osmTagsFor(String isoCode);
}
