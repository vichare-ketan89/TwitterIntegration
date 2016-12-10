package com.keto.twitterintegration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.keto.twitterintegration.R;
import com.keto.twitterintegration.util.IConstants;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

public class MenuActivity extends AppCompatActivity implements IConstants, View.OnClickListener{

    private String userName;
    private Long userId;
    private EditText tweetEditText;
    private Button searchTweets;
    private Button getFollowersButton;
    private Button postTweetButton;
    private Button showTimeLineButton;
    private EditText tweetSearchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        getDataFromBundle();
        init();
    }

    private void getDataFromBundle(){
        Bundle bundle = getIntent().getExtras().getBundle(BUNDLE_KEY_TWITTER_LOGIN);
        if(bundle != null) {
            userName = bundle.getString(USER_NAME_KEY);
            userId = bundle.getLong(USER_ID_KEY);
        }
    }

    private void init(){
        TextView helloUserTextView = (TextView) findViewById(R.id.hello_user_text_view);
        showTimeLineButton = (Button) findViewById(R.id.show_timeline_buton);
        postTweetButton = (Button) findViewById(R.id.post_tweet_button);
        getFollowersButton = (Button) findViewById(R.id.get_followers_list_button);
        searchTweets = (Button) findViewById(R.id.search_tweets);
        tweetEditText = (EditText) findViewById(R.id.edit_tweet);
        tweetSearchText = (EditText) findViewById(R.id.edit_tweet_search);

        //Register Listeners
        showTimeLineButton.setOnClickListener(this);
        postTweetButton.setOnClickListener(this);
        getFollowersButton.setOnClickListener(this);
        searchTweets.setOnClickListener(this);

        helloUserTextView.setText("Hello "+ userName);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.show_timeline_buton :{
                startTimeLineActivity();
                break;
            }

            case R.id.post_tweet_button :{
                postTweet();
                break;
            }

            case R.id.get_followers_list_button :{
                getFollowersList();
                break;
            }

            case R.id.search_tweets: {
                searchTweets();
                break;
            }

            default:
        }
    }


    private void searchTweets() {
        Intent intent = new Intent(this, TimeLineActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(USER_NAME_KEY, userName);
        bundle.putLong(USER_ID_KEY, userId);
        bundle.putString(TIMELINE_TYPE_KEY, SEARCH);
        bundle.putString(SEARCH_TWEET_KEY, tweetSearchText.getText().toString());
        intent.putExtra(BUNDLE_KEY_TWITTER_LOGIN, bundle);
        startActivity(intent);
    }

    private void getFollowersList() {
        Intent intent = new Intent(this, FollowersActivity.class);
        Bundle bundle = new Bundle();
        bundle.putLong(USER_ID_KEY, userId);
        intent.putExtra(BUNDLE_KEY_TWITTER_LOGIN, bundle);
        startActivity(intent);
    }

    private void startTimeLineActivity(){
        Intent intent = new Intent(this, TimeLineActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(USER_NAME_KEY, userName);
        bundle.putString(TIMELINE_TYPE_KEY, USER);
        bundle.putLong(USER_ID_KEY, userId);
        intent.putExtra(BUNDLE_KEY_TWITTER_LOGIN, bundle);
        startActivity(intent);
    }

    private void postTweet(){
        TweetComposer.Builder builder = new TweetComposer.Builder(this)
                .text(tweetEditText.getText()+"");
        builder.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cleanUp();
    }

    private void cleanUp(){
        showTimeLineButton.setOnClickListener(null);
        postTweetButton.setOnClickListener(null);
        getFollowersButton.setOnClickListener(null);
        searchTweets.setOnClickListener(null);
    }
}
