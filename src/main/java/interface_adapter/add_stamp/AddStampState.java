package interface_adapter.add_stamp;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Everything the Add Stamp screen needs to draw itself.
 */
public class AddStampState {
    private String userId = "";
    private List<DishSuggestion> suggestions = new ArrayList<>();
    private boolean manualMode;
    private Map<String, String> countries = new LinkedHashMap<>();
    private String errorMessage = "";
    private String successMessage = "";
    private String passportSummary = "";
    private boolean clearForm;

    public String getUserId() {
        return userId;
    }

    public List<DishSuggestion> getSuggestions() {
        return suggestions;
    }

    public boolean isManualMode() {
        return manualMode;
    }

    public Map<String, String> getCountries() {
        return countries;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public String getPassportSummary() {
        return passportSummary;
    }

    public boolean isClearForm() {
        return clearForm;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSuggestions(List<DishSuggestion> suggestions) {
        this.suggestions = suggestions;
    }

    public void setManualMode(boolean manualMode) {
        this.manualMode = manualMode;
    }

    public void setCountries(Map<String, String> countries) {
        this.countries = countries;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public void setPassportSummary(int stamps, int countriesTried) {
        this.passportSummary = "Passport: " + stamps + " stamps · " + countriesTried + " countries";
    }

    public void setClearForm(boolean clearForm) {
        this.clearForm = clearForm;
    }
}
