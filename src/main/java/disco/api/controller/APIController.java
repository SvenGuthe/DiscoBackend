package disco.api.controller;

import com.mashape.unirest.http.exceptions.UnirestException;
import disco.api.externalapi.googledistance.GoogleDistanceAPIService;
import disco.api.externalapi.googleplace.GooglePlaceAPIService;
import disco.api.externalapi.googleplace.GooglePlaceGETResponse;
import disco.api.externalapi.instagram.InstagramAPIService;
import disco.api.externalapi.instagram.InstagramGETResponse;
import disco.api.externalapi.twitter.TwitterAPIService;
import disco.api.externalapi.twitter.TwitterGETResponse;
import disco.api.externalapi.weather.WeatherAPIService;
import disco.api.externalapi.weather.WeatherGETResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "api")
public class APIController {

    @Autowired
    public APIController(){

    }

    @RequestMapping(method = RequestMethod.GET, value ="instagram", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InstagramGETResponse> getInstagramResponseList(
            @RequestParam("lat") Double lat,
            @RequestParam("lng") Double lng,
            @RequestParam("rad") Double rad) {
        InstagramAPIService instagramAPIService = new InstagramAPIService(
                lat,
                lng,
                rad
        );
        return instagramAPIService.getAllImagesFromLocation();
    }

    @RequestMapping(method = RequestMethod.GET, value ="twitter", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TwitterGETResponse> getTwitterResponseList(
            @RequestParam("lat") Double lat,
            @RequestParam("lng") Double lng,
            @RequestParam("rad") Double rad) {
        TwitterAPIService twitterAPIService = new TwitterAPIService(
                lat,
                lng,
                rad
        );
        return twitterAPIService.getAllTweetsFromLocation();
    }

    @RequestMapping(method = RequestMethod.GET, value ="googleplacerestaurant", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GooglePlaceGETResponse> getGooglePlaceRestaurantResponseList(
            @RequestParam("lat") Double lat,
            @RequestParam("lng") Double lng,
            @RequestParam("rad") Double rad) {
        GooglePlaceAPIService googlePlaceAPIService = new GooglePlaceAPIService(
                lat,
                lng,
                rad
        );
        return googlePlaceAPIService.getAllRestaurantsLocation();
    }

    @RequestMapping(method = RequestMethod.GET, value ="googleplacesight", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GooglePlaceGETResponse> getGooglePlaceSightResponseList(
            @RequestParam("lat") Double lat,
            @RequestParam("lng") Double lng,
            @RequestParam("rad") Double rad) {
        GooglePlaceAPIService googlePlaceAPIService = new GooglePlaceAPIService(
                lat,
                lng,
                rad
        );
        return googlePlaceAPIService.getAllPointOfInterestsLocation();
    }

    @RequestMapping(method = RequestMethod.GET, value ="googledistanceroute", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<GooglePlaceGETResponse> getGoogleDistanceRoute(
            @RequestParam("lat") Double lat,
            @RequestParam("lng") Double lng,
            @RequestParam("rad") Double rad) {
        GoogleDistanceAPIService googleDistanceAPIService = new GoogleDistanceAPIService(
                lat,
                lng,
                rad
        );
        return googleDistanceAPIService.getRouting();
    }

    @RequestMapping(method = RequestMethod.GET, value ="weather", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<WeatherGETResponse> getWeatherForecast(
            @RequestParam("lat") Double lat,
            @RequestParam("lng") Double lng,
            @RequestParam("rad") Double rad) {
        WeatherAPIService weatherAPIService = new WeatherAPIService(
                lat,
                lng,
                rad
        );
        return weatherAPIService.getWeatherForecast();
    }

}
