package com.example.jay.instagramclone.instagramhomepage;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jay.instagramclone.FirebaseUtil;
import com.example.jay.instagramclone.Models.Post;
import com.example.jay.instagramclone.PostViewHolder;
import com.example.jay.instagramclone.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class InstaPost extends Fragment {

    RecyclerView mPostsRecycler;
    RecyclerView.Adapter<PostViewHolder> mAdapter;

    public InstaPost() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_insta_post, container, false);
        mPostsRecycler = (RecyclerView) view.findViewById(R.id.posts_recycler);
        Query allPostsQuery = FirebaseUtil.getPostsRef();
        mAdapter = getFirebaseAdapter(allPostsQuery);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setReverseLayout(true);
        mPostsRecycler.setLayoutManager(manager);
        mPostsRecycler.setAdapter(mAdapter);
        return view;
    }

    private FirebaseRecyclerAdapter getFirebaseAdapter(Query query){
        return new FirebaseRecyclerAdapter<Post,PostViewHolder>(Post.class,R.layout.post_card, PostViewHolder.class,query) {
            @Override
            protected void populateViewHolder(PostViewHolder viewHolder, Post model, int position) {
                viewHolder.setmProfileImg(model.getAuthor().getProfile_image());
                viewHolder.setPostImg(model.getImageUrl());
                viewHolder.setUsername(model.getAuthor().getDisplay_name());
            }
        };
    }

}
