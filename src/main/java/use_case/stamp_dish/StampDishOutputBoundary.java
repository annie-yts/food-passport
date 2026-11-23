package use_case.stamp_dish;

public interface StampDishOutputBoundary {
    void prepareSuccessView(StampDishOutputData outputData);
    void prepareFailView(String errorMessage);
}
