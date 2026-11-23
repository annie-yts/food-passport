package use_case.find_restaurants;

public class Coordinates {
    private final double latitude;
    private final double longitude;

    public Coordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public double latitude() { return latitude; }

    public double longitude() { return longitude; }
}
