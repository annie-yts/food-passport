package data_access;

import org.json.JSONArray;
import org.json.JSONObject;
import use_case.find_restaurants.Coordinates;
import use_case.find_restaurants.GatewayException;
import use_case.find_restaurants.GeocodingGateway;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

/**
 * Looks up coordinates for an address using OpenStreetMap's Nominatim service.
 */
public class NominatimGeocodingGateway implements GeocodingGateway {
    private static final String URL = "https://nominatim.openstreetmap.org/search";
    private static final String USER_AGENT = "FoodPassport-CSC207/1.0";

    private final HttpClient client = HttpClient.newHttpClient();

    @Override
    public Optional<Coordinates> geocode(String address) throws GatewayException {
        try {
            final String query = URLEncoder.encode(address, StandardCharsets.UTF_8);
            final HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(URL + "?q=" + query + "&format=json&limit=1"))
                    .header("User-Agent", USER_AGENT)
                    .GET()
                    .build();
            final HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new GatewayException("Nominatim returned status " + response.statusCode(), null);
            }

            final JSONArray results = new JSONArray(response.body());
            if (results.isEmpty()) {
                return Optional.empty();
            }
            final JSONObject first = results.getJSONObject(0);
            return Optional.of(new Coordinates(
                    Double.parseDouble(first.getString("lat")),
                    Double.parseDouble(first.getString("lon"))));
        }
        catch (IOException | RuntimeException ex) {
            throw new GatewayException("Geocoding failed", ex);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new GatewayException("Geocoding interrupted", ex);
        }
    }
}
