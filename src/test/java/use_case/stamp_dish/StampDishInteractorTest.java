package use_case.stamp_dish;

import Entities.Passport;
import Entities.PlaceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class StampDishInteractorTest {

    /** Only knows two countries. */
    static class FakeCountryList implements CountryListDataAccessInterface {
        @Override
        public List<String> getAllCountries() {
            return List.of("Thailand", "Ethiopia");
        }
    }

    /** Stores passports in a map instead of a file. */
    static class FakePassportDataAccess implements StampDishDataAccessInterface {
        final Map<String, Passport> passports = new HashMap<>();

        @Override
        public Passport getPassport(String userId) {
            return passports.computeIfAbsent(userId, Passport::new);
        }

        @Override
        public void savePassport(Passport passport) {
            passports.put(passport.getUserId(), passport);
        }
    }

    /** Remembers what the interactor told it. */
    static class FakePresenter implements StampDishOutputBoundary {
        StampDishOutputData lastSuccess;
        String lastError;

        @Override
        public void prepareSuccessView(StampDishOutputData outputData) {
            lastSuccess = outputData;
        }

        @Override
        public void prepareFailView(String errorMessage) {
            lastError = errorMessage;
        }
    }

    private FakePassportDataAccess dataAccess;
    private FakePresenter presenter;
    private StampDishInteractor interactor;

    @BeforeEach
    void setUp() {
        dataAccess = new FakePassportDataAccess();
        presenter = new FakePresenter();
        interactor = new StampDishInteractor(dataAccess, new FakeCountryList(), presenter);
    }

    /** Builds an input with sensible defaults, so each test only changes what it cares about. */
    private StampDishInputData input(String dishId, String dishName, String country,
                                     String date, int rating) {
        return new StampDishInputData("demo-user", dishId, dishName, country,
                null, null, date, PlaceType.HOME, rating, "", "");
    }

    @Test
    void firstStampFromCountryIsNew() {
        interactor.execute(input("53191", "Pad Thai", "Thailand", "2026-11-01", 5));

        assertNull(presenter.lastError);
        assertTrue(presenter.lastSuccess.isNewCountry());
        assertEquals(1, presenter.lastSuccess.getTotalStamps());
    }

    @Test
    void secondStampSameCountryIsNotNew() {
        interactor.execute(input("53191", "Pad Thai", "Thailand", "2026-11-01", 5));
        interactor.execute(input("52774", "Pad See Ew", "Thailand", "2026-11-02", 4));

        assertFalse(presenter.lastSuccess.isNewCountry());
        assertEquals(2, presenter.lastSuccess.getTotalStamps());
        assertEquals(1, presenter.lastSuccess.getTotalCountries());
    }

    @Test
    void twoCountriesCounted() {
        interactor.execute(input("53191", "Pad Thai", "Thailand", "2026-11-01", 5));
        interactor.execute(input(null, "Injera", "Ethiopia", "2026-11-02", 4));

        assertTrue(presenter.lastSuccess.isNewCountry());
        assertEquals(2, presenter.lastSuccess.getTotalCountries());
    }

    @Test
    void futureDateFails() {
        interactor.execute(input("53191", "Pad Thai", "Thailand", "2099-01-01", 5));

        assertNotNull(presenter.lastError);
        assertNull(presenter.lastSuccess);
    }

    @Test
    void badDateTextFails() {
        interactor.execute(input("53191", "Pad Thai", "Thailand", "nov 3", 5));

        assertNotNull(presenter.lastError);
        assertNull(presenter.lastSuccess);
    }

    @Test
    void ratingTooLowFails() {
        interactor.execute(input("53191", "Pad Thai", "Thailand", "2026-11-01", 0));

        assertNotNull(presenter.lastError);
        assertNull(presenter.lastSuccess);
    }

    @Test
    void ratingTooHighFails() {
        interactor.execute(input("53191", "Pad Thai", "Thailand", "2026-11-01", 6));

        assertNotNull(presenter.lastError);
        assertNull(presenter.lastSuccess);
    }

    @Test
    void unknownCountryFails() {
        interactor.execute(input(null, "Turkish Delight", "Narnia", "2026-11-01", 5));

        assertNotNull(presenter.lastError);
        assertNull(presenter.lastSuccess);
    }

    @Test
    void nullCountryFails() {
        interactor.execute(input(null, "Mystery Dish", null, "2026-11-01", 5));

        assertNotNull(presenter.lastError);
        assertNull(presenter.lastSuccess);
    }

    @Test
    void blankDishNameFails() {
        interactor.execute(input(null, "   ", "Thailand", "2026-11-01", 5));

        assertNotNull(presenter.lastError);
        assertNull(presenter.lastSuccess);
    }

    @Test
    void failedStampIsNotSaved() {
        interactor.execute(input("53191", "Pad Thai", "Thailand", "2026-11-01", 0));

        assertEquals(0, dataAccess.getPassport("demo-user").getStamps().size());
    }

    @Test
    void manualDishGetsGeneratedId() {
        interactor.execute(input(null, "Injera", "Ethiopia", "2026-11-01", 4));

        var stamp = dataAccess.getPassport("demo-user").getStamps().get(0);
        assertTrue(stamp.getDish().isManual());
        assertTrue(stamp.getDish().getId().startsWith("manual-"));
    }

    @Test
    void searchedDishKeepsItsId() {
        interactor.execute(input("53191", "Pad Thai", "Thailand", "2026-11-01", 5));

        var stamp = dataAccess.getPassport("demo-user").getStamps().get(0);
        assertFalse(stamp.getDish().isManual());
        assertEquals("53191", stamp.getDish().getId());
    }
}