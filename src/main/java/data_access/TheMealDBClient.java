package data_access;

import Entities.Dish;
import Entities.DishFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import use_case.discover_dishes.ApiAccessBoundary;
import use_case.discover_dishes.ApiException;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class TheMealDBClient implements ApiAccessBoundary {
    private static final URI API_BASE = URI.create("https://www.themealdb.com/api/json/v1/1/");
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(15);

    private final HttpClient client = HttpClient.newHttpClient();
    private final DishFactory dishFactory = new DishFactory();
    private final CountryMapping countries;
    private final URI baseUri;

    public TheMealDBClient(CountryMapping countries) {
        this(countries, API_BASE);
    }

    TheMealDBClient(CountryMapping countries, URI baseUri) {
        this.countries = countries;
        this.baseUri = baseUri;
    }

    @Override
    public Dish[] getDish(String target, Boolean isSearchByDish) {
        List<Dish> dishes = new ArrayList<>();
        try {
            if (isSearchByDish) {
                addDishes(dishes, requestMeals("search.php?s=", target), null);
            }
            else {
                String area = countries.areaFor(target);
                if (area != null) {
                    JSONArray matches = requestMeals("filter.php?a=", area);
                    for (int i = 0; i < matches.length(); i++) {
                        String id = matches.getJSONObject(i).getString("idMeal");
                        addDishes(dishes, requestMeals("lookup.php?i=", id), target);
                    }
                }
            }
        }
        catch (JSONException ex) {
            throw new ApiException("Invalid TheMealDB response", ex);
        }
        return dishes.toArray(new Dish[0]);
    }

    private void addDishes(List<Dish> dishes, JSONArray meals, String requestedCountryCode) {
        for (int i = 0; i < meals.length(); i++) {
            JSONObject meal = meals.getJSONObject(i);
            String countryCode = countries.countryCodeFor(meal.optString("strCountry"), meal.optString("strArea"));
            // The same area filter may return meals from more than one country.
            if (countryCode != null && (requestedCountryCode == null || requestedCountryCode.equals(countryCode))) {
                dishes.add(dishFactory.create(meal.getString("idMeal"), meal.getString("strMeal"), countryCode,
                        meal.optString("strCategory", null), meal.optString("strMealThumb", null),
                        meal.optString("strInstructions", null), false));
            }
        }
    }

    private JSONArray requestMeals(String endpoint, String value) {
        URI uri = baseUri.resolve(endpoint + URLEncoder.encode(value, StandardCharsets.UTF_8));
        HttpRequest request = HttpRequest.newBuilder(uri).timeout(REQUEST_TIMEOUT).GET().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                throw new IOException("TheMealDB returned HTTP " + response.statusCode());
            }
            JSONObject body = new JSONObject(response.body());
            return body.isNull("meals") ? new JSONArray() : body.getJSONArray("meals");
        }
        catch (IOException ex) {
            throw new ApiException("TheMealDB request failed", ex);
        }
        catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new ApiException("TheMealDB request interrupted", ex);
        }
    }
}
