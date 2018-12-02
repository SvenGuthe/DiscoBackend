package disco.api.externalapi.commons;

public class WeatherCommons {

    public static String getWeatherAPIKey() {
        final String key = "WEATHER_API_KEY";
        return Commons.returnKey(key);
    }

}
