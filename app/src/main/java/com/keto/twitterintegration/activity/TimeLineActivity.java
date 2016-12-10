package com.keto.twitterintegration.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ListViewCompat;

import com.keto.twitterintegration.R;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

import static com.keto.twitterintegration.util.IConstants.BUNDLE_KEY_TWITTER_LOGIN;
import static com.keto.twitterintegration.util.IConstants.SEARCH;
import static com.keto.twitterintegration.util.IConstants.SEARCH_TWEET_KEY;
import static com.keto.twitterintegration.util.IConstants.TIMELINE_TYPE_KEY;
import static com.keto.twitterintegration.util.IConstants.USER;
import static com.keto.twitterintegration.util.IConstants.USER_NAME_KEY;

public class TimeLineActivity extends AppCompatActivity {

    private ListViewCompat timeLineListView;
    private String userName;
    private String timelineType;
    private String searchText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_line);
        getDataFromBundle();
        init();
    }

    private void getDataFromBundle(){
        Bundle bundle = getIntent().getExtras().getBundle(BUNDLE_KEY_TWITTER_LOGIN);
        if(bundle != null) {
            userName = bundle.getString(USER_NAME_KEY);
            timelineType = bundle.getString(TIMELINE_TYPE_KEY);
            searchText = bundle.getString(SEARCH_TWEET_KEY);
        }
    }

    private void init(){
        timeLineListView = (ListViewCompat) findViewById(R.id.timeline_list_view);

        final Timeline timeline;

        if(timelineType.equalsIgnoreCase(USER)) {
            timeline = new UserTimeline.Builder()
                    .screenName(userName)
                    .build();
        }else if(timelineType.equalsIgnoreCase(SEARCH)){

            timeline = new SearchTimeline.Builder()
                    .query(searchText)
                    .build();
        }else{
            timeline = new SearchTimeline.Builder()
                    .query("#twitter")
                    .build();
        }

        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(timeline)
                .build();

        timeLineListView.setAdapter(adapter);
    }

}
