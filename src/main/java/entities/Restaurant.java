package entities;

import java.util.List;

public class Restaurant {
    private static final double EARTH_RADIUS_KM = 6371.0;

    private final String name;
    private final double latitude;
    private final double longitude;
    private final List<String> cuisineTags;
    private final String address;

    public Restaurant(String name, double latitude, double longitude, List<String> cuisineTags, String address) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cuisineTags = List.copyOf(cuisineTags); // immutable copy
        this.address = address;
    }

    public String getName() { return name; }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public List<String> getCuisineTags() { return cuisineTags; }

    public String getAddress() { return address; }

    public double distanceTo(double lat, double lon) {
        double dLat = Math.toRadians(lat - latitude);
        double dLon = Math.toRadians(lon - longitude);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(latitude)) * Math.cos(Math.toRadians(lat))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        return EARTH_RADIUS_KM * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }
}
