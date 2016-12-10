package com.keto.twitterintegration.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.keto.twitterintegration.R;
import com.keto.twitterintegration.network.CustomServices;
import com.keto.twitterintegration.network.CustomTwitterApiClient;
import com.keto.twitterintegration.pojo.Follower;
import com.keto.twitterintegration.util.TwitterIntegrationUtil;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;

/**
 * Created by ketanvichare on 12/6/16.
 */

public class FollowerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView userName;
    public TextView userScreenName;
    public TextView userLocation;
    public EditText messageEditText;
    public Button sendImageButton;
    public Context mContext;

    public FollowerViewHolder(Context context, View view){
        super(view);

        mContext = context;
        userName = (TextView) view.findViewById(R.id.user_name);
        userScreenName = (TextView) view.findViewById(R.id.user_screen_name);
        userLocation = (TextView) view.findViewById(R.id.user_location);
        messageEditText = (EditText) view.findViewById(R.id.message_edit_text);
        sendImageButton = (Button) view.findViewById(R.id.send_image_button);

        sendImageButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.send_image_button :{
                Follower follower = (Follower)view.getTag();
                sendMessage(follower);
                break;
            }

            default:
        }
    }

    private void sendMessage(Follower follower){
        Toast.makeText(mContext, "Sending message...", Toast.LENGTH_SHORT).show();
        final TwitterSession activeSession = TwitterCore.getInstance().getSessionManager().getActiveSession();
        CustomTwitterApiClient customTwitterApiClient = new CustomTwitterApiClient(activeSession );

        CustomServices customServices = customTwitterApiClient.getFollowersService();
        Call<ResponseBody> sendMessageCall = customServices.sendMessage(follower.getUserScreenName(), follower.getUserId(), messageEditText.getText().toString());
        sendMessageCall.enqueue(sendDirectMessageCallback);
    }

    public Callback<ResponseBody> sendDirectMessageCallback = new Callback<ResponseBody>() {
        @Override
        public void success(Result<ResponseBody> result) {

            String stringResponse = TwitterIntegrationUtil.convertResponseToString(result);
            Log.d("FollowersActivity", stringResponse);

            try{
                JSONObject jsonObject = new JSONObject(stringResponse);
                JSONObject recipient = (JSONObject) jsonObject.get("recipient");

                String name = recipient.getString("name");
                Toast.makeText(mContext, "message sent to "+name, Toast.LENGTH_SHORT).show();

            }catch(JSONException ex){
                ex.printStackTrace();
            }
        }

        @Override
        public void failure(TwitterException exception) {
            Toast.makeText(mContext, "Message could not be sent", Toast.LENGTH_SHORT).show();
        }
    };
}
