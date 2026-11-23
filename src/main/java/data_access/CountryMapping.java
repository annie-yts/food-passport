package data_access;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

/** Translates between TheMealDB labels and the ISO alpha-3 codes used by passports. */
public final class CountryMapping {
    private final Map<String, String> namesByCode = new LinkedHashMap<>();
    private final Map<String, String> areasByCode = new LinkedHashMap<>();
    private final Map<String, String> codesByName = new LinkedHashMap<>();
    private final Map<String, String> codesByArea = new LinkedHashMap<>();

    public CountryMapping() {
        try (InputStream input = CountryMapping.class.getResourceAsStream("/country_mapping.json")) {
            if (input == null) {
                throw new IllegalStateException("Missing bundled country_mapping.json");
            }
            JSONArray rows = new JSONArray(new JSONTokener(input));
            for (int i = 0; i < rows.length(); i++) {
                JSONObject row = rows.getJSONObject(i);
                String code = row.getString("countryCode");
                String name = row.getString("name");
                String area = row.getString("area");
                namesByCode.put(code, name);
                areasByCode.put(code, area);
                codesByName.put(name, code);
                // Labels such as "Dominican" need an explicit country to disambiguate them.
                codesByArea.merge(area, code, (existing, added) -> "");
            }
        }
        catch (IOException ex) {
            throw new IllegalStateException("Could not read bundled country_mapping.json", ex);
        }
    }

    public Map<String, String> getCountryNamesByCode() {
        return Collections.unmodifiableMap(namesByCode);
    }

    String areaFor(String countryCode) {
        return areasByCode.get(countryCode);
    }

    String countryCodeFor(String name, String area) {
        if (!name.isBlank()) {
            return codesByName.get(name);
        }
        String code = codesByArea.getOrDefault(area, "");
        return code.isEmpty() ? null : code;
    }
}
