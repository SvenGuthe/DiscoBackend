package disco.api.externalapi.googledistance;

import com.google.maps.errors.ApiException;
import disco.api.externalapi.googleplace.GooglePlaceGETResponse;

import java.io.IOException;

import java.util.Map;

public class GoogleDistanceTestRunner {

    public static void main(String[] args) throws InterruptedException, ApiException, IOException {

        GoogleDistanceAPIService googleDistanceAPIService = new GoogleDistanceAPIService(
                48.858386309412,
                2.2945538162266,
                5000
        );

        Map<Integer, GooglePlaceGETResponse> routing = googleDistanceAPIService.getRouting();

        return;

    }

}
