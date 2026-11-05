# Food Passport 🌍🍽️

*Been, but for cuisines: track every country you've eaten your way through.*

## Team Information

### Team Members
- Arnab Karmokar
- Brian Chang
- Annie Shen
- Siyuan Chen
- Zikuo Wang

---

## Project Description

### Domain
Food & lifestyle / personal data tracking

### Specification
Food Passport is a desktop app for people who want to explore the world through food. Each time a user eats a dish, whether at a restaurant, at a friend's place, or while travelling, they **log a food experience**: the dish, the cuisine it belongs to, where and when they had it, a rating, and notes. Each experience **stamps** one or more countries in their passport.

A **world map** colours every country by how often the user has eaten its cuisine, and clicking a country shows their history with it. Users can **discover dishes** from countries they haven't tried yet and save them to a **bucket list**. They can **search a neighbourhood** for restaurants serving cuisines that would earn them a new stamp. A **stats and achievements** screen shows world and continent coverage, favourite cuisines, and badges (e.g. "Tried every country in Southeast Asia").

A cuisine does not always map to exactly one country (e.g. *Levantine*, *Tex-Mex*, *Caribbean*), so the system treats cuisines and countries as a many-to-many relationship.

---

## User Stories

### User Story 1: Log a food experience
As a food lover, I want to log a dish I've eaten (cuisine, date, place, rating, notes) so that its country is stamped in my passport.

### User Story 2: World map
As a food lover, I want to see a world map coloured by how much of each country's cuisine I've tried, and click a country to see my history with it, so that I can see my food journey at a glance.

### User Story 3: Discover dishes and bucket list
As a curious eater, I want to browse popular dishes from a country I haven't tried and add them to a bucket list so that I know what to order next.

### User Story 4: Find it nearby
As a student, I want to enter an address and see nearby restaurants, with the ones serving a cuisine I haven't tried highlighted, so that I can earn a new stamp this week.

### User Story 5: Stats and achievements
As a food lover, I want to see my coverage of the world and of each continent, my top-rated cuisines, and badges I've unlocked so that I stay motivated to keep exploring.

### Stretch User Stories (if time allows)
- **Compare passports:** As a user, I want to compare my map with a friend's and see our "taste overlap" score so that we can plan where to eat together.
- **Recommend my next cuisine:** As a user, I want the app to suggest a cuisine I haven't tried, based on what I rated highly, so that I'm likely to enjoy it.
- **Export:** As a user, I want to export my passport as an image so that I can share it.

---

## Use Cases (MVP)

### Use Case 1: Log Food Experience
**Main flow**
1. User opens "Add Stamp" and types a dish name.
2. System searches TheMealDB and suggests matching dishes with their cuisine (area).
3. User picks a suggestion (or picks a cuisine manually), then enters date, place type (home / restaurant / travel / other), rating (1–5), and notes.
4. System resolves the cuisine to one or more countries, saves the entry, and updates the passport.
5. System shows "New stamp: 🇪🇹 Ethiopia!" if this is a first, and checks for newly unlocked achievements.

**Alternative flows**
- Dish not found → user selects a cuisine from the full list.
- Cuisine maps to several countries → user chooses which country (or countries) to stamp.
- Missing or invalid fields (no cuisine, rating out of range, future date) → error is shown and nothing is saved.
- API unavailable → user can still log the experience with a manually selected cuisine.

### Use Case 2: View World Map
**Main flow**
1. User opens the Map view.
2. System draws world country borders and shades each country by number of entries (0, 1, 2–4, 5+).
3. User clicks a country.
4. System shows a side panel with the flag, name, region, number of entries, average rating, first-tried date, and the list of entries.

**Alternative flows**
- Click lands on ocean or on a country with no data → panel shows "Not tried yet", with a button that leads to Use Case 3.

### Use Case 3: Discover Dishes and Manage Bucket List
**Main flow**
1. User selects a country (from the map, or by searching).
2. System fetches dishes for that cuisine from TheMealDB and shows them as cards (photo, name, category).
3. User adds dishes to their bucket list.
4. Later, user marks a bucket-list item as eaten. The system opens Use Case 1, pre-filled.

**Alternative flows**
- No dishes available for that country → system shows "No dishes yet". The user can still add the cuisine itself to the bucket list.
- Duplicate bucket-list item → system tells the user it's already on the list.

### Use Case 4: Find Restaurants Nearby
**Main flow**
1. User enters an address or neighbourhood and a radius.
2. System geocodes it (Nominatim), then queries OpenStreetMap (Overpass) for restaurants tagged with a cuisine.
3. System parses each place's `cuisine` tag (which can list several values, e.g. `italian;pizza`) and matches it to known cuisines.
4. System lists results sorted by distance, with a **NEW STAMP** badge on cuisines the user hasn't tried.
5. User saves a restaurant to their bucket list.

**Alternative flows**
- Address not found → error message.
- No tagged restaurants in range → system suggests increasing the radius.
- Unrecognised cuisine tag → system shows the raw tag but cannot award a stamp for it.

### Use Case 5: View Stats and Achievements
**Main flow**
1. User opens the Stats view.
2. System calculates: countries tried / total, % per continent and subregion, entries per month, top-rated cuisines, and longest streak of new countries.
3. System shows unlocked and locked badges, with progress (e.g. "Southeast Asia: 4/11").

**Alternative flows**
- New user with no entries → system shows an empty state with a prompt to log a first meal.

---

## Minimal Viable Product (MVP)

| Team Member | MVP Use Case |
|-------------|--------------|
| TBD | Use Case 1: Log Food Experience |
| TBD | Use Case 2: View World Map |
| TBD | Use Case 3: Discover Dishes and Bucket List |
| TBD | Use Case 4: Find Restaurants Nearby |
| TBD | Use Case 5: Stats and Achievements |

*Login/signup (adapted from the course's `ca-user-login` example) is shared scaffolding, not anyone's lead use case.*

---

## Entities

**Country**
- `isoCode: String` (ISO 3166-1 alpha-3), `name: String`, `region: String`, `subregion: String`, `flagUrl: String`

**Cuisine**
- `id: String`, `name: String`, `countryCodes: List<String>`. This is the many-to-many link to Country.
- `isMultiCountry(): boolean`

**FoodEntry**
- `id: String`, `dishName: String`, `cuisine: Cuisine`, `stampedCountries: List<String>`, `date: LocalDate`, `placeType: PlaceType` (enum: HOME, RESTAURANT, TRAVEL, OTHER), `rating: int`, `notes: String`, `restaurantName: String` (optional)

**Dish**
- `id: String`, `name: String`, `cuisineName: String`, `category: String`, `thumbnailUrl: String`

**BucketListItem**
- `id: String`, `dish: Dish` (or null), `cuisine: Cuisine`, `restaurantName: String` (optional), `addedOn: LocalDate`, `completed: boolean`
- `markCompleted()`

**Restaurant**
- `name: String`, `latitude: double`, `longitude: double`, `cuisineTags: List<String>`, `address: String`

**Passport** (aggregate)
- `owner: String`, `entries: List<FoodEntry>`, `bucketList: List<BucketListItem>`, `badges: List<Badge>`
- `addEntry(FoodEntry)`, `hasTried(String isoCode)`, `entriesFor(String isoCode)`, `countriesTried(): Set<String>`

**Badge**
- `id: String`, `name: String`, `description: String`, `unlockedOn: LocalDate`

**BadgeRule** (interface)
- `isSatisfiedBy(Passport): boolean`, `progress(Passport): double`

---

## API Usage

| API | Docs | Used for | Key? | Called successfully? |
|---|---|---|---|---|
| TheMealDB | https://www.themealdb.com/api.php | Dish search (UC1), dishes by area (UC3), cuisine list | Free test key `1` (education use) | ☐ |
| REST Countries v3.1 | https://restcountries.com | Demonym → country, region/subregion, flags (UC1, UC2, UC5) | None | ☐ |
| Nominatim (OSM) | https://nominatim.org/release-docs/latest/api/Search/ | Address → coordinates (UC4) | None (read its usage policy) | ☐ |
| Overpass API (OSM) | https://wiki.openstreetmap.org/wiki/Overpass_API | Nearby restaurants with `cuisine=*` tags (UC4) | None | ☐ |

**Notes**
- TheMealDB areas are demonyms ("Nigerian"); REST Countries can look up a country by demonym. That makes the dish → cuisine → country chain possible.
- TheMealDB's multi-ingredient filter and some listings are premium-only; we only use free endpoints.
- World borders are drawn from a bundled GeoJSON file (e.g. Natural Earth, public domain) using Java2D.

---

## Design Notes (for the final presentation)

**Clean Architecture**
- Each external API sits behind an interface in the use-case layer (`DishGateway`, `CountryGateway`, `GeocodingGateway`, `PlacesGateway`). Interactors never see HTTP or JSON.
- Persistence goes through `PassportDataAccessInterface`, with two implementations: `FilePassportDAO` (local JSON) and `DBPassportDAO` (the course's user/JSON DB API). This is the Dependency Inversion Principle in action: we can switch storage without touching the use cases.

**Design patterns with a real job to do**
- **Observer:** logging an entry publishes an event; the achievement engine listens and re-checks badges. UC1 doesn't need to know about UC5.
- **Strategy:** each `BadgeRule` is a strategy (`ContinentCompleteRule`, `CountryCountRule`, `StreakRule`), and new badges need no changes to existing code (Open/Closed).
- **Decorator:** `CachingDishGateway` / `CachingPlacesGateway` wrap the real gateways. They cut API calls, respect OSM usage limits, and keep the app usable offline.
- **Adapter:** each gateway translates an external API's format (OSM `cuisine` tags, TheMealDB areas) into our own `Cuisine` and `Restaurant` entities.
- **Factory:** builds `Cuisine` objects from the different sources (TheMealDB area, OSM tag, manual pick) so they all have one consistent form.

**Hard parts we chose on purpose (where the rigour is)**
1. **Cuisine ↔ country mapping** is many-to-many and messy. Rules have to cover regional cuisines, fusion, and unmatched OSM tags.
2. **Map rendering:** parsing GeoJSON, projecting it onto the screen, colouring countries, and detecting which country was clicked (`Path2D.contains`).
3. **Parsing OSM cuisine tags:** lowercase, semicolon-separated, inconsistent values, matched against our cuisine list.
4. **Statistics:** coverage per continent and subregion, streaks, rating aggregates, and the stretch "taste overlap" score (Jaccard similarity).
5. **Testability:** every interactor can be unit-tested with fake gateways and an in-memory DAO, with no network needed.

**Open decisions for the team**
- Does any entry count as a stamp, or only rated ones?
- For a multi-country cuisine, does one entry stamp all its countries or only the one the user picks?
- Local JSON only, or the shared DB API (needed for the stretch "compare passports" story)?
