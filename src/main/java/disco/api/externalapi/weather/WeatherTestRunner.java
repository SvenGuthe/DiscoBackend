package disco.api.externalapi.weather;

import java.util.List;

public class WeatherTestRunner {

    public static void main(String[] args) {

        Double lat = 50.978;
        Double lng = 11.0328;

        WeatherAPIService weatherAPIService = new WeatherAPIService(
                lat,
                lng,
                5000
        );

        List<WeatherGETResponse> getWeatherForecast = weatherAPIService.getWeatherForecast();

        return;

    }

}
