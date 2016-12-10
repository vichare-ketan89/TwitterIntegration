package com.keto.twitterintegration.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.keto.twitterintegration.R;
import com.keto.twitterintegration.pojo.Follower;
import com.keto.twitterintegration.views.FollowerViewHolder;

import java.util.ArrayList;

/**
 * Created by ketanvichare on 12/6/16.
 */

public class FollowersRecyclerAdapter extends RecyclerView.Adapter<FollowerViewHolder> {

    private Context mContext;
    private ArrayList<Follower> mFollowers;

    public FollowersRecyclerAdapter(Context context, ArrayList<Follower> followers){
        mContext = context;
        mFollowers = followers;
    }

    @Override
    public FollowerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_follower_item, parent, false);
        return new FollowerViewHolder(mContext, view);
    }


    @Override
    public void onBindViewHolder(FollowerViewHolder holder, int position) {
        holder.userName.setText("Name : "+mFollowers.get(position).getUserName());
        holder.userScreenName.setText("Screen Name : "+mFollowers.get(position).getUserScreenName());
        holder.userLocation.setText("Location : "+mFollowers.get(position).getLocation());

        holder.sendImageButton.setTag(mFollowers.get(position));
    }

    @Override
    public int getItemCount() {
        if(mFollowers != null)
        return mFollowers.size();
        else return 0;
    }
}
