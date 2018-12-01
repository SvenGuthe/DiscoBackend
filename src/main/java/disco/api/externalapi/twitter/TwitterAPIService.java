package disco.api.externalapi.twitter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.social.twitter.api.GeoCode;
import org.springframework.social.twitter.api.SearchParameters;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.util.*;

public class TwitterAPIService {

    final String twitter_consumer_key = "TWITTER_CONSUMER_KEY";
    final String twitter_consumer_secret = "TWITTER_CONSUMER_SECRET";
    final String twitter_access_token = "TWITTER_ACCESS_TOKEN";
    final String twitter_access_token_secret = "TWITTER_ACCESS_TOKEN_SECRET";

    private static Logger logger = LogManager.getRootLogger();

    private double lat;
    private double lng;
    private double rad;

    public TwitterAPIService(double lat, double lng, double rad){
        this.lat = lat;
        this.lng = lng;
        this.rad = rad;
    }

    public List<TwitterGETResponse> getAllTweetsFromLocation() {

        Map<String, String> twitterKeys = getTwitterKeys();

        if(twitterKeys == null) {
            return new LinkedList<>();
        }

        Twitter twitter = new TwitterTemplate(
                twitterKeys.get(twitter_consumer_key),
                twitterKeys.get(twitter_consumer_secret),
                twitterKeys.get(twitter_access_token),
                twitterKeys.get(twitter_access_token_secret)
        );

        SearchParameters searchParameters = new SearchParameters("")
                .geoCode(new GeoCode(lat,
                        lng,
                        (int) Math.round(rad / 1000),
                        GeoCode.Unit.KILOMETER));

        List<Tweet> tweetList = twitter.searchOperations().search(searchParameters).getTweets();

        return formatTweetToGETResponse(tweetList);
    }

    private List<TwitterGETResponse> formatTweetToGETResponse(List<Tweet> tweetList) {

        List<TwitterGETResponse> twitterGETResponseList = new LinkedList<>();
        Iterator<Tweet> tweetIterator = tweetList.iterator();

        while(tweetIterator.hasNext()){
            Tweet singleTweet = tweetIterator.next();

            TwitterGETResponse twitterGETResponse = new TwitterGETResponse();

            String name = singleTweet.getUser().getName();
            String text = singleTweet.getText();
            Date date = singleTweet.getCreatedAt();

            twitterGETResponse.setName(name);
            twitterGETResponse.setText(text);
            twitterGETResponse.setDate(date);

            twitterGETResponseList.add(twitterGETResponse);

        }
        return twitterGETResponseList;
    }

    private Map<String, String> getTwitterKeys() {

        Map<String, String> env = System.getenv();
        Map<String, String> twitter_access_map = new HashMap<>();

        if(env.containsKey(twitter_consumer_key) &&
        env.containsKey(twitter_consumer_secret) &&
        env.containsKey(twitter_access_token) &&
        env.containsKey(twitter_access_token_secret)) {

            twitter_access_map.put(twitter_consumer_key, env.get(twitter_consumer_key));
            twitter_access_map.put(twitter_consumer_secret, env.get(twitter_consumer_secret));
            twitter_access_map.put(twitter_access_token, env.get(twitter_access_token));
            twitter_access_map.put(twitter_access_token_secret, env.get(twitter_access_token_secret));

            logger.info("The Env-Variable " + twitter_consumer_key + " = " + env.get(twitter_consumer_key) );
            logger.info("The Env-Variable " + twitter_consumer_secret + " = " + env.get(twitter_consumer_secret) );
            logger.info("The Env-Variable " + twitter_access_token + " = " + env.get(twitter_access_token) );
            logger.info("The Env-Variable " + twitter_access_token_secret + " = " + env.get(twitter_access_token_secret) );

            return twitter_access_map;
        } else {
            return null;
        }

    }

}
