package disco.api.controller;

import disco.api.externalapi.instagram.InstagramAPIService;
import disco.api.externalapi.instagram.InstagramGETResponse;
import disco.api.externalapi.twitter.TwitterAPIService;
import disco.api.externalapi.twitter.TwitterGETResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

}
