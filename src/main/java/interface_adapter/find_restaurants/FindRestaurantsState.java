package interface_adapter.find_restaurants;

import java.util.ArrayList;
import java.util.List;

public class FindRestaurantsState {
    private String isoCode = "";
    private String address = "";
    private double radiusKm = 2.0;
    private List<String> resultLines = new ArrayList<>();
    private String errorMessage = "";

    public String getIsoCode() { return isoCode; }
    public void setIsoCode(String isoCode) { this.isoCode = isoCode; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public double getRadiusKm() { return radiusKm; }
    public void setRadiusKm(double radiusKm) { this.radiusKm = radiusKm; }

    public List<String> getResultLines() { return resultLines; }
    public void setResultLines(List<String> resultLines) { this.resultLines = resultLines; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
}
