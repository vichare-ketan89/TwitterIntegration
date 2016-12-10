package com.keto.twitterintegration.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.keto.twitterintegration.R;
import com.keto.twitterintegration.adapter.FollowersRecyclerAdapter;
import com.keto.twitterintegration.network.CustomServices;
import com.keto.twitterintegration.network.CustomTwitterApiClient;
import com.keto.twitterintegration.pojo.Follower;
import com.keto.twitterintegration.util.TwitterIntegrationUtil;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.keto.twitterintegration.util.IConstants.BUNDLE_KEY_TWITTER_LOGIN;
import static com.keto.twitterintegration.util.IConstants.USER_ID_KEY;

public class FollowersActivity extends AppCompatActivity {

    private FollowersRecyclerAdapter followersRecyclerAdapter;
    private RecyclerView followersRecyclerView;
    private Context mContext;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        init();
    }

    private void getDataFromBundle(){
        Bundle bundle = getIntent().getExtras().getBundle(BUNDLE_KEY_TWITTER_LOGIN);
        if(bundle != null) {
            userId = bundle.getString(USER_ID_KEY);
        }
    }

    private void init(){
        mContext = this;
        followersRecyclerView = (RecyclerView) findViewById(R.id.followers_recycler_view);
        followersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final TwitterSession activeSession = TwitterCore.getInstance()
                .getSessionManager().getActiveSession();
        CustomTwitterApiClient customTwitterApiClient = new CustomTwitterApiClient(activeSession );

        CustomServices customServices = customTwitterApiClient.getFollowersService();
        Call<ResponseBody> call = customServices.list(userId);
        call.enqueue(followerCallback);
    }

    Callback<ResponseBody> followerCallback = new Callback<ResponseBody>() {
        @Override
        public void success(Result<ResponseBody> result) {
            Log.d("FollowersActivity", result.data.toString());

            String stringResponse = TwitterIntegrationUtil.convertResponseToString(result);
            Log.d("FollowersActivity", stringResponse);

            try{
                JSONObject jsonObject = new JSONObject(stringResponse);
                JSONArray users = (JSONArray) jsonObject.get("users");

                ArrayList<Follower> followers = new ArrayList<>();
                for(int i = 0; i < users.length(); i++){
                    JSONObject user = (JSONObject) users.get(i);
                    Follower follower = new Follower();
                    follower.setUserName(user.getString("name"));
                    follower.setUserScreenName(user.getString("screen_name"));
                    follower.setLocation(user.getString("location"));
                    follower.setUserId(user.getLong("id"));

                    followers.add(follower);
                }

                followersRecyclerAdapter = new FollowersRecyclerAdapter(mContext, followers);
                followersRecyclerView.setAdapter(followersRecyclerAdapter);

            }catch(JSONException ex){
                ex.printStackTrace();
            }
        }

        @Override
        public void failure(TwitterException exception) {
            Log.d("FollowersActivity", exception.getMessage());
        }
    };
}
