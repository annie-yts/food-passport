package data_access;

import use_case.find_restaurants.CountryMappingGateway;

import java.util.LinkedHashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/**
 * Turns a country's TheMealDB area label into OpenStreetMap cuisine tags.
 */
public class AreaCountryMappingGateway implements CountryMappingGateway {
    private static final Map<String, Set<String>> EXTRA_TAGS = Map.of(
            "japanese", Set.of("sushi", "ramen"),
            "italian", Set.of("pizza", "pasta"),
            "chinese", Set.of("dim_sum"),
            "mexican", Set.of("tacos"));

    private final CountryMapping countryMapping;

    public AreaCountryMappingGateway(CountryMapping countryMapping) {
        this.countryMapping = countryMapping;
    }

    @Override
    public Set<String> osmTagsFor(String isoCode) {
        final String area = countryMapping.areaFor(isoCode);
        if (area == null || area.isBlank() || "unknown".equalsIgnoreCase(area)) {
            return Set.of();
        }
        final String base = area.trim().toLowerCase(Locale.ROOT).replace(' ', '_');
        final Set<String> tags = new LinkedHashSet<>();
        tags.add(base);
        tags.addAll(EXTRA_TAGS.getOrDefault(base, Set.of()));
        return tags;
    }
}
