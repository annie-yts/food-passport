# Food Passport 🌍🍽️

*Been, but for food: track every country you've eaten your way through, one dish at a time.*

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
Food Passport is a desktop app for people who want to explore the world through food. Each time a user eats a dish, whether at a restaurant, at a friend's place, or while travelling, they **log it as a dish stamp**: the dish, the country it comes from, where and when they had it, a rating, and notes.

Countries are never stamped directly. A country counts as "tried" when the user has at least one dish stamp from it. A **world map** colours every country by how many dish stamps it has, and clicking a country shows those dishes. Users can **discover dishes** from countries they haven't tried yet and save them to a **bucket list**. From any dish or country, they can **find restaurants nearby** that serve that country's food. A **stats** screen shows world and continent coverage, top-rated countries, and a country → dish breakdown.

Our two food data sources classify food differently. TheMealDB labels each dish with an *area* (e.g. `Japanese`, `Mexican`). OpenStreetMap labels each restaurant with free-form *cuisine tags* (e.g. `sushi`, `italian;pizza`, `ice_cream`). Neither source knows about the other, so we use our own **country mapping table** to translate both into one shared list of countries. Tags we can't map confidently to a country (e.g. `ice_cream`, `burger`) are excluded.

---

## User Stories

### User Story 1: Stamp a dish
As a food lover, I want to log a dish I've eaten (country, date, place, rating, notes) so that it's added to my passport.

### User Story 2: World map
As a food lover, I want to see a world map coloured by how many dishes I've tried from each country, and click a country to see those dishes, so that I can see my food journey at a glance.

### User Story 3: Discover dishes and bucket list
As a curious eater, I want to browse dishes from a country I haven't tried and add them to a bucket list so that I know what to order next.

### User Story 4: Find it nearby
As a student, I want to find restaurants near me that serve a country's food so that I can actually go and try a dish on my bucket list.

### User Story 5: Stats
As a food lover, I want to see my coverage of the world and of each continent, my top-rated countries, and which dishes I've had from each country so that I stay motivated to keep exploring.

### Stretch User Stories (if time allows)
- **Badges:** As a user, I want to unlock badges (e.g. "Tried 5 countries in Southeast Asia") so that exploring feels rewarding.
- **Compare passports:** As a user, I want to compare my map with a friend's and see our "taste overlap" score so that we can plan where to eat together.
- **Recommend my next country:** As a user, I want the app to suggest a country I haven't tried, based on what I rated highly, so that I'm likely to enjoy it.
- **Export:** As a user, I want to export my passport as an image so that I can share it.

---

## Use Cases (MVP)

### Use Case 1: Stamp a Dish
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

### Use Case 2: View World Map
**Main flow**
1. User opens the Map view.
2. System draws world country borders and shades each country by number of dish stamps (0, 1, 2–4, 5+).
3. User clicks a country.
4. System shows a side panel with the flag, name, region, number of dishes tried, average rating, first-tried date, and the list of dish stamps.

**Alternative flows**
- Click lands on ocean → nothing happens.
- Country with no stamps → panel shows "Not tried yet", with a button that opens Use Case 3 for that country.

### Use Case 3: Discover Dishes and Manage Bucket List
**Main flow**
1. User selects a country (from the map, or by searching).
2. System looks up that country's area in the mapping table, fetches its dishes from TheMealDB, and shows them as cards (photo, name, category).
3. User adds dishes to their bucket list.
4. From a dish card or the bucket list, user can click **Find nearby**, which opens Use Case 4 for that dish's country.
5. Later, user marks a bucket-list item as eaten. The system opens Use Case 1, pre-filled.

**Alternative flows**
- No TheMealDB area maps to that country → system shows "No dishes yet for this country".
- Duplicate bucket-list item → system tells the user it's already on the list.

### Use Case 4: Find Restaurants Nearby
**Main flow**
1. User arrives with a country (from Use Case 3) and enters an address or neighbourhood and a radius.
2. System geocodes the address (Nominatim).
3. System uses the mapping table to get the OSM cuisine tags for that country (e.g. Japan → `japanese`, `sushi`, `ramen`), then queries OpenStreetMap (Overpass) for restaurants with any of those tags.
4. System lists results sorted by distance, with name, address, and matching tags.

**Alternative flows**
- Address not found → error message.
- No matching restaurants in range → system suggests increasing the radius.
- Country has no mapped OSM tags → system says nearby search isn't available for that country.

*This use case is for discovery only. It never creates stamps.*

### Use Case 5: View Stats
**Main flow**
1. User opens the Stats view.
2. System calculates: countries tried / total, % per continent and subregion, dish stamps per month, and top-rated countries (by average rating).
3. System shows a **country → dish tree**: each tried country, expandable to the dishes stamped there.

**Alternative flows**
- New user with no stamps → system shows an empty state with a prompt to stamp a first dish.

---

## Minimal Viable Product (MVP)

| Team Member | MVP Use Case |
|-------------|--------------|
| TBD | Use Case 1: Stamp a Dish |
| TBD | Use Case 2: View World Map |
| TBD | Use Case 3: Discover Dishes and Bucket List |
| TBD | Use Case 4: Find Restaurants Nearby |
| TBD | Use Case 5: View Stats |

*Login/signup (adapted from the course's `ca-user-login` example) is shared scaffolding, not anyone's lead use case. The country mapping table is also shared, since Use Cases 1, 3 and 4 all depend on it.*

---

## Entities

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
- `owner: String`, `stamps: List<DishStamp>`, `bucketList: List<BucketListItem>`
- `addStamp(DishStamp)`, `hasTried(String isoCode)`, `stampsFor(String isoCode)`, `countriesTried(): Set<String>`

---

## Country Mapping Table

A bundled file (`country_mapping.json`) that connects each data source's labels to our country list. It's loaded through a `CountryMappingGateway`, so use cases never see raw labels.

| Country | TheMealDB area | OSM cuisine tags |
|---|---|---|
| JPN (Japan) | `Japanese` | `japanese`, `sushi`, `ramen` |
| MEX (Mexico) | `Mexican` | `mexican`, `tacos` |
| ITA (Italy) | `Italian` | `italian`, `pizza`, `pasta` |
| … | … | … |

**Rules**
- Each TheMealDB area maps to at most one country.
- An OSM tag maps to a country only if it clearly belongs to that country's food. Generic tags (`burger`, `ice_cream`, `coffee_shop`) and broad regional ones (`asian`, `middle_eastern`) are left out.
- OSM tags can list several values (`italian;pizza`). We split them and match each value.

---

## API Usage

| API | Docs | Used for | Key? | Called successfully? |
|---|---|---|---|---|
| TheMealDB | https://www.themealdb.com/api.php | Dish search (UC1), dishes by area (UC3) | Free test key `1` (education use) | ☐ |
| REST Countries v3.1 | https://restcountries.com | Country names, region/subregion, flags (UC2, UC5) | None | ☐ |
| Nominatim (OSM) | https://nominatim.org/release-docs/latest/api/Search/ | Address → coordinates (UC4) | None (read its usage policy) | ☐ |
| Overpass API (OSM) | https://wiki.openstreetmap.org/wiki/Overpass_API | Nearby restaurants with matching `cuisine=*` tags (UC4) | None | ☐ |

**Notes**
- TheMealDB's multi-ingredient filter and some listings are premium-only; we only use free endpoints.
- World borders are drawn from a bundled GeoJSON file (e.g. Natural Earth, public domain) using Java2D.

---

## Design Notes (for the final presentation)

**Clean Architecture**
- Each external API sits behind an interface in the use-case layer (`DishGateway`, `CountryGateway`, `GeocodingGateway`, `PlacesGateway`, `CountryMappingGateway`). Interactors never see HTTP or JSON.
- Persistence goes through `PassportDataAccessInterface`, with two implementations: `FilePassportDAO` (local JSON) and `DBPassportDAO` (the course's user/JSON DB API). This is the Dependency Inversion Principle in action: we can switch storage without touching the use cases.

**Design patterns with a real job to do**
- **Adapter:** each gateway translates an external API's format (TheMealDB areas, OSM `cuisine` tags) into our own `Dish`, `Country` and `Restaurant` entities, using the mapping table.
- **Decorator:** `CachingDishGateway` / `CachingPlacesGateway` wrap the real gateways. They cut API calls, respect OSM usage limits, and keep the app usable offline.
- **Observer:** stamping a dish publishes an event; the map and stats views listen and refresh. (If we reach the badges stretch story, the badge checker listens to the same event.)
- **Strategy (stretch):** each badge rule is its own class, so adding a badge doesn't touch existing code.

**Hard parts we chose on purpose (where the rigour is)**
1. **Connecting two classification systems:** TheMealDB areas and OSM cuisine tags have no common key. The mapping table and its rules (what to include, what to exclude, splitting multi-value tags) are our own design, and they're what link UC1, UC3 and UC4.
2. **Map rendering:** parsing GeoJSON, projecting it onto the screen, colouring countries, and detecting which country was clicked (`Path2D.contains`).
3. **Statistics:** coverage per continent and subregion, rating aggregates, and the country → dish tree.
4. **Testability:** every interactor can be unit-tested with fake gateways and an in-memory DAO, with no network needed.

**Decisions made (PR #4 review)**
- Stamps are **dish stamps**. A country is "tried" if it has at least one dish stamp.
- Each dish belongs to exactly one country, via the mapping table.
- Only Use Case 1 creates stamps. Use Case 4 is discovery only.
- Badges moved to stretch goals to keep the MVP realistic.

**Open decision for the team**
- Local JSON only, or the shared DB API (needed for the stretch "compare passports" story)?