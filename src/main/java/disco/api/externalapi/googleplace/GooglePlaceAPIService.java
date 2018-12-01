package disco.api.externalapi.googleplace;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.*;
import disco.api.externalapi.googlecommons.GoogleCommons;
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

    private GeoApiContext getContext(String googleAPIKey) {
        return new GeoApiContext.Builder()
                .apiKey(googleAPIKey)
                .build();
    }

    private LatLng getLatLng() {
        return new LatLng(
                lat,
                lng
        );
    }

    public List<GooglePlaceGETResponse> getAllPointOfInterestsLocation() {

        String googleAPIKey = GoogleCommons.getGoogleAPIKey();

        if(googleAPIKey == null) {
            logger.warn("There is no Google API Key in the Environment Variables");
            return null;
        }

        GeoApiContext context = getContext(googleAPIKey);
        LatLng latLng = getLatLng();

        try {
            List<PlacesSearchResult> placesSearchResultList = getAllPlaceSearchResults(context, latLng, RequestType.POINTOFINTEREST);
            return getAllPlaceSearchResultsInFormat(placesSearchResultList);
        } catch (InterruptedException e) {
            logger.warn("Error during fetching results: " + e);
            return null;
        }

    }

    public List<GooglePlaceGETResponse> getAllRestaurantsLocation() {

        String googleAPIKey = GoogleCommons.getGoogleAPIKey();

        if(googleAPIKey == null) {
            logger.warn("There is no Google API Key in the Environment Variables");
            return null;
        }

        GeoApiContext context = getContext(googleAPIKey);
        LatLng latLng = getLatLng();

        try {
            List<PlacesSearchResult> placesSearchResultList = getAllPlaceSearchResults(context, latLng, RequestType.RESTAURANT);
            return getAllPlaceSearchResultsInFormat(placesSearchResultList);
        } catch (InterruptedException e) {
            logger.warn("Error during fetching results: " + e);
            return null;
        }

    }

    private List<GooglePlaceGETResponse> getAllPlaceSearchResultsInFormat(List<PlacesSearchResult> placesSearchResultList) {

        List<GooglePlaceGETResponse> googlePlaceGETResponseList = new LinkedList<>();
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

            GooglePlaceGETResponse googlePlaceGETResponse = new GooglePlaceGETResponse();
            googlePlaceGETResponse.setAddress(address);
            googlePlaceGETResponse.setLat(lat);
            googlePlaceGETResponse.setLng(lng);
            googlePlaceGETResponse.setName(name);
            googlePlaceGETResponse.setRating(rating);
            googlePlaceGETResponse.setType(type);

            googlePlaceGETResponseList.add(googlePlaceGETResponse);

        }

        return googlePlaceGETResponseList;

    }

    private PlacesSearchResponse getRestaurantResponse(GeoApiContext context, LatLng latLng) {
        return PlacesApi
                .nearbySearchQuery(context, latLng)
                .keyword("Restaurant")
                .rankby(RankBy.PROMINENCE)
                .radius((int) Math.round(rad))
                .awaitIgnoreError();
    }

    private PlacesSearchResponse getNextRestaurantResponse(GeoApiContext context, LatLng latLng, String nextPageToken) {
        return PlacesApi
                .nearbySearchNextPage(context, nextPageToken)
                .location(latLng)
                .keyword("Restaurant")
                .rankby(RankBy.PROMINENCE)
                .radius((int) Math.round(rad))
                .awaitIgnoreError();
    }

    private PlacesSearchResponse getPointOfInterestResponse(GeoApiContext context, LatLng latLng) {
        return PlacesApi
                .nearbySearchQuery(context, latLng)
                .keyword("Sehenswürdigkeiten")
                .rankby(RankBy.PROMINENCE)
                .radius((int) Math.round(rad))
                .awaitIgnoreError();
    }

    private PlacesSearchResponse getNextPointOfInterestResponse(GeoApiContext context, LatLng latLng, String nextPageToken) {
        return PlacesApi
                .nearbySearchNextPage(context, nextPageToken)
                .location(latLng)
                .keyword("Sehenswürdigkeiten")
                .rankby(RankBy.PROMINENCE)
                .radius((int) Math.round(rad))
                .awaitIgnoreError();
    }

    private List<PlacesSearchResult> getAllPlaceSearchResults(GeoApiContext context, LatLng latLng, RequestType requestType) throws InterruptedException {
        List<PlacesSearchResult> placesSearchResultList = new ArrayList<>();

        PlacesSearchResponse placesSearchResponse;

        if(requestType == RequestType.RESTAURANT) {
            placesSearchResponse = getRestaurantResponse(context, latLng);
        } else {
            placesSearchResponse = getPointOfInterestResponse(context, latLng);
        }

        Collections.addAll(placesSearchResultList, placesSearchResponse.results);

        /*

        if(placesSearchResponse.nextPageToken != null){
            do {
                String nextPageToken = placesSearchResponse.nextPageToken;

                Thread.sleep(3000);

                if(requestType == RequestType.RESTAURANT) {
                    placesSearchResponse = getNextRestaurantResponse(context, latLng, nextPageToken);
                } else {
                    placesSearchResponse = getNextPointOfInterestResponse(context, latLng, nextPageToken);
                }

                if(placesSearchResponse != null){
                    Collections.addAll(placesSearchResultList, placesSearchResponse.results);
                }
            } while (placesSearchResponse != null && placesSearchResponse.nextPageToken != null);
        }

        */

        return placesSearchResultList;
    }

}
