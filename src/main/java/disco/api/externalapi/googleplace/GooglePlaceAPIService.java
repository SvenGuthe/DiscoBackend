package disco.api.externalapi.googleplace;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class GooglePlaceAPIService {

    private static Logger logger = LogManager.getRootLogger();

    private double lat;
    private double lng;
    private double rad;

    public GooglePlaceAPIService(double lat, double lng, double rad){
        this.lat = lat;
        this.lng = lng;
        this.rad = rad;
    }

    public List<GooglePlaceRestaurantGETResponse> getAllRestaurantsLocation() {

        String googleAPIKey = getGoogleAPIKey();

        if(googleAPIKey == null) {
            logger.warn("There is no Google API Key in the Environment Variables");
            return null;
        }

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(googleAPIKey)
                .build();

        LatLng latLng = new LatLng(
                lat,
                lng
        );

        try {
            List<PlacesSearchResult> placesSearchResultList = getAllPlaceSearchResults(context, latLng);
            return getAllPlaceSearchResultsInFormat(placesSearchResultList);
        } catch (InterruptedException e) {
            logger.warn("Error during fetching results: " + e);
            return null;
        }

    }

    private List<GooglePlaceRestaurantGETResponse> getAllPlaceSearchResultsInFormat(List<PlacesSearchResult> placesSearchResultList) {

        List<GooglePlaceRestaurantGETResponse> googlePlaceRestaurantGETResponseList = new LinkedList<>();
        Iterator<PlacesSearchResult> placeIterator = placesSearchResultList.iterator();

        while(placeIterator.hasNext()) {

            PlacesSearchResult placesSearchResult = placeIterator.next();

            String address = placesSearchResult.formattedAddress;
            String name = placesSearchResult.name;
            Double lat = placesSearchResult.geometry.location.lat;
            Double lng = placesSearchResult.geometry.location.lng;
            Float rating = placesSearchResult.rating;
            List<String> type = new ArrayList<>();
            Collections.addAll(type, placesSearchResult.types);

            GooglePlaceRestaurantGETResponse googlePlaceRestaurantGETResponse = new GooglePlaceRestaurantGETResponse();
            googlePlaceRestaurantGETResponse.setAddress(address);
            googlePlaceRestaurantGETResponse.setLat(lat);
            googlePlaceRestaurantGETResponse.setLng(lng);
            googlePlaceRestaurantGETResponse.setName(name);
            googlePlaceRestaurantGETResponse.setRating(rating);
            googlePlaceRestaurantGETResponse.setType(type);

            googlePlaceRestaurantGETResponseList.add(googlePlaceRestaurantGETResponse);

        }

        return googlePlaceRestaurantGETResponseList;

    }

    private List<PlacesSearchResult> getAllPlaceSearchResults(GeoApiContext context, LatLng latLng) throws InterruptedException {
        List<PlacesSearchResult> placesSearchResultList = new ArrayList<>();

        PlacesSearchResponse placesSearchResponse = PlacesApi
                .nearbySearchQuery(context, latLng)
                .type(PlaceType.RESTAURANT, PlaceType.BAR, PlaceType.CAFE, PlaceType.FOOD)
                .radius(5000)
                .awaitIgnoreError();

        Collections.addAll(placesSearchResultList, placesSearchResponse.results);

        if(placesSearchResponse.nextPageToken != null){
            do {
                String nextPageToken = placesSearchResponse.nextPageToken;

                Thread.sleep(3000);

                placesSearchResponse = PlacesApi
                        .nearbySearchNextPage(context, nextPageToken)
                        .location(latLng)
                        .type(PlaceType.RESTAURANT, PlaceType.BAR, PlaceType.CAFE, PlaceType.FOOD)
                        .radius(5000)
                        .awaitIgnoreError();
                if(placesSearchResponse != null){
                    Collections.addAll(placesSearchResultList, placesSearchResponse.results);
                }
            } while (placesSearchResponse != null && placesSearchResponse.nextPageToken != null);
        }

        return placesSearchResultList;
    }

    private String getGoogleAPIKey() {
        final String envGoogleAPIKeyKey = "GOOGLE_API_KEY";

        Map<String, String> env = System.getenv();

        if(env.containsKey(envGoogleAPIKeyKey)) {
            String envGoogleAPIKeyValue = env.get(envGoogleAPIKeyKey);
            logger.info("The Env-Variable " + envGoogleAPIKeyKey + " = " + envGoogleAPIKeyValue );
            return envGoogleAPIKeyValue;
        } else {
            return null;
        }
    }

}
