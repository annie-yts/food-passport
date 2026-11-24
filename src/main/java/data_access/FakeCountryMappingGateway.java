package data_access;

import use_case.find_restaurants.CountryMappingGateway;

import java.util.Set;

public class FakeCountryMappingGateway implements CountryMappingGateway {
    @Override
    public Set<String> osmTagsFor(String isoCode) {
        return Set.of("japanese");
    }
}
