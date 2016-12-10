package com.keto.twitterintegration.network;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;

/**
 * Created by ketanvichare on 12/6/16.
 */

public class CustomTwitterApiClient extends TwitterApiClient {

    public CustomTwitterApiClient(TwitterSession twitterSession){
        super(twitterSession);
    }

    public CustomServices getFollowersService(){
        return getService(CustomServices.class);
    }
}

