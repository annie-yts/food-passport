# Food Passport 🌍🍽️

*Track every country you've eaten your way through, one dish at a time.*

## Team Information

### Team Members
- Arnab Karmokar
- Brian Chang
- Annie Shen
- Siyuan(Andy) Chen

---

## Project Description

### Domain
Food & lifestyle / personal data tracking

### Specification
Food Passport is a desktop app for people who want to explore the world through food. Each time a user eats a dish, whether at a restaurant, at a friend's place, or while travelling, they **log it as a dish stamp**: the dish, the country it comes from, where and when they had it, a rating, and notes.

Countries are never stamped directly. A country counts as "tried" when the user has at least one dish stamp from it. A **world map** colours every country by how many dish stamps it has, and clicking a country shows those dishes. Users can **discover dishes** from countries they haven't tried yet and save them to a **bucket list**. From any dish or country, they can **find restaurants nearby** that serve that country's food. Each user has their own **account**, and a **profile** page shows their world and continent coverage, top-rated countries, and a country → dish breakdown, with an option to export a stats card to share.

Our two food data sources classify food differently. TheMealDB labels each dish with an *area* (e.g. `Japanese`, `Mexican`). OpenStreetMap labels each restaurant with free-form *cuisine tags* (e.g. `sushi`, `italian;pizza`, `ice_cream`). Neither source knows about the other, so we use our own **country mapping table** to translate both into one shared list of countries. Tags we can't map confidently to a country (e.g. `ice_cream`, `burger`) are excluded.

---

## User Stories

### User Story 1: Account and profile
As a user, I want my own account (sign up, log in, log out, and manage my username, password, or account) and a profile page with my stats, so that my passport is private to me, I can see my coverage of the world and of each continent, my top-rated countries, and which dishes I've had from each country, and I can export a summary to share with friends.

### User Story 2: Stamp a dish
As a food lover, I want to log a dish I've eaten (country, date, place, rating, notes) so that it's added to my passport.

### User Story 3: World map
As a food lover, I want to see a world map coloured by how many dishes I've tried from each country, and click a country to see those dishes, so that I can see my food journey at a glance.

### User Story 4: Discover dishes and bucket list
As a curious eater, I want to browse dishes from a country I haven't tried and add them to a bucket list so that I know what to order next.

### User Story 5: Find it nearby
As a student, I want to find restaurants near me that serve a country's food so that I can actually go and try a dish on my bucket list.

### Stretch User Stories (if time allows)
- **Badges:** As a user, I want to unlock badges (e.g. "Tried 5 countries in Southeast Asia") so that exploring feels rewarding.
- **Recommend my next country:** As a user, I want the app to suggest a country I haven't tried, based on what I rated highly, so that I'm likely to enjoy it.

---

## Use Cases (MVP)

### Use Case 1: Account and Profile
This use case covers everything tied to the user's account: authentication, account settings, and the profile page with stats.

**Main flow: sign up and log in**
1. On launch, the user sees the Log In screen, with a **Go to Sign Up** button.
2. To sign up, the user enters a username, a password, and the password again (Confirm Password). The system checks that every field is filled in, the passwords match, and the username is free, then creates a `User` with a new user ID and creates an empty passport for them.
3. To log in, the user enters their username and password (pressing Return also logs in). The system checks the password and opens their passport.
4. The user clicks **Log Out** at any time to return to the Log In screen.

**Main flow: profile and stats**
1. The user opens their Profile.
2. The system shows their username and calculates: countries tried / total, % per continent and subregion, dish stamps per month, and top-rated countries (by average rating).
3. The system shows a **country → dish tree**: each tried country, expandable to the dishes stamped there.
4. The user clicks **Export**. The system generates a stats card (username, countries tried, continent coverage, top-rated countries) and saves it as a PNG image the user can send to friends.

**Main flow: account settings** (from the Profile)
- **Change username:** user enters a new username. The system checks it's free and saves it. The user ID stays the same, so the passport is unaffected.
- **Change password:** user enters their current password and a new one (twice). The system verifies the current password, then saves the new password.
- **Delete account:** user confirms with their password. The system deletes the user from `data/users.json`, deletes their passport file, and returns to the Log In screen.

**Alternative flows**
- Any field left empty (sign up or log in) → a popup names every empty field, e.g. "Username and Password cannot be empty.", and nothing is saved.
- Passwords don't match (sign up) → "Passwords don't match." is shown and nothing is saved.
- Username already taken (sign up or change username) → "Username is already taken." is shown and nothing is saved.
- Unknown username at log in → "Username does not exist." is shown.
- Wrong password at log in → "Incorrect password." is shown.
- Wrong current password (change password or delete account) → error is shown and nothing changes.
- User cancels the delete confirmation → nothing is deleted.
- New user with no stamps → the profile shows an empty state with a prompt to stamp a first dish. Export is disabled.
- Image can't be saved (e.g. no write permission) → error is shown and the profile stays open.

### Use Case 2: Stamp a Dish
**Main flow**
1. User opens "Add Stamp" and types a dish name.
2. System searches TheMealDB and suggests matching dishes, each with its country (from the dish's area, via the mapping table).
3. User picks a suggestion, then enters date, place type (home / restaurant / travel / other), rating (1–5), and notes.
4. System saves a dish stamp and updates the passport.
5. If this is the user's first dish from that country, the system shows "New country: 🇪🇹 Ethiopia!".

**Alternative flows**
- Dish not found → user enters the dish name themselves and picks its country from a list.
- Dish's area doesn't map to a country → user picks the country from a list.
- Missing or invalid fields (no country, rating out of range, future date) → error is shown and nothing is saved.
- API unavailable → user can still stamp a dish they type in, with a manually picked country.

*This is the only use case that creates stamps.*

### Use Case 3: View World Map
**Main flow**
1. User opens the Map view.
2. System draws world country borders and shades each country by number of dish stamps (0, 1, 2–4, 5+).
3. User clicks a country.
4. System shows a side panel with the flag, name, region, number of dishes tried, average rating, first-tried date, and the list of dish stamps.

**Alternative flows**
- Click lands on ocean → nothing happens.
- Country with no stamps → panel shows "Not tried yet", with a button that opens Use Case 4 for that country.

### Use Case 4: Discover Dishes and Manage Bucket List
**Main flow**
1. User selects a country (from the map, or by searching).
2. System looks up that country's area in the mapping table, fetches its dishes from TheMealDB, and shows them as cards (photo, name, category).
3. User adds dishes to their bucket list.
4. From a dish card or the bucket list, user can click **Find nearby**, which opens Use Case 5 for that dish's country.
5. Later, user marks a bucket-list item as eaten. The system opens Use Case 2, pre-filled.

**Alternative flows**
- No TheMealDB area maps to that country → system shows "No dishes yet for this country".
- Duplicate bucket-list item → system tells the user it's already on the list.

### Use Case 5: Find Restaurants Nearby
**Main flow**
1. User arrives with a country (from Use Case 4) and enters an address or neighbourhood and a radius in km.
2. System geocodes the address (Nominatim).
3. System uses the mapping table to get the OSM cuisine tags for that country (e.g. Japan → `japanese`, `sushi`, `ramen`), then queries OpenStreetMap (Overpass) for restaurants with any of those tags.
4. System lists results sorted by distance, with name, address, matching tags, and distance in km.

**Alternative flows**
- Address left empty → "Address cannot be empty."
- Radius is not a positive number → "Radius must be greater than 0."
- Radius over 50 km → "Radius cannot be more than 50km."
- Address not found → "Address not found."
- No matching restaurants in range → system suggests increasing the radius.
- Country has no mapped OSM tags → system says nearby search isn't available for that country.
- Nominatim or Overpass can't be reached → "Couldn't reach the map service. Please try again later."

*This use case is for discovery only. It never creates stamps.*

---

## Minimal Viable Product (MVP)

| Team Member | MVP Use Case |
|-------------|--------------|
| Arnab Karmokar | Use Case 1: Account and Profile |
| Brian Chang | Use Case 2: Stamp a Dish |
| Siyuan(Andy) Chen | Use Case 4: Discover Dishes and Bucket List |
| Annie Shen | Use Case 5: Find Restaurants Nearby |
| All members (shared) | Use Case 3: View World Map |

*Use Case 3 has no single lead. The whole team builds it together, and each contribution is tracked through its own branch and PR.*

*Use Case 1 is the largest, since it owns login (adapted from the course's `ca-user-login` example), account settings, the profile, and stats. The country mapping table is shared, since Use Cases 2, 4 and 5 all depend on it.*

---

## Entities

**User**
- `userId: String` (unique, generated at signup), `userName: String`, `password: String`
- Stored in `data/users.json`.

**Country**
- `isoCode: String` (ISO 3166-1 alpha-3), `name: String`, `region: String`, `subregion: String`, `flagUrl: String`

**Dish**
- `id: String` (TheMealDB id, or generated for user-entered dishes), `name: String`, `countryCode: String`, `category: String`, `thumbnailUrl: String`
- `category` is TheMealDB's dish type (e.g. "Seafood", "Vegetarian"). It is **not** used to decide a dish's country.

**DishStamp**
- `id: String`, `dish: Dish`, `date: LocalDate`, `placeType: PlaceType` (enum: HOME, RESTAURANT, TRAVEL, OTHER), `rating: int`, `notes: String`, `restaurantName: String` (optional)
- `getCountryCode()` returns the dish's country.

**BucketListItem**
- `id: String`, `dish: Dish`, `addedOn: LocalDate`, `completed: boolean`
- `markCompleted()`

**Restaurant**
- `name: String`, `latitude: double`, `longitude: double`, `cuisineTags: List<String>`, `address: String`

**Passport** (aggregate)
- `userId: String` (links to the owning `User`), `stamps: List<DishStamp>`, `bucketList: List<BucketListItem>`
- `addStamp(DishStamp)`, `hasTried(String isoCode)`, `stampsFor(String isoCode)`, `countriesTried(): Set<String>`

---

## Local Storage and Authentication (MVP)

Food Passport uses local JSON files for user authentication and passport data storage.

The application supports multiple independent users on a single shared computer. Each user can sign up, log in, and access their own passport, dish stamps, and bucket list (see Use Case 1).

- `data/users.json`: Stores user IDs, usernames, and passwords.
- `data/passports/{userId}.json`: Stores each user's dish stamps and bucket-list items.
- Each user's data is associated with a unique user ID.
- Users can only access their own passport through the application.

**Limitations**

- User accounts and passport data are stored locally.
- Passwords are stored in plain text, so users shouldn't reuse a password from another account.
- Cross-device login and data synchronization are not supported in the MVP.
- Cloud-based authentication and remote data storage may be considered in future development.

## Country Mapping Table

UC4 loads `src/main/resources/country_mapping.json` at startup, so the country picker works offline. It displays country names and passes ISO 3166-1 alpha-3 codes to dish search. TheMealDB's adapter translates those codes to area labels for requests and converts results back to country codes. Update the bundled file when TheMealDB adds countries. [TheMealDB area list](https://www.themealdb.com/api/json/v1/1/list.php?a=list)

The OSM column below shows the intended tag mappings for nearby restaurant search.

| Country | TheMealDB area | OSM cuisine tags |
|---|---|---|
| JPN (Japan) | `Japanese` | `japanese`, `sushi`, `ramen` |
| MEX (Mexico) | `Mexican` | `mexican`, `tacos` |
| ITA (Italy) | `Italian` | `italian`, `pizza`, `pasta` |
| … | … | … |

**Rules**
- Several countries can share a TheMealDB area label. Dish search uses the meal's country name when present. An area label alone identifies a country only if the mapping is unique. UC4 skips dishes without a mapped country.
- An OSM tag maps to a country only if it clearly belongs to that country's food. Generic tags (`burger`, `ice_cream`, `coffee_shop`) and broad regional ones (`asian`, `middle_eastern`) are left out.
- OSM tags can list several values (`italian;pizza`). We split them and match each value.

---

## API Usage

| API | Docs | Used for | Key? | Called successfully? |
|---|---|---|---|---|
| TheMealDB | https://www.themealdb.com/api.php | Dish search (UC2), dishes by area (UC4) | Free test key `1` (education use) | ☐ |
| REST Countries v3.1 | https://restcountries.com | Country names, region/subregion, flags (UC1, UC3) | None | ☐ |
| Nominatim (OSM) | https://nominatim.org/release-docs/latest/api/Search/ | Address → coordinates (UC5) | None (read its usage policy) | ☐ |
| Overpass API (OSM) | https://wiki.openstreetmap.org/wiki/Overpass_API | Nearby restaurants with matching `cuisine=*` tags (UC5) | None | ☐ |

**Notes**
- TheMealDB's multi-ingredient filter and some listings are premium-only; we only use free endpoints.
- World borders are drawn from a bundled GeoJSON file (e.g. Natural Earth, public domain) using Java2D.

---

## Design Notes (for the final presentation)

**Clean Architecture**
- Each external API sits behind an interface in the use-case layer (`DishGateway`, `CountryGateway`, `GeocodingGateway`, `PlacesGateway`, `CountryMappingGateway`). Interactors never see HTTP or JSON.
- Persistence goes through `PassportDataAccessInterface`, implemented by `FilePassportDAO` using local JSON files. User accounts and passport data are stored locally, with each user's passport associated with a unique user ID. This follows the Dependency Inversion Principle, allowing the storage implementation to be replaced in the future without modifying the core use cases.

**Design patterns with a real job to do**
- **Adapter:** each gateway translates an external API's format (TheMealDB areas, OSM `cuisine` tags) into our own `Dish`, `Country` and `Restaurant` Entities, using the mapping table.
- In Use Case 5, NominatimGeocodingGateway and OverpassPlacesGateway implement GeocodingGateway and PlacesGateway. FindRestaurantsInteractor depends only on those interfaces, so it is tested with fake gateways and no network.
- **Decorator:** `CachingDishGateway` / `CachingPlacesGateway` wrap the real gateways. They cut API calls, respect OSM usage limits, and keep the app usable offline.
- **Observer:** stamping a dish publishes an event; the map and profile views listen and refresh. (If we reach the badges stretch story, the badge checker listens to the same event.)
- **Strategy (stretch):** each badge rule is its own class, so adding a badge doesn't touch existing code.

**Hard parts we chose on purpose (where the rigour is)**
1. **Connecting two classification systems:** TheMealDB areas and OSM cuisine tags have no common key. The mapping table and its rules (what to include, what to exclude, splitting multi-value tags) are our own design, and they're what link UC2, UC4 and UC5.
2. **Map rendering:** parsing GeoJSON, projecting it onto the screen, colouring countries, and detecting which country was clicked (`Path2D.contains`).
3. **Statistics:** coverage per continent and subregion, rating aggregates, and the country → dish tree.
4. **Testability:** every interactor can be unit-tested with fake gateways and an in-memory DAO, with no network needed.

**Decisions made**

*Stamps and scope*
- Stamps are **dish stamps**. A country is "tried" if it has at least one dish stamp.
- Each dish belongs to exactly one country, via the mapping table.
- Only Use Case 2 creates stamps. Use Case 5 is discovery only.
- Badges moved to stretch goals to keep the MVP realistic.
- Login, account settings, the profile, stats and export are all one use case (Use Case 1), since they're all account information.
- Sharing is the profile's Export option (a stats card saved as a PNG), replacing the earlier "compare passports" and "export" stretch stories.

*Storage and users*
- Local JSON is used for both user authentication and passport persistence.
- The application supports multiple users on a single shared computer, each with an independent passport linked by their unique user ID.
- Cross-device login and data synchronization are outside the scope of the MVP. Cloud-based authentication and a remote database may be considered as future enhancements.

---

## Acknowledgements

Use Case 1's sign up, log in and log out are based on the CSC207 **ca-login** example (https://github.com/annie-yts/ca-login). Each file says at the top whether it was copied or adapted, and what changed.

**Copied unchanged**
- `interface_adapter`: `ViewManagerModel`, `ViewManagerModelListener`
- `interface_adapter.login`: `LoginState`, `LoginViewModel`, `LoginViewModelListener`
- `interface_adapter.signup`: `SignupController`, `SignupState`, `SignupViewModelListener`
- `use_case.login`: `LoginInputData`, `LoginOutputData`
- `use_case.logout`: `LogoutInputBoundary`, `LogoutOutputBoundary`, `LogoutOutputData`, `LogoutUserDataAccessInterface`
- `use_case.signup`: `SignupInputBoundary`, `SignupInputData`, `SignupOutputBoundary`, `SignupOutputData`
- `view`: `ViewManager`

**Adapted**
- `Entities`: `User` (added `userId` and setters), `UserFactory` (generates the `userId`)
- `app`: `AppBuilder`, `Main` (Profile screen, `data/users.json`, opens on Log In)
- `data_access`: `FileUserDataAccessObject` (saves JSON instead of CSV, with a `userId`), `InMemoryUserDataAccessObject`
- `interface_adapter.login`: `LoginController`, `LoginPresenter` (go to Profile; Go to Sign Up)
- `interface_adapter.logout`: `LogoutController`, `LogoutPresenter` (completed the lab's TODOs; clears the login screen)
- `interface_adapter.profile`: `ProfileState`, `ProfileViewModel`, `ProfileViewModelListener` (from `LoggedIn…`)
- `interface_adapter.signup`: `SignupPresenter` (clears the screen after sign up), `SignupViewModel` (labels, removed Cancel)
- `use_case.login`: `LoginInputBoundary`, `LoginOutputBoundary`, `LoginInteractor` (names empty fields; separate unknown-username and wrong-password messages), `LoginUserDataAccessInterface`
- `use_case.logout`: `LogoutInteractor`
- `use_case.signup`: `SignupInteractor` (names every empty field), `SignupUserDataAccessInterface`
- `view`: `LoginView`, `SignupView` (Enter key, popups, button changes, screens clear), `ProfileView` (from `LoggedInView`)
