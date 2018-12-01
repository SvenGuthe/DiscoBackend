package disco.api.externalapi.instagram;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class InstagramTestRunner {

    public static void main(String args[]){

        Logger logger = LogManager.getRootLogger();

        InstagramAPIService instagramAPIService = new InstagramAPIService(
                48.858386309412,
                2.2945538162266,
                5000
        );

        try {
            List<InstagramGETResponse> instagramGETResponseList = instagramAPIService.getAllImagesFromLocation();
            logger.info("SUCCESSFUL");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
