package data_access;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import interface_adapter.DataAccessBoundary;


public class TheMealDBClient implements DataAccessBoundary {

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

    public static String[] getJsonInfo (HttpResponse<String> response, String target) {
        JsonObject json = JsonParser.parseString(
                response.body()).getAsJsonObject();

        JsonArray jsonResults = json
                .getAsJsonArray("meals");

        //check whether there are results
        if (jsonResults.isEmpty()) {
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


