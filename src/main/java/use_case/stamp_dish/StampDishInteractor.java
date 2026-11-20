package use_case.stamp_dish;

import entities.Dish;
import entities.DishStamp;
import entities.Passport;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.UUID;

/**
 * Interactor for the Stamp Dish use case. Checks the user's input, adds a
 * dish stamp to their passport, and reports whether it's a new country.
 */
public class StampDishInteractor implements StampDishInputBoundary {
    private final StampDishDataAccessInterface passportDataAccess;
    private final CountryListDataAccessInterface countryList;
    private final StampDishOutputBoundary presenter;

    /**
     * Creates the interactor.
     *
     * @param passportDataAccess loads and saves passports
     * @param countryList the list of valid countries
     * @param presenter receives the success or failure result
     */
    public StampDishInteractor(StampDishDataAccessInterface passportDataAccess,
                               CountryListDataAccessInterface countryList, StampDishOutputBoundary presenter) {
        this.passportDataAccess = passportDataAccess;
        this.countryList = countryList;
        this.presenter = presenter;
    }

    @Override
    public void execute(StampDishInputData input) {
        if (input.getDishName() == null || input.getDishName().isBlank()) {
            presenter.prepareFailView("Enter a dish name.");
            return;
        }
        if (input.getCountry() == null || !countryList.getAllCountries().contains(input.getCountry())) {
            presenter.prepareFailView("Pick a country.");
            return;
        }
        if (input.getRating() < 1 || input.getRating() > 5) {
            presenter.prepareFailView("Invalid rating. Rating must be between 1 and 5.");
            return;
        }
        LocalDate date;
        try {
            date = LocalDate.parse(input.getDateText().trim());
        } catch (DateTimeParseException e) {
            presenter.prepareFailView("Invalid date. Date must be in format yyyy-mm-dd.");
            return;
        }
        if (date.isAfter(LocalDate.now())) {
            presenter.prepareFailView("Invalid date. Date can't be in the future.");
            return;
        }
        boolean manual = input.getDishId() == null;
        String dishId;
        if (manual) {
            dishId = "manual-" + UUID.randomUUID();
        } else {
            dishId = input.getDishId();
        }
        Dish dish = new Dish(dishId, input.getDishName(), input.getCountry(), input.getCategory(),
                input.getThumbnailUrl(), "", manual);
        Passport passport = passportDataAccess.getPassport(input.getUserId());
        boolean isNew = !passport.hasTried(input.getCountry());
        DishStamp dishStamp = new DishStamp(UUID.randomUUID().toString(), dish, date, input.getPlaceType(),
                input.getRating(), input.getNotes(), input.getRestaurantName());
        passport.addStamp(dishStamp);
        passportDataAccess.savePassport(passport);
        StampDishOutputData outputData = new StampDishOutputData(input.getDishName(), input.getCountry(), isNew,
                passport.getStamps().size(), passport.countriesTried().size());
        presenter.prepareSuccessView(outputData);
    }
}
