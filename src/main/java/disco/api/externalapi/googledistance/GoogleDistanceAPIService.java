package disco.api.externalapi.googledistance;

import com.google.maps.DirectionsApi;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.LatLng;
import com.google.maps.model.TravelMode;
import disco.api.externalapi.googlecommons.GoogleCommons;
import disco.api.externalapi.googleplace.GooglePlaceAPIService;
import disco.api.externalapi.googleplace.GooglePlaceGETResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.time.Instant;
import java.util.*;

public class GoogleDistanceAPIService {

    private static Logger logger = LogManager.getRootLogger();

    private double lat;
    private double lng;
    private double rad;

    public GoogleDistanceAPIService(double lat, double lng, double rad){
        this.lat = lat;
        this.lng = lng;
        this.rad = rad;
    }

    public List<GooglePlaceGETResponse> getRouting() {

        String API_KEY = GoogleCommons.getGoogleAPIKey();
        GeoApiContext context = new GeoApiContext.Builder()
                .apiKey(API_KEY)
                .build();

        List<GooglePlaceGETResponse> googlePlaceGETResponseList = getAllGooglePlaces();

        Iterator<GooglePlaceGETResponse> googlePlaceGETResponseIterator = googlePlaceGETResponseList.iterator();

        Map<Integer, LatLng> mapLocations = new HashMap<>();
        Map<Integer, GooglePlaceGETResponse> mapLocationNames = new HashMap<>();

        Integer index = 0;
        Integer maxIndex = 9;

        if(googlePlaceGETResponseList.size() < maxIndex){
            maxIndex = googlePlaceGETResponseList.size();
        }

        while (googlePlaceGETResponseIterator.hasNext() && index <= maxIndex){

            GooglePlaceGETResponse googlePlaceGETResponse = googlePlaceGETResponseIterator.next();

            mapLocationNames.put(index, googlePlaceGETResponse);
            mapLocations.put(index, new LatLng(googlePlaceGETResponse.getLat(), googlePlaceGETResponse.getLng()));
            index++;

        }

        try {
            double[][] distanceMatrix = getDistanceMatrix(context, mapLocations);
            TspDynamicProgrammingRecursive solver = new TspDynamicProgrammingRecursive(distanceMatrix);

            // Map<Integer, GooglePlaceGETResponse> resultMap = new HashMap<>();
            List<GooglePlaceGETResponse> resultList = new LinkedList<>();

            List<Integer> routing = solver.getTour();
            Iterator<Integer> routingIterator = routing.iterator();

            Integer stepCount = 1;

            while(routingIterator.hasNext()) {
                Integer mapIndex = routingIterator.next();
                // resultMap.put(stepCount, mapLocationNames.get(mapIndex));
                resultList.add(mapLocationNames.get(mapIndex));
                stepCount++;
            }

            return resultList;

            // Print: 42.0
            //System.out.println("Tour cost: " + solver.getTourCost());

        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        } catch (ApiException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    private List<GooglePlaceGETResponse> getAllGooglePlaces(){
        GooglePlaceAPIService googlePlaceAPIService = new GooglePlaceAPIService(
                lat,
                lng,
                rad
        );
        return googlePlaceAPIService.getAllPointOfInterestsLocation();
    }


    private double[][] getDistanceMatrix(GeoApiContext context, Map<Integer, LatLng> mapLocations) throws InterruptedException, ApiException, IOException {
        Instant time = Instant.now();
        Boolean isForCalculateArrivalTime = false;
        DirectionsApi.RouteRestriction routeRestriction = null;

        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context);
        if (isForCalculateArrivalTime) {
            req.departureTime(time);
        } else {
            req.arrivalTime(time);
        }
        if (routeRestriction == null) {
            routeRestriction = DirectionsApi.RouteRestriction.TOLLS;
        }

        LatLng[] arrayLocations = mapLocations.values().toArray(new LatLng[mapLocations.values().size()]);

        DistanceMatrix trix = req.origins(arrayLocations)
                .destinations(arrayLocations)
                .mode(TravelMode.WALKING)
                .avoid(routeRestriction)
                .await();

        int n = trix.rows.length;
        double[][] distanceMatrix = new double[n][n];

        for(Integer indexRows = 0; indexRows < trix.rows.length; indexRows++){

            DistanceMatrixElement[] distanceMatrixElements = trix.rows[indexRows].elements;

            for(Integer indexColumns = 0; indexColumns < distanceMatrixElements.length; indexColumns++) {

                distanceMatrix[indexRows][indexColumns] = distanceMatrixElements[indexColumns].distance.inMeters;

            }

        }

        return distanceMatrix;
    }

}
