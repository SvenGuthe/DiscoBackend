package disco.api.externalapi.instagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.apache.logging.log4j.*;
import org.json.JSONArray;
import org.json.JSONObject;

public class InstagramAPIService {

    private static Logger logger = LogManager.getRootLogger();

    private double lat;
    private double lng;
    private double rad;

    public InstagramAPIService(double lat, double lng, double rad){
        this.lat = lat;
        this.lng = lng;
        this.rad = rad;
    }

    public List<InstagramGETResponse> getAllImagesFromLocation() {

        logger.info("Try to access Instagram API with the following parameters: ");
        logger.info("Latitude: " + lat);
        logger.info("Longitude" + lng);
        logger.info("Radius" + rad);

        String instagramAPIUrl = buildInstagramAPIURL();
        logger.info("InstagramAPIURL: " + instagramAPIUrl);

        if(instagramAPIUrl == null) {
            logger.warn("InstagramAPIURL is null");
            return null;
        }

        HttpResponse<JsonNode> httpResponse = null;
        try {
            httpResponse = accessInstagramAPIURL(instagramAPIUrl).asJson();
        } catch (UnirestException e) {
            logger.warn("Fail parsing HTTP Response: " + e);
            return new ArrayList<InstagramGETResponse>();
        }

        logger.info("The HTTP Response Code is " + httpResponse.getStatus());

        if(httpResponse.getStatus() == 200) {
            return formatJSONCallback(httpResponse.getBody());
        } else {
            logger.warn("Wrong HTTP Response Code");
            return null;
        }

    }

    private String buildInstagramAPIURL() {

        final String basicURL = "https://api.instagram.com/v1/media/search?";

        String instagramAccessToken = getInstagramAccessToken();

        if(instagramAccessToken == null) {
            logger.warn("There is no Instagram Access Token in the Environment Variables");
            return null;
        }

        StringBuilder urlBuild = new StringBuilder();

        urlBuild.append(basicURL);
        urlBuild.append("lat=");
        urlBuild.append(lat);
        urlBuild.append("&lng=");
        urlBuild.append(lng);
        urlBuild.append("&distance=");
        urlBuild.append(rad);
        urlBuild.append("&access_token=");
        urlBuild.append(instagramAccessToken);

        return urlBuild.toString();
    }

    private GetRequest accessInstagramAPIURL(String instagramAPIURL) {

        return Unirest.get(instagramAPIURL);

    }

    private List<InstagramGETResponse> formatJSONCallback(JsonNode responseBody) {

        List<InstagramGETResponse> instagramGETResponseList = new ArrayList<InstagramGETResponse>();

        JSONObject parentObject = responseBody.getObject();
        JSONArray allImages = parentObject.getJSONArray("data");

        Integer countImages = allImages.length();

        for (Integer index = 0; index < countImages; index++){
            JSONObject singlePost = allImages.getJSONObject(index);

            String imageURL = singlePost.getJSONObject("images").getJSONObject("standard_resolution").getString("url");

            JSONObject location = singlePost.getJSONObject("location");
            Double lat = location.getDouble("latitude");
            Double lng = location.getDouble("longitude");
            String name = location.getString("name");

            InstagramGETResponse instagramGETResponse = new InstagramGETResponse(
                    lat,
                    lng,
                    name,
                    imageURL
            );

            instagramGETResponseList.add(instagramGETResponse);

        }

        return instagramGETResponseList;
    }

    private String getInstagramAccessToken() {
        final String envInstagramAccessTokenKey = "INSTAGRAM_ACCESS_TOKEN";

        Map<String, String> env = System.getenv();

        if(env.containsKey(envInstagramAccessTokenKey)) {
            String envInstagramAccessTokenValue = env.get(envInstagramAccessTokenKey);
            logger.info("The Env-Variable " + envInstagramAccessTokenKey + " = " + envInstagramAccessTokenValue );
            return envInstagramAccessTokenValue;
        } else {
            return null;
        }
    }

}
