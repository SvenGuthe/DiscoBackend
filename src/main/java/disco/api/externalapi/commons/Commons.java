package disco.api.externalapi.commons;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public class Commons {

    private static Logger logger = LogManager.getRootLogger();

    public static String returnKey(String envKey) {

        Map<String, String> env = System.getenv();

        if(env.containsKey(envKey)) {
            String keyValue = env.get(envKey);
            logger.info("The Env-Variable " + envKey + " = " + keyValue );
            return keyValue;
        } else {
            return null;
        }

    }

}
