package com.example.jay.instagramclone;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Jay on 7/11/2017.
 */

public class PostViewHolder extends RecyclerView.ViewHolder {
    private ImageView mProfileImg;
    private TextView username;
    private ImageView postImg;
    Context context;
    public PostViewHolder(View itemView) {
        super(itemView);
        mProfileImg = (ImageView) itemView.findViewById(R.id.profile_image);
        username = (TextView) itemView.findViewById(R.id.top_profile_name);
        postImg = (ImageView) itemView.findViewById(R.id.post_image);
        context = mProfileImg.getContext();
    }

    public void setmProfileImg(String mProfileImg) {
        Glide.with(context).load(mProfileImg).fitCenter().into(this.mProfileImg);
    }

    public void setUsername(String username) {
        this.username.setText(username);
    }

    public void setPostImg(String postImg) {
        Glide.with(context).load(postImg).fitCenter().into(this.postImg);
    }
}
