package data_access;

import entities.Restaurant;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.find_restaurants.Coordinates;
import use_case.find_restaurants.GatewayException;
import use_case.find_restaurants.PlacesGateway;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Finds restaurants near a point using OpenStreetMap's Overpass service.
 */
public class OverpassPlacesGateway implements PlacesGateway {
    private static final String URL = "https://overpass-api.de/api/interpreter";
    private static final String USER_AGENT = "FoodPassport-CSC207/1.0";

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public List<Restaurant> findNearby(Coordinates center, double radiusKm, Set<String> cuisineTags)
        throws GatewayException {
        try {
            final String query = buildQuery(center, radiusKm, cuisineTags);
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL))
                    .header("User-Agent", USER_AGENT)
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(
                            "data=" + URLEncoder.encode(query, StandardCharsets.UTF_8)))
                    .build();
            final HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new GatewayException("Overpass returned status " + response.statusCode(), null);
            }
            return parse(response.body());
        }
        catch (IOException | RuntimeException ex) {
            throw new GatewayException("Restaurant search failed", ex);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new GatewayException("Restaurant search interrupted", ex);
        }
    }

    private String buildQuery(Coordinates center, double radiusKm, Set<String> cuisineTags) {
        final String regex = String.join("|", cuisineTags);
        return String.format(Locale.US,
                "[out:json][timeout:25];"
                    + "node[\"amenity\"=\"restaurant\"][\"cuisine\"~\"%s\"](around:%d,%f,%f);"
                    + "out;",
                regex, (int) (radiusKm * 1000), center.latitude(), center.longitude());
    }

    private List<Restaurant> parse(String body) {
        final JSONArray elements = new JSONObject(body).getJSONArray("elements");
        final List<Restaurant> restaurants = new ArrayList<>();
        for (int i = 0; i < elements.length(); i++) {
            final JSONObject element = elements.getJSONObject(i);
            final JSONObject tags = element.optJSONObject("tags");
            if (tags == null || !tags.has("name")) {
                continue;
            }
            final List<String> cuisines = new ArrayList<>();
            for (String tag: tags.optString("cuisine", "").split(";")) {
                if (!tag.isBlank()) {
                    cuisines.add(tag.trim().toLowerCase(Locale.ROOT));
                }
            }
            restaurants.add(new Restaurant(
                    tags.getString("name"),
                    element.getDouble("lat"),
                    element.getDouble("lon"),
                    cuisines,
                    buildAddress(tags)));
        }
        return restaurants;
    }

    private String buildAddress(JSONObject tags) {
        final String number = tags.optString("addr:housenumber", "");
        final String street = tags.optString("addr:street", "");
        final String address = (number + " " + street).trim();
        return address.isEmpty() ? "Address not listed" : address;
    }
}