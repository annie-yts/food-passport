package use_case.find_restaurants;

public class FindRestaurantsInputData {
    private final String isoCode;
    private final String address;
    private final double radiusKm;

    public FindRestaurantsInputData(String isoCode, String address, double radiusKm) {
        this.isoCode = isoCode;
        this.address = address;
        this.radiusKm = radiusKm;
    }

    public String isoCode() { return isoCode; }

    public String address() { return address; }

    public double radiusKm() { return radiusKm; }
}
