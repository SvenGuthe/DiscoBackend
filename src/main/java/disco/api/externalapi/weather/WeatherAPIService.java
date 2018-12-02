package disco.api.externalapi.weather;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import disco.api.externalapi.commons.WeatherCommons;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class WeatherAPIService {

    private static Logger logger = LogManager.getRootLogger();

    private double lat;
    private double lng;
    private double rad;

    public WeatherAPIService(double lat, double lng, double rad){
        this.lat = lat;
        this.lng = lng;
        this.rad = rad;
    }

    public List<WeatherGETResponse> getWeatherForecast(){

        String apiKey = WeatherCommons.getWeatherAPIKey();

        String url = createURL(apiKey);
        JSONObject responseBody = getResponseBody(url);

        if(responseBody == null){
            return null;
        }

        Integer responseCode = responseBody.getInt("cod");

        if(responseCode != 200){
            return null;
        }

        JSONArray listOfForecasting = responseBody.getJSONArray("list");

        return formatForecastList(listOfForecasting);

    }

    private List<WeatherGETResponse> formatForecastList(JSONArray listOfForecasting){
        Integer arrayLength = listOfForecasting.length();

        List<WeatherGETResponse> getResponseList = new LinkedList<>();

        for(Integer index = 0; index < arrayLength; index ++){

            JSONObject singleEntry = listOfForecasting.getJSONObject(index);

            String datetime = singleEntry.getString("dt_txt");

            JSONArray weatherArray = singleEntry.getJSONArray("weather");
            JSONObject weatherObject = weatherArray.getJSONObject(0);

            String condition = weatherObject.getString("main");
            String conditionDescription = weatherObject.getString("description");
            String icon = "http://openweathermap.org/img/w/" + weatherObject.getString("icon") + ".png";

            JSONObject temperatures = singleEntry.getJSONObject("main");
            Double temp_min = temperatures.getDouble("temp_min");
            Double temp_max = temperatures.getDouble("temp_max");

            WeatherGETResponse weatherGETResponse = new WeatherGETResponse(
                    datetime,
                    condition,
                    conditionDescription,
                    icon,
                    (int) Math.round(temp_min-273.15),
                    (int) Math.round(temp_max-273.15)
            );

            getResponseList.add(weatherGETResponse);

        }

        return getResponseList;
    }

    private String createURL(String appId){
        return "https://samples.openweathermap.org/data/2.5/forecast?lat=" + lat + "&lon=" + lng + "&appid=" + appId;
    }

    private JSONObject getResponseBody(String url){
        HttpResponse<JsonNode> httpResponse = null;
        try {
            httpResponse = Unirest.get(url).asJson();
        } catch (UnirestException e) {
            e.printStackTrace();
            return null;
        }
        return httpResponse.getBody().getObject();
    }

}
