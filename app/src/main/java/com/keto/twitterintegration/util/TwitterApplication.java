package com.keto.twitterintegration.util;

import android.app.Application;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterSession;

import io.fabric.sdk.android.Fabric;

/**
 * Created by ketanvichare on 12/3/16.
 */

public class TwitterApplication extends Application {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "zvRwlSiabKpQOXTqm4nB7Bqc4";
    private static final String TWITTER_SECRET = "MLupgVQHaWNG2SdepnNh3yGsJu3RLQ3yyLNc6nqKfknmLvXXeA";
    private TwitterSession twitterSession;

    @Override
    public void onCreate() {
        super.onCreate();
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
    }

    public TwitterSession getTwitterSession() {
        return twitterSession;
    }

    public void setTwitterSession(TwitterSession twitterSession) {
        this.twitterSession = twitterSession;
    }
}
