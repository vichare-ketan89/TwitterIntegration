package com.keto.twitterintegration.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.keto.twitterintegration.R;
import com.keto.twitterintegration.util.IConstants;
import com.keto.twitterintegration.util.TwitterApplication;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

public class FirstActivity extends AppCompatActivity implements IConstants{

    private TwitterLoginButton mTwitterLoginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void init(){
        mTwitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        mTwitterLoginButton.setCallback(twitterSessionCallback);
    }

    private Callback<TwitterSession> twitterSessionCallback = new Callback<TwitterSession>() {
        @Override
        public void success(Result<TwitterSession> result) {
            if(result != null && result.data != null){
                startSecondActivity(result.data.getUserName(), result.data.getUserId());
                ((TwitterApplication)getApplication()).setTwitterSession(result.data);
            }
        }

        @Override
        public void failure(TwitterException exception) {

        }
    };

    private void startSecondActivity(String name, Long userId){
        Intent intent = new Intent(this, MenuActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(USER_NAME_KEY, name);
        bundle.putLong(USER_ID_KEY, userId);
        intent.putExtra(BUNDLE_KEY_TWITTER_LOGIN, bundle);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        mTwitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

}
