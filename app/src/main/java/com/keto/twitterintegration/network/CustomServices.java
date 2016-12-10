package com.keto.twitterintegration.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by ketanvichare on 12/6/16.
 */

public interface CustomServices {
        @GET("/1.1/followers/list.json")
        Call<ResponseBody> list(@Query("screen_name") String userId);

        @POST("https://api.twitter.com/1.1/direct_messages/new.json")
        Call<ResponseBody> sendMessage(@Query("screen_name") String screenName,
                                       @Query("user_id") Long userId,
                                       @Query("text") String messageText);

        @GET("https://api.twitter.com/1.1/direct_messages.json")
        Call<ResponseBody> getDirectMessage(@Query("count") int count);
}
