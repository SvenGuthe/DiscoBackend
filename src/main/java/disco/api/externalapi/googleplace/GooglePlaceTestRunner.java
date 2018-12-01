package disco.api.externalapi.googleplace;

import com.google.maps.GeoApiContext;
import com.google.maps.PlacesApi;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlaceType;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;

import java.util.*;

public class GooglePlaceTestRunner {

    public static void main(String[] args) throws InterruptedException {

        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey("...")
                .build();

        LatLng latLng = new LatLng(
                50.9787,
                11.03283
        );

        /*

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

        */
        /*
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
        */

        return;

    }

}
