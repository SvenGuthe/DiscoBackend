package disco.api.controller;

import disco.api.externalapi.instagram.InstagramAPIService;
import disco.api.externalapi.instagram.InstagramGETResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api")
public class APIController {

    @Autowired
    public APIController(){

    }

    @RequestMapping(method = RequestMethod.GET, value ="disco", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<InstagramGETResponse> getResponseList(
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

}
