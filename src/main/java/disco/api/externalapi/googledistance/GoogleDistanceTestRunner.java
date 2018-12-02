package disco.api.externalapi.googledistance;

import disco.api.externalapi.googleplace.GooglePlaceGETResponse;

import java.util.List;

public class GoogleDistanceTestRunner {

    public static void main(String[] args) {

        GoogleDistanceAPIService googleDistanceAPIService = new GoogleDistanceAPIService(
                48.858386309412,
                2.2945538162266,
                5000
        );

        List<GooglePlaceGETResponse> routing = googleDistanceAPIService.getRouting();

        return;

    }

}
