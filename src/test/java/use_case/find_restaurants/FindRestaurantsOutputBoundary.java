package use_case.find_restaurants;

public interface FindRestaurantsOutputBoundary {
    void prepareSuccessView(FindRestaurantsOutputData outputData);

    void prepareFailureView(String errorMessage);
}
