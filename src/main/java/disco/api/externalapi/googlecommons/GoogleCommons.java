package disco.api.externalapi.googlecommons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class GoogleCommons {

    private static Logger logger = LogManager.getRootLogger();

    public static String getGoogleAPIKey() {
        final String envGoogleAPIKeyKey = "GOOGLE_API_KEY";

        Map<String, String> env = System.getenv();

        if(env.containsKey(envGoogleAPIKeyKey)) {
            String envGoogleAPIKeyValue = env.get(envGoogleAPIKeyKey);
            logger.info("The Env-Variable " + envGoogleAPIKeyKey + " = " + envGoogleAPIKeyValue );
            return envGoogleAPIKeyValue;
        } else {
            return null;
        }
    }

}
