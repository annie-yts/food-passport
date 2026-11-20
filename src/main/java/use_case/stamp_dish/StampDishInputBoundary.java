package use_case.stamp_dish;

public interface StampDishInputBoundary {
    /**
     * Validates the input and, if valid, saves a new dish stamp.
     *
     * @param inputData what the user entered in the Add Stamp form
     */
    void execute(StampDishInputData inputData);
}
