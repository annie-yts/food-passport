package data_access;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import use_case.discover_dishes.ApiAccessBoundary;
import entities.Dish;
import entities.DishFactory;
import use_case.discover_dishes.ApiException;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class TheMealDBClient implements ApiAccessBoundary {

    @Override
    public String[] getCountry() {

        // Create http client
        HttpClient client = HttpClient.newHttpClient();


        // Create http request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://www.themealdb.com/api/json/v1/1/list.php?a=list"))
                .GET()
                .build();

        // get response
        try {
            HttpResponse<String> response = client.send(
                request,
                HttpResponse.BodyHandlers.ofString()
            );

            return getJsonInfo(response, "strCountry");
        } catch (IOException e) {
            throw new ApiException("API request failed", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException("API request interrupted", e);
        }

    }

    @Override
    public Dish[] getDish(String target, Boolean isSearchByDish) {
        // Create http client
        HttpClient client = HttpClient.newHttpClient();


        if (isSearchByDish) {
            return searchByDish(target, client);
        } else {
            return searchByCountry(target, client);
        }
    }


    private Dish[] searchByDish(String dishName, HttpClient client) {

        // Create http request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://www.themealdb.com/api/json/v1/1/search.php?s=" +
                                URLEncoder.encode(dishName, StandardCharsets.UTF_8)))
                .GET()
                .build();

        // get response
        try {
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            String[] ids = getJsonInfo(response, "idMeal");
            String[] names = getJsonInfo(response, "strMeal");
            String[] categories = getJsonInfo(response, "strCategory");
            String[] areas = getJsonInfo(response, "strCountry");
            String[] instructions = getJsonInfo(response, "strInstructions");
            String[] mealThumbUrls = getJsonInfo(response, "strMealThumb");
            Dish[] dishes = new Dish[ids.length];
            for (int i = 0; i < ids.length; i++) {
                DishFactory dishFactory = new DishFactory();
                Dish dish = dishFactory.create(
                        ids[i],
                        names[i],
                        areas[i],
                        categories[i],
                        mealThumbUrls[i],
                        instructions[i],
                        false
                );
                dishes[i] = dish;
            }
            return dishes;

        } catch (IOException e) {
            throw new ApiException("API request failed", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException("API request interrupted", e);
        }
    }

    private Dish[] searchByCountry(String country, HttpClient client) {
        // Create http request
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://www.themealdb.com/api/json/v1/1/filter.php?a=" +
                                URLEncoder.encode(country, StandardCharsets.UTF_8)))
                .GET()
                .build();

        // get response
        try {
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
            String[] dishIds = getJsonInfo(response, "idMeal");
            Dish[] dishes = new Dish[dishIds.length];
            for (int i = 0; i < dishIds.length; i++) {
                dishes[i] = searchById(dishIds[i], client);
            }
            return dishes;
        } catch (IOException e) {
            throw new ApiException("API request failed", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException("API request interrupted", e);
        }

    }

    private Dish searchById(String id, HttpClient client) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(
                        "https://themealdb.com/api/json/v1/1/lookup.php?i=" + id))
                .GET()
                .build();
        try {
            HttpResponse<String> response = client.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
            String[] ids = getJsonInfo(response, "idMeal");
            String[] names = getJsonInfo(response, "strMeal");
            String[] categories = getJsonInfo(response, "strCategory");
            String[] areas = getJsonInfo(response, "strCountry");
            String[] instructions = getJsonInfo(response, "strInstructions");
            String[] mealThumbUrls = getJsonInfo(response, "strMealThumb");
            DishFactory dishFactory = new DishFactory();
            return dishFactory.create(
                    ids[0],
                    names[0],
                    areas[0],
                    categories[0],
                    mealThumbUrls[0],
                    instructions[0],
                    false
            );
        } catch (IOException e) {
            throw new ApiException("API request failed", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ApiException("API request interrupted", e);
        }

    }

    private String[] getJsonInfo(HttpResponse<String> response, String target) {
        JsonObject json = JsonParser.parseString(
                response.body()).getAsJsonObject();

        JsonElement element = json.get("meals");

        // avoid throw exception when there are no results found
        if (element == null || element.isJsonNull()) {
            return new String[0];
        }

        JsonArray jsonResults = element.getAsJsonArray();

        //check whether there are results
        if (jsonResults == null) {
            return new String[0];
        }

        String[] results = new String[jsonResults.size()];

        for (int i = 0; i < jsonResults.size(); i++) {
            String result = jsonResults.get(i)
                    .getAsJsonObject()
                    .get(target).getAsString();
            results[i] = result;
        }

        return results;
    }

//
//    public static void main(String[] args) {
//
//        for (String s: GetCountry()) {
//            System.out.println(s);
//        }
//
//    }
}


