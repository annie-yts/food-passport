package use_case.discover_dishes;

public interface DiscoverDishesOutputBoundary {
    void prepareSuccessView(DiscoverDishesOutputData outputData);

    void prepareOutOfScopeView(String info, int pages);

    void prepareNoDishFoundView(String info);

    void prepareNetworkErrorView(ApiException e);
}
